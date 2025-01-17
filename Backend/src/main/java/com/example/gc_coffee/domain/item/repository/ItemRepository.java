package com.example.gc_coffee.domain.item.repository;

import com.example.gc_coffee.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOrderByIdDesc();

    Page<Item> findByItemNameLikeIgnoreCase(String searchKeyword, PageRequest pageRequest);

    Page<Item> findByEmailLikeIgnoreCase(String searchKeyword, PageRequest pageRequest);

    Page<Item> findByZipCodeLikeIgnoreCase(String searchKeyword, PageRequest pageRequest);
}
