package net.rewerk.servlets.repository;

import net.rewerk.servlets.model.Book;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookRepositoryTest {
    BookRepository bookRepository;

    @Before
    public void setUp() {
        try {
            flushRepositoryResource();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        bookRepository = BookRepository.getInstance();
    }
    @After
    public void tearDown() {
        bookRepository.deleteAll();
        bookRepository = null;
    }

    @Test
    public void whenRepositoryCreated_thenCorrectCount() {
        Assert.assertEquals(0, bookRepository.count());
    }

    @Test
    public void givenBook_whenSave_thenCorrectCount() {
        bookRepository.save(getBook());

        Assert.assertEquals(1, bookRepository.count());
    }

    @Test
    public void givenBook_whenFindById_thenCorrect() {
        Book book = getBook();
        bookRepository.save(book);
        Book bookFromRepository = bookRepository.findById(book.getId());

        Assert.assertEquals(book, bookFromRepository);
    }

    @Test
    public void givenBook_whenDelete_thenCorrect() {
        Book book = getBook();
        bookRepository.save(book);
        bookRepository.delete(book.getId());

        Assert.assertEquals(0, bookRepository.count());
    }

    @Test
    public void whenFindAll_thenCorrect() {
        List<Book> books = new ArrayList<>();

        Assert.assertEquals(books, bookRepository.findAll());
    }

    @Test
    public void givenBook_whenExistsById_thenCorrect() {
        Book book = getBook();
        bookRepository.save(book);

        Assert.assertTrue(bookRepository.isExistsById(book.getId()));
    }

    private Book getBook() {
        return new Book(UUID.randomUUID(), "Test title", "Test author", 1990, 10);
    }

    public static void flushRepositoryResource() throws NoSuchFieldException, IllegalAccessException {
        Field instance = BookRepository.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        URL path = BookRepository.class.getResource("/books.json");
        if (path != null) {
            File file = new File(Paths.get(path.getPath()).toString());
            if (file.exists() && file.canWrite()) {
                if (!file.delete()) {
                    throw new RuntimeException("Could not delete file " + file.getAbsolutePath());
                }
            }
        }
    }
}
