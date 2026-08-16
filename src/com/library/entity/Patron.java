package com.library.entity;

import com.library.constant.NotificationType;
import com.library.interfaces.NotificationStrategy;
import com.library.interfaces.Observer;
import com.library.util.IdGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Patron extends LibraryEntity implements Observer {

    private String name;
    private List<Integer> borrowedBooks;
    private Set<Integer> borrowedHistory;
    private NotificationType notificationType;
    private NotificationStrategy notificationStrategy;

    public Patron(String name, NotificationType notificationType) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
        this.borrowedHistory = new HashSet<>();
        this.setNotificationType(notificationType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Integer> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public Set<Integer> getBorrowedHistory() {
        return borrowedHistory;
    }

    public void setBorrowedHistory(Set<Integer> borrowedHistory) {
        this.borrowedHistory = borrowedHistory;
    }

    public void addBorrowedBook(int bookId) {
        borrowedBooks.add(bookId);
        borrowedHistory.add(bookId);
    }

    public void returnBook(int bookId) {
        borrowedBooks.remove(Integer.valueOf(bookId));
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
        this.notificationStrategy =
                NotificationFactory.getNotificationStrategy(notificationType);
    }

    @Override
    public void notifyObservers(String message) {
        this.notificationStrategy.sendNotification("Notification for: " + this.name + " -> " + message);
    }

    @Override
    public String toString() {
        return "Patron{" +
                super.toString() + " " +
                ", name='" + name + '\'' +
                ", borrowedBooks=" + borrowedBooks +
                ", borrowedHistory=" + borrowedHistory +
                '}';
    }
}
