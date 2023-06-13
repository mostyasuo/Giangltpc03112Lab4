package com.example.demo.service;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.DB.DB;
import com.example.demo.model.Item;
import com.example.demo.model.ShoppingCartService;

import lombok.Data;
@Data
@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private Map<Integer, Item> map = new HashMap<>();
    ShoppingCartServiceImpl cartServiceImpl;
    @Override
    public Item add(Integer id) {
        Item item = map.get(id);
        if (item == null) {
            // Mặt hàng chưa tồn tại trong giỏ hàng
            // Thêm mặt hàng vào giỏ hàng với số lượng ban đầu là 1
            DB db = new DB(); // Tạo đối tượng DB
            Map<Integer, Item> dbItems = db.items; // Lấy danh sách mặt hàng từ DB
            Item dbItem = dbItems.get(id); // Lấy mặt hàng từ DB dựa trên ID
            
            if (dbItem != null) {
                // Copy thông tin từ DBItem sang item mới
                item = new Item(dbItem.getId(), dbItem.getName(), dbItem.getPrice(), 1);
                map.put(id, item);
            }
        } else {
            // Mặt hàng đã tồn tại trong giỏ hàng
            // Tăng số lượng lên 1
            item.setQty(item.getQty() + 1);
        }
        return item;
    }


    @Override
    public void remove(Integer id) {
        map.remove(id);
    }

    @Override
    public Item update(Integer id, int qty) {
        Item item = map.get(id);
        if (item != null) {
            // Cập nhật số lượng của mặt hàng
            item.setQty(qty);
        }
        return item;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Collection<Item> getItems() {
        return map.values();
    }

    @Override
    public int getCount() {
        int count = 0;
        for (Item item : map.values()) {
            count += item.getQty();
        }
        return count;
    }

    @Override
    public double getAmount() {
        double amount = 0.0;
        for (Item item : map.values()) {
            amount += item.getPrice() * item.getQty();
        }
        return amount;
    }
}

