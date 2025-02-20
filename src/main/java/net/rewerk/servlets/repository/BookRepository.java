package net.rewerk.servlets.repository;

import net.rewerk.servlets.exception.SourceSaveException;
import net.rewerk.servlets.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookRepository extends AbstractRepository<Book> {
    private final String FILENAME = "books.json";
    private static BookRepository instance;
    private final List<Book> books = new ArrayList<>();

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    private BookRepository() {
        try {
            books.addAll(super.getEntities(FILENAME, Book.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Book> findAll() {
        return books;
    }

    public long count() {
        return books.size();
    }

    public boolean isExistsById(UUID id) {
        return findByIdInternal(id) != null;
    }

    public Book findById(UUID id) {
        return findByIdInternal(id);
    }

    public Book save(Book book) {
        try {
            Book old = findByIdInternal(book.getId());
            if (old != null) {
                this.books.remove(old);
            }
            this.books.add(book);
            super.save(this.books, FILENAME);
            return book;
        } catch (SourceSaveException e) {
            return null;
        }
    }

    public boolean delete(UUID id) {
        Book book = findByIdInternal(id);
        if (book != null) {
            this.books.remove(book);
            super.save(this.books, FILENAME);
            return true;
        }
        return false;
    }

    public void deleteAll() {
        this.books.clear();
    }

    private Book findByIdInternal(UUID id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }
}
