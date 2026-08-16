package com.library.service;

import com.library.entity.Book;
import com.library.entity.Branch;
import com.library.exception.BookNotFoundException;
import com.library.exception.BranchNotFoundException;
import com.library.exception.InvalidDataException;
import com.library.util.DataStore;
import com.library.util.IdGenerator;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BranchService {

    private static final Logger LOGGER = Logger.getLogger(BranchService.class.getName());
    private DataStore<Branch> branchDataStore = new DataStore<>();
    private final BookService bookService;

    public BranchService(BookService bookService){
        this.bookService = bookService;
    }

    public Branch addBranch(Branch branch) {
        if (Objects.isNull(branch)) {
            throw new InvalidDataException("Branch data can't be null");
        }
        branch.setId(IdGenerator.idGenerator().nextBranchId());
        branchDataStore.addData(branch);
        return branch;
    }

    public List<Branch> getAllBranches() {
        return branchDataStore.getAll();
    }

    public Branch search(int id) {
        Branch branch = branchDataStore.getById(id);
        if (Objects.isNull(branch)) {
            throw new BranchNotFoundException("Branch not found with id: " + id);
        }
        return branch;
    }

    public void transferBook(int bookId, int branchId) {
        LOGGER.log(Level.INFO, "Validating Patron");
        Branch branch = search(branchId);
        if (Objects.isNull(branch)) {
            throw new BranchNotFoundException("Error in transfer Book. Branch not found with id: " + branchId);
        }
        LOGGER.log(Level.INFO, "Validating Book");
        Book book = bookService.search(bookId);
        if (Objects.isNull(book)) {
            throw new BookNotFoundException("Error in transfer Book. Book not found with id: " + bookId);
        }
        LOGGER.log(Level.INFO, "Transferring book: " + book.getTitle() + " to branch: " + branch.getBranchName());
        book.setBranchId(branchId);
    }

}
