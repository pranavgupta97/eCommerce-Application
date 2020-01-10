package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
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

public class OrderControllerTest {

    private OrderController orderController;

    private OrderRepository orderRepository = mock(OrderRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
    }

    @Test
    public void submit_order_happy_path() {
        User user = new User();
        user.setId(0);
        user.setUsername("Bruh");
        user.setPassword("BruhDudeBruh");

        Item item = new Item();
        item.setId((long) 0);
        item.setName("Tom Ford Bruh");
        item.setPrice(BigDecimal.valueOf(69.421));
        item.setDescription("This is the real deal bruhszz!");

        Cart cart = new Cart();
        cart.setId((long) 0);
        cart.setUser(user);

        List<Item> items = new ArrayList<Item>();
        items.add(item);
        items.add(item);
        items.add(item);

        cart.setItems(items);
        cart.setTotal(BigDecimal.valueOf((69.421) * items.size()));

        user.setCart(cart);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<UserOrder> userOrderResponseEntity = orderController.submit(user.getUsername());

        assertNotNull(userOrderResponseEntity);
        assertEquals(200, userOrderResponseEntity.getStatusCodeValue());
        assertEquals(BigDecimal.valueOf((69.421) * items.size()), userOrderResponseEntity.getBody().getTotal());


    }

    @Test
    public void getOrdersForUser_happy_path() {

        User user = new User();
        user.setId(0);
        user.setUsername("Bruh");
        user.setPassword("BruhDudeBruh");

        Item item = new Item();
        item.setId((long) 0);
        item.setName("Tom Ford Bruh");
        item.setPrice(BigDecimal.valueOf(69.421));
        item.setDescription("This is the real deal bruhszz!");

        Cart cart = new Cart();
        cart.setId((long) 0);
        cart.setUser(user);

        List<Item> items = new ArrayList<Item>();
        items.add(item);
        items.add(item);
        items.add(item);

        cart.setItems(items);
        cart.setTotal(BigDecimal.valueOf((69.421) * items.size()));

        user.setCart(cart);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        UserOrder userOrder = UserOrder.createFromCart(cart);

        List<UserOrder> userOrders = new ArrayList<UserOrder>();
        userOrders.add(userOrder);

        when(orderRepository.findByUser(user)).thenReturn(userOrders);

        ResponseEntity<List<UserOrder>> userOrdersResponseEntity = orderController.getOrdersForUser(user.getUsername());

        assertNotNull(userOrdersResponseEntity);

        assertEquals(200, userOrdersResponseEntity.getStatusCodeValue());

        assertEquals(1, userOrdersResponseEntity.getBody().size());

    }
}