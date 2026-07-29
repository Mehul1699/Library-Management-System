package com.library;

import com.library.constant.BookStatus;
import com.library.constant.NotificationType;
import com.library.entity.Book;
import com.library.entity.Branch;
import com.library.entity.Patron;
import com.library.service.BookService;
import com.library.service.BranchService;
import com.library.service.IssueService;
import com.library.service.PatronService;

import java.util.*;

public class Main {

    private static final BookService bookService = new BookService();
    private static final PatronService patronService = new PatronService();
    private static final BranchService branchService = new BranchService(bookService);
    private static final IssueService issueService = new IssueService(bookService, patronService);

    static void main() {
        int choice = 0;
        while (choice != 17) {
            System.out.println("=== Library Management System ===");
            System.out.println("Please select an option");
            System.out.println("1. Add Branch");
            System.out.println("2. Add Book");
            System.out.println("3. Remove a book");
            System.out.println("4. Add Patron");
            System.out.println("5. Search book by Id");
            System.out.println("6. Search book by title");
            System.out.println("7. Search books by Author");
            System.out.println("8. Search book by ISBN");
            System.out.println("9. Search Patron by Id");
            System.out.println("10. Search Patron by name");
            System.out.println("11. Borrow a book");
            System.out.println("12. Return a book");
            System.out.println("13. Reserve a book");
            System.out.println("14. Transfer a book");
            System.out.println("15. Display all branches");
            System.out.println("16. Display recommended books");
            System.out.println("17. Exit");

            System.out.println("Please enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addBranch(scanner);
                    break;
                case 2:
                    addBook(scanner);
                    break;
                case 3:
                    removeBook(scanner);
                    break;
                case 4:
                    addPatron(scanner);
                    break;
                case 5:
                    searchBookById(scanner);
                    break;
                case 6:
                    searchBookByTitle(scanner);
                    break;
                case 7:
                    searchBookByAuthor(scanner);
                    break;
                case 8:
                    searchBookByISBN(scanner);
                    break;
                case 9:
                    searchPatronById(scanner);
                    break;
                case 10:
                    searchPatronByName(scanner);
                    break;
                case 11:
                    borrowBook(scanner);
                    break;
                case 12:
                    returnBook(scanner);
                    break;
                case 13:
                    reserveBook(scanner);
                    break;
                case 14:
                    transferBook(scanner);
                    break;
                case 15:
                    displayAllBranches();
                    break;
                case 16:
                    System.out.println("Please enter patron id: ");
                    int patronId = scanner.nextInt();
                    scanner.nextLine();
                    displayRecommendedBooks(patronId);
                    break;
                case 17:
                default:
                    System.out.println("THANK YOU!!");
                    break;
            }
        }
    }

    private static void addBranch(Scanner scanner) {
        System.out.println("Please enter branch name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter branch address: ");
        String address = scanner.nextLine();
        Branch branch = new Branch(name, address);
        branchService.addBranch(branch);
        System.out.println("Branch added: " + branch);
    }

    private static void addBook(Scanner scanner) {
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();
        System.out.println("Enter author name: ");
        String author = scanner.nextLine();
        System.out.println("Enter isbn: ");
        String isbn = scanner.nextLine();
        System.out.println("Enter publication year: ");
        int publicationYear = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter branch id of the branch which would own this book: ");
        int branchId = scanner.nextInt();
        scanner.nextLine();
        Book book = new Book(
                title, author, isbn, publicationYear, branchId
        );
        bookService.addBook(book);
        System.out.println("Book added: " + book);
    }

    private static void removeBook(Scanner scanner) {
        System.out.println("Enter book id: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        try {
            Book book = bookService.search(bookId);
            bookService.removeBook(book);
        } catch (Exception e) {
            System.out.println("Error in remove book: " + e.getLocalizedMessage());
        }
    }

