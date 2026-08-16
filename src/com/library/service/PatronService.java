package com.library.service;

import com.library.entity.Patron;
import com.library.exception.InvalidDataException;
import com.library.exception.PatronNotFoundException;
import com.library.util.DataStore;
import com.library.util.IdGenerator;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatronService {

    private DataStore<Patron> patronDataStore = new DataStore<>();
    private static final Logger LOGGER = Logger.getLogger(PatronService.class.getName());

    public Patron addPatron(Patron patron) {
        if (Objects.isNull(patron)) {
            LOGGER.log(Level.WARNING, "Patron data can't be null");
            throw new InvalidDataException("Patron data can't be null");
        }
        patron.setId(IdGenerator.idGenerator().nextPatronId());
        patronDataStore.addData(patron);
        return patron;
    }

    public Patron search(int id) {
        Patron patron = patronDataStore.getById(id);
        if (Objects.nonNull(patron)) {
            return patron;
        }
        LOGGER.log(Level.WARNING, "Patron data not found with id: " + id);
        throw new PatronNotFoundException("Patron data not found with id: " + id);
    }

    public List<Patron> search(String name) {
        if (name == null || name.isEmpty()) {
            LOGGER.log(Level.WARNING, "Patron name can't be null or empty");
            throw new PatronNotFoundException("Patron name can't be null or empty");
        }
        return patronDataStore.getAll().stream()
                .filter(patron -> patron.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

}
