package com.rtrn.repository;

import com.rtrn.model.Item;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ItemRepository {
    private Map<Long, Item> items = new ConcurrentHashMap<>();
    private AtomicLong idCounter = new AtomicLong();

    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(idCounter.incrementAndGet());
        }
        items.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return items.get(id);
    }
}
