package com.bugtracker.security.util;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;

public class DedupingObjectIdResolver extends SimpleObjectIdResolver {
    @Override
    public void bindItem(IdKey id, Object ob) {
        if (_items == null) {
            _items = new HashMap<>();
        }
        _items.put(id, ob);
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        return new DedupingObjectIdResolver();
    }
}