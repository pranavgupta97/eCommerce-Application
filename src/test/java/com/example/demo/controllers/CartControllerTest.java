package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;

    private CartRepository cartRepository = mock(CartRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);

    }

    @Test
    public void add_to_cart_happy_path() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("Bruh");
        modifyCartRequest.setItemId(0);
        modifyCartRequest.setQuantity(9);

        User user = new User();
        user.setId(0);
        user.setUsername("Bruh");
        user.setPassword("BruhDudeBruh");

        Cart cart = new Cart();
        user.setCart(cart);
        Item item = new Item();item.setId((long) 0);
        item.setName("Tom Ford Bruh");
        item.setPrice(BigDecimal.valueOf(69.420));
        item.setDescription("This is the real deal bruhszz!");

        Optional<Item> optionalItem = Optional.of(item);

        when(userRepository.findByUsername(modifyCartRequest.getUsername())).thenReturn(user);

        when(itemRepository.findById(modifyCartRequest.getItemId())).thenReturn(optionalItem);


        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(modifyCartRequest);

        assertNotNull(cartResponseEntity);
        assertEquals(200, cartResponseEntity.getStatusCodeValue());
        assertEquals(BigDecimal.valueOf((69.420 * 9)), cartResponseEntity.getBody().getTotal());
        assertEquals(9, cartResponseEntity.getBody().getItems().size());

    }
}
