package com.library.util;

import com.library.entity.LibraryEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore<T extends LibraryEntity> {

    private List<T> data = new ArrayList<>();
    private Map<Integer, T> dataMap = new HashMap<>();

    public void addData(T obj) {
        data.add(obj);
        dataMap.put(obj.getId(), obj);
    }

    public T getById(int id) {
        return dataMap.get(id);
    }

    public List<T> getAll() {
        return data;
    }

    public void remove(T obj) {
        data.remove(obj);
        dataMap.remove(obj.getId());
    }

}
