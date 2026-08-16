package com.library.exception;

public class BookNotBorrowableException extends RuntimeException {

    public BookNotBorrowableException(String message) {
        super(message);
    }

}
