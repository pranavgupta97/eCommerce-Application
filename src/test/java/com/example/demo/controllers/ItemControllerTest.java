package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();

        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void get_items_happy_path() {
        Item itemOne = new Item();
        itemOne.setId((long) 0);
        itemOne.setName("Mr. Burberry Bruh");
        itemOne.setPrice(BigDecimal.valueOf(100.00));
        itemOne.setDescription("The scent is EDP and it is LIT Bruh!");

        Item itemTwo = new Item();
        itemTwo.setId((long) 1);
        itemTwo.setName("House 69 Bruhszz!");
        itemTwo.setPrice(BigDecimal.valueOf(36.42069));
        itemTwo.setDescription("Inspired by David Beckham Bruhszz!");

        List<Item> items = new ArrayList<>();
        items.add(itemOne);
        items.add(itemTwo);

        when(itemRepository.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> itemsResponseEntity = itemController.getItems();


        assertNotNull(itemsResponseEntity);

        assertEquals(200, itemsResponseEntity.getStatusCodeValue());

        assertEquals(2, itemsResponseEntity.getBody().size());


    }
}