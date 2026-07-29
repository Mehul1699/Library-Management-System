package com.library.service;

import com.library.constant.BookStatus;
import com.library.entity.Book;
import com.library.entity.Branch;
import com.library.entity.Patron;
import com.library.exception.BookNotFoundException;
import com.library.exception.BranchNotFoundException;
import com.library.exception.PatronNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class IssueService {

    private static final Logger LOGGER = Logger.getLogger(IssueService.class.getName());
    private final PatronService patronService;
    private final BookService bookService;

    public IssueService(BookService bookService,
                        PatronService patronService) {
        this.bookService = bookService;
        this.patronService = patronService;
    }

    public void borrowBook(int bookId, int patronId) {
        LOGGER.log(Level.INFO, "Validating Patron");
        Patron patron = patronService.search(patronId);
        if (Objects.isNull(patron)) {
            throw new PatronNotFoundException("Error in borrow Book. Patron not found with id: " + patronId);
        }
        LOGGER.log(Level.INFO, "Validating Book");
        Book book = bookService.search(bookId);
        if (Objects.isNull(book)) {
            throw new BookNotFoundException("Error in borrow Book. Book not found with id: " + bookId);
        }
        LOGGER.log(Level.INFO, "Validating Book status");
        boolean isReservedByPatron = Boolean.TRUE;
        if (book.getObservers() != null && !book.getObservers().isEmpty()) {
            Patron patron1 = (Patron) book.getObservers().stream().findFirst().get();
            isReservedByPatron = patron1.getId() == patronId;
        }
        if (book.getBookStatus() != BookStatus.CHECKED_OUT && isReservedByPatron) {
            book.setBookStatus(BookStatus.CHECKED_OUT);
            if (book.getObservers() != null && !book.getObservers().isEmpty()) {
                book.removeObservers(patron);
            }
            LOGGER.log(Level.INFO, "Book: " + book.getTitle() + " borrowed by: " + patron.getName());
            patron.addBorrowedBook(bookId);
        }
    }

    public void returnBook(int bookId, int patronId) {
        LOGGER.log(Level.INFO, "Validating Patron");
        Patron patron = patronService.search(patronId);
        if (Objects.isNull(patron)) {
            throw new PatronNotFoundException("Error in return Book. Patron not found with id: " + patronId);
        }
        LOGGER.log(Level.INFO, "Validating Book");
        Book book = bookService.search(bookId);
        if (Objects.isNull(book)) {
            throw new BookNotFoundException("Error in return Book. Book not found with id: " + bookId);
        }
        boolean isReturnPossible = patron.getBorrowedBooks().contains(bookId);
        if (isReturnPossible) {
            patron.returnBook(bookId);
            LOGGER.log(Level.INFO, "Book: " + book.getTitle() + " returned by: " + patron.getName());
            book.setBookStatus(BookStatus.AVAILABLE);
            if (book.getObservers() != null && !book.getObservers().isEmpty()) {
                book.notifyObservers();
            }
        }
    }

    public void reserveBook(int patronId, int bookId) {
        LOGGER.log(Level.INFO, "Validating Patron");
        Patron patron = patronService.search(patronId);
        if (Objects.isNull(patron)) {
            throw new PatronNotFoundException("Error in reserve Book. Patron not found with id: " + patronId);
        }
        LOGGER.log(Level.INFO, "Validating Book");
        Book book = bookService.search(bookId);
        if (Objects.isNull(book)) {
            throw new BookNotFoundException("Error in reserve Book. Book not found with id: " + bookId);
        }
        LOGGER.log(Level.INFO, "Reserving book: " + book.getTitle() + " id: " + book.getId() + " for: " + patron.getName());
        book.addObservers(patron);
    }

}
