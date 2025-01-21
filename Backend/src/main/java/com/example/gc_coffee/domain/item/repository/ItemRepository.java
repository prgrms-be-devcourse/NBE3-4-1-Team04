package com.example.gc_coffee.domain.item.repository;

import com.example.gc_coffee.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByIsDeletedFalseOrderByIdDesc();

    Page<Item> findByItemNameLikeIgnoreCaseAndIsDeletedFalse(String searchKeyword, PageRequest pageRequest);

    Optional<Item> findFirstByOrderByIdDesc();
}
