package com.library.entity;

import com.library.constant.BookStatus;
import com.library.interfaces.Observer;
import com.library.util.IdGenerator;

import java.util.LinkedHashSet;
import java.util.Set;

public class Book extends LibraryEntity {

    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private int branchId;
    private BookStatus bookStatus;
    private Set<Observer> observers = new LinkedHashSet<>();

    public Book(String title, String author, String isbn, int publicationYear,
                int branchId) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.branchId = branchId;
        this.bookStatus = BookStatus.AVAILABLE;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void addObservers(Observer observer) {
        this.observers.add(observer);
    }

    public Set<Observer> getObservers() {
        return observers;
    }

    public void removeObservers(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        if (!observers.isEmpty()) {
            observers.stream().findFirst().get().notifyObservers("Book: " + this.title + " written by: " + this.author + " is available now");
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                super.toString() + " " +
                "bookStatus=" + bookStatus +
                ", branchId=" + branchId +
                ", publicationYear=" + publicationYear +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
