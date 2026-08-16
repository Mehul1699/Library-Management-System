package com.library.service;

import com.library.entity.Book;
import com.library.exception.BookNotFoundException;
import com.library.exception.InvalidDataException;
import com.library.util.DataStore;
import com.library.util.IdGenerator;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookService {

    private DataStore<Book> bookDataStore = new DataStore<>();
    private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());


    public Book addBook(Book book) {
        if (Objects.isNull(book)) {
            LOGGER.log(Level.WARNING, "Book data can't be null");
            throw new InvalidDataException("Book data can't be null");
        }
        book.setId(IdGenerator.idGenerator().nextBookId());
        bookDataStore.addData(book);
        return book;
    }

    public void removeBook(Book book) {
        if (Objects.isNull(book)) {
            throw new InvalidDataException("Book data can't be null. Not able to remove");
        }
        bookDataStore.remove(book);
    }

    public List<Book> getAllBooks() {
        return bookDataStore.getAll();
    }

    public Book search(int id) {
        Book book = bookDataStore.getById(id);
        if (Objects.nonNull(book)) {
            return book;
        }
        LOGGER.log(Level.WARNING, "No book found with id: " + id);
        throw new BookNotFoundException("No book found with id: " + id);
    }

    public List<Book> search(String title) {
        if (title == null || title.isEmpty()) {
            LOGGER.log(Level.WARNING, "Book can't be searched with blank name");
            throw new InvalidDataException("Book can't be searched with blank name");
        }
        return bookDataStore.getAll().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .toList();
    }

    public List<Book> searchByAuthor(String author) {
        if (author == null || author.isEmpty()) {
            LOGGER.log(Level.WARNING, "Book can't be searched with blank author name");
            throw new InvalidDataException("Book can't be searched with blank author name");
        }
        return bookDataStore.getAll().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .toList();
    }

    public Book searchByISBN(String isbn) {
        if (isbn != null && !isbn.isEmpty()) {
            List<Book> books = bookDataStore.getAll();
            for (Book book : books) {
                if (book.getIsbn().equals(isbn)) {
                    return book;
                }
            }
        }
        throw new InvalidDataException("Book can't be searched with blank author name");
    }

}
