package net.rewerk.servlets.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rewerk.servlets.model.Book;
import net.rewerk.servlets.repository.BookRepository;
import net.rewerk.servlets.util.Utils;

import java.io.IOException;
import java.util.UUID;

public class BookServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book book = null;
        String id = request.getParameter("id");
        if (id != null && Utils.isValidUUID(id)) {
            book = BookRepository.getInstance().findById(UUID.fromString(id));
        }
        request.setAttribute("book", book);
        this.getServletContext().getRequestDispatcher("/book.jsp").forward(request, response);
    }
}
