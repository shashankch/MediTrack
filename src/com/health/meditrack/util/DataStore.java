
package com.health.meditrack.util;

import com.health.meditrack.exception.InvalidDataException;
import java.util.*;

public class DataStore<T> {

    private final Map<String, T> map = new HashMap<>();

    public synchronized T save(String id, T obj) {
        map.put(id, obj);
        return map.get(id);
    }

    public synchronized T get(String id) {
        return map.get(id);
    }

    public synchronized List<T> all() {
        return new ArrayList<>(map.values());
    }

    public synchronized T delete(String id) {
        return map.remove(id);
    }

    public synchronized T update(String id, T updatedObj) {

        if (!map.containsKey(id)) {
            throw new InvalidDataException("Entity not found with id: " + id);
        }
        map.put(id, updatedObj);
        return map.get(id);
    }
}
