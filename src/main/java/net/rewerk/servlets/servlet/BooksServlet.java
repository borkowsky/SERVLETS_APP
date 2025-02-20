package net.rewerk.servlets.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rewerk.servlets.model.Book;
import net.rewerk.servlets.model.User;
import net.rewerk.servlets.repository.BookRepository;
import net.rewerk.servlets.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BooksServlet extends HttpServlet {
    private final BookRepository bookRepository = BookRepository.getInstance();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        BookRepository bookRepository = BookRepository.getInstance();
        List<Book> books = bookRepository.findAll();
        Book book = null;
        if (action != null && (
                action.equalsIgnoreCase("edit") || action.equalsIgnoreCase("delete")
        )) {
            String id = request.getParameter("id");
            if (id != null && Utils.isValidUUID(id)) {
                book = bookRepository.findById(UUID.fromString(id));
            }
            if (book == null) {
                response.sendRedirect(request.getContextPath() + "/books");
                return;
            }
        }
        request.setAttribute("books", books);
        request.setAttribute("book", book);
        request.setAttribute("booksTotal", bookRepository.count());
        this.getServletContext().getRequestDispatcher("/books.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/books");
            return;
        }
        BookRepository bookRepository = BookRepository.getInstance();
        List<Book> books = bookRepository.findAll();
        String operation;
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (!Utils.isValidUUID(id)) {
            id = null;
        }
        List<String> errors;
        if (action != null && action.equalsIgnoreCase("delete")) {
            errors = doDeleteBook(request);
            operation = "delete";
        } else if (id != null) {
            errors = doUpdateBook(request);
            operation = "update";
        } else {
            errors = doCreateBook(request);
            operation = "create";
        }
        request.setAttribute("errors", errors);
        request.setAttribute("success", errors == null || errors.isEmpty());
        request.setAttribute("operation", operation);
        request.setAttribute("books", books);
        request.setAttribute("booksTotal", books.size());
        this.getServletContext().getRequestDispatcher("/books.jsp").forward(request, response);
    }

    private List<String> doCreateBook(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        Book fields = getBookFields(request);
        if (fields.getTitle() == null || fields.getTitle().isEmpty()) {
            errors.add("Title is required");
        }
        if (fields.getAuthor() == null || fields.getAuthor().isEmpty()) {
            errors.add("Author is required");
        }
        if (fields.getYear() < 1) {
            errors.add("Invalid year value");
        }
        if (fields.getPages() < 1) {
            errors.add("Invalid pages value");
        }
        if (errors.isEmpty()) {
            fields.setId(UUID.randomUUID());
            if (bookRepository.save(fields) != null) {
                errors = null;
            } else {
                errors.add("Book creation failed");
            }
        }
        return errors;
    }

    private List<String> doUpdateBook(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        Book fields = getBookFields(request);
        if (fields.getId() == null) {
            errors.add("ID is required");
        }
        if (errors.isEmpty()) {
            Book book = bookRepository.findById(fields.getId());
            if (book == null) {
                errors.add("Book not found");
            } else {
                if (fields.getTitle() != null) book.setTitle(fields.getTitle());
                if (fields.getAuthor() != null) book.setAuthor(fields.getAuthor());
                if (fields.getYear() > 0) book.setYear(fields.getYear());
                if (fields.getPages() > 0) book.setPages(fields.getPages());
                if (bookRepository.save(book) != null) {
                    errors = null;
                } else {
                    errors.add("Book update failed");
                }
            }
        }
        return errors;
    }

    private List<String> doDeleteBook(HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        String id = request.getParameter("id");
        if (id == null || !Utils.isValidUUID(id)) {
            errors.add("Invalid book ID");
            return errors;
        }
        if (!bookRepository.isExistsById(UUID.fromString(id))) {
            errors.add("Book not found");
            return errors;
        }
        if (!bookRepository.delete(UUID.fromString(id))) {
            errors.add("Book delete failed");
        }
        return errors;
    }

    private Book getBookFields(HttpServletRequest request) {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        int year = request.getParameter("year") != null ?
                Integer.parseInt(request.getParameter("year")) : 0;
        int pages = request.getParameter("pages") != null ?
                Integer.parseInt(request.getParameter("pages")) : 0;
        return new Book(
                id == null || Utils.isValidUUID(id) ? null : UUID.fromString(id),
                title,
                author,
                year,
                pages
        );
    }
}
