package com.example.gc_coffee.domain.item.service;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.repository.ItemRepository;
import com.example.gc_coffee.global.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item addItem(Category category, String itemName, int itemPrice, String itemImage, int quantity) {
        Item item = Item.builder()
                .category(category)
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemImage(itemImage)
                .quantity(quantity)
                .build();

        return itemRepository.save(item);
    }

    public Long count() {
        return itemRepository.count();
    }

    public List<Item> findAllByOrderByIdDesc() {
        return itemRepository.findAllByOrderByIdDesc();
    }

    // (조회) 검색어가 없을 경우, 전체 조회
    public Page<Item> findByPaged(String searchKeyword, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("id")));
        return itemRepository.findAll(pageRequest);
    }

    // (조회) %상품명% 조회
    public Page<Item> findByPaged(String searchKeywordType, String searchKeyword, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("id")));

        if (Ut.str.isBlank(searchKeyword)) { return itemRepository.findAll(pageRequest); }

        searchKeyword = "%" + searchKeyword + "%";

        return itemRepository.findByItemNameLikeIgnoreCase(searchKeyword, pageRequest);
    }

    public Optional<Item> findById(long itemId) {
        return itemRepository.findById(itemId);
    }
}
