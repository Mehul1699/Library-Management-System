package com.library.entity;

public abstract class LibraryEntity {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LibraryEntity{" +
                "id=" + id +
                '}';
    }
}
