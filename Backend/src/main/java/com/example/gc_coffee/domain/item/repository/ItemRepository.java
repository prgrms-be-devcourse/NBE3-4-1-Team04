package com.example.gc_coffee.domain.item.repository;

import com.example.gc_coffee.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOrderByIdDesc();
}
