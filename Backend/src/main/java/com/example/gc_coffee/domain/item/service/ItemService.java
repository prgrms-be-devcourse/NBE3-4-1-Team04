package com.example.gc_coffee.domain.item.service;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.repository.ItemRepository;
import com.example.gc_coffee.global.exceptions.BusinessException;
import com.example.gc_coffee.global.exceptions.constant.ErrorCode;
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

    public Item addItem(Category category, String itemName, int itemPrice, String itemImage, int quantity, String description) {
        Item item = Item.builder()
                .category(category)
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemImage(itemImage)
                .quantity(quantity)
                .itemDescription(description)
                .build();

        return itemRepository.save(item);
    }

    public Long count() {
        return itemRepository.count();
    }

    public List<Item> findAllByOrderByIdDesc() {
        return itemRepository.findAllByOrderByIdDesc();
    }

    // (조회) Item 전체 조회 및, 상품 이름 검색
    public Page<Item> findByPaged(String searchKeyword, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("id")));

        if (Ut.str.isBlank(searchKeyword)) { return itemRepository.findAll(pageRequest); }

        searchKeyword = "%" + searchKeyword + "%";

        return itemRepository.findByItemNameLikeIgnoreCase(searchKeyword, pageRequest);
    }

    public Optional<Item> findById(long itemId) {
        return itemRepository.findById(itemId);
    }



    public Item modify(long itemId, Category category, String itemName, int itemPrice, String itemImage, int itemQuantity, String description) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));

        item.setCategory(category);
        item.setItemName(itemName);
        item.setItemPrice(itemPrice);
        item.setItemImage(itemImage);
        item.setQuantity(itemQuantity);
        item.setItemDescription(description);

        return itemRepository.save(item);
    }

    public void delete(long itemId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));

        itemRepository.delete(item);
    }

    public void flush() {
        itemRepository.flush();
    }

    public Optional<Item> fineLatest() {
        return itemRepository.findFirstByOrderByIdDesc();
    }
}