    private static void addPatron(Scanner scanner) {
        System.out.println("Please enter patron name: ");
        String name = scanner.nextLine();
        NotificationType notificationType = null;
        while (notificationType == null) {
            try {
                System.out.print("Enter notification type (EMAIL/SMS): ");
                notificationType = NotificationType.valueOf(scanner.next().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid specialization. Try again.");
            }
        }
        Patron patron = new Patron(name, notificationType);
        patronService.addPatron(patron);
        System.out.println("Patron created: " + patron);
    }

    private static void searchBookById(Scanner scanner) {
        System.out.println("Please enter book id to search: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        try {
            Book book = bookService.search(bookId);
            System.out.println("Response of search book by id: " + book);
        } catch (Exception e) {
            System.out.println("Error in search book by id: " + e.getLocalizedMessage());
        }
    }

    private static void searchBookByTitle(Scanner scanner) {
        System.out.println("Please enter book title to search: ");
        String title = scanner.nextLine();
        try {
            List<Book> book = bookService.search(title);
            System.out.println("Response of search book by title: " + book);
        } catch (Exception e) {
            System.out.println("Error in search book by title: " + e.getLocalizedMessage());
        }
    }

    private static void searchBookByAuthor(Scanner scanner) {
        System.out.println("Please enter author to search book: ");
        String author = scanner.nextLine();
        try {
            List<Book> book = bookService.searchByAuthor(author);
            System.out.println("Response of search book by author: " + book);
        } catch (Exception e) {
            System.out.println("Error in search book by author: " + e.getLocalizedMessage());
        }
    }

    private static void searchBookByISBN(Scanner scanner) {
        System.out.println("Please enter isbn to search book: ");
        String isbn = scanner.nextLine();
        try {
            Book book = bookService.searchByISBN(isbn);
            System.out.println("Response of search book by isbn: " + book);
        } catch (Exception e) {
            System.out.println("Error in search book by isbn: " + e.getLocalizedMessage());
        }
    }

    private static void searchPatronById(Scanner scanner) {
        System.out.println("Please enter patron id to search: ");
        int patronId = scanner.nextInt();
        scanner.nextLine();
        try {
            Patron patron = patronService.search(patronId);
            System.out.println("Response of search patron by id: " + patron);
        } catch (Exception e) {
            System.out.println("Error in search patron by id: " + e.getLocalizedMessage());
        }
    }

    private static void searchPatronByName(Scanner scanner) {
        System.out.println("Please enter patron name to search: ");
        String patronName = scanner.nextLine();
        try {
            List<Patron> patron = patronService.search(patronName);
            System.out.println("Response of search patron by name: " + patron);
        } catch (Exception e) {
            System.out.println("Error in search patron by name: " + e.getLocalizedMessage());
        }
    }

    private static void borrowBook(Scanner scanner) {
        System.out.println("Please enter patron id: ");
        int patronId = scanner.nextInt();
        scanner.nextLine();
        displayRecommendedBooks(patronId);
        System.out.println("Please enter book id to borrow: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        try {
            issueService.borrowBook(bookId, patronId);
        } catch (Exception e) {
            System.out.println("Error in borrow book: " + e.getLocalizedMessage());
        }
    }

    private static void returnBook(Scanner scanner) {
        System.out.println("Please enter patron id: ");
        int patronId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter book id of book to be returned: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        try {
            issueService.returnBook(bookId, patronId);
        } catch (Exception e) {
            System.out.println("Error in return book: " + e.getLocalizedMessage());
        }
    }

    private static void reserveBook(Scanner scanner) {
        System.out.println("Please enter patron id: ");
        int patronId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter book id of book to be reserved: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        try {
            issueService.reserveBook(patronId, bookId);
        } catch (Exception e) {
            System.out.println("Error in reserve book: " + e.getLocalizedMessage());
        }
    }

    private static void transferBook(Scanner scanner) {
        System.out.println("Please enter book id to transfer: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter new branch owner id: ");
        int branchId = scanner.nextInt();
        scanner.nextLine();
        try {
            branchService.transferBook(bookId, branchId);
        } catch (Exception e) {
            System.out.println("Error in transfer book: " + e.getLocalizedMessage());
        }
    }

    private static void displayAllBranches() {
        System.out.println(branchService.getAllBranches());
    }

    private static void displayRecommendedBooks(int patronId) {
        try {
            Patron patron = patronService.search(patronId);
            Set<Integer> booksHistory = patron.getBorrowedHistory();
            if (booksHistory != null && !booksHistory.isEmpty()) {
                System.out.println("=== Recommended books ===");
                Set<String> authorNames = new HashSet<>();
                for (Integer bookId : booksHistory) {
                    Book book = bookService.search(bookId);
                    authorNames.add(book.getAuthor());
                }
                for (String authorName : authorNames) {
                    System.out.println("Books written by: " + authorName);
                    System.out.println(bookService.searchByAuthor(authorName));
                }
            } else {
                System.out.println(bookService.getAllBooks());
            }
        } catch (Exception e) {
            System.out.println("Error in display recommended books: " + e.getLocalizedMessage());
        }
    }
}