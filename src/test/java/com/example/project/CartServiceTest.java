package com.example.project;

import com.example.project.service.CartService;
import com.example.project.DTO.OrderDTO;
import com.example.project.DTO.UpdateOrderDTO;
import com.example.project.model.OrderItem;
import com.example.project.model.Product;
import com.example.project.model.ShoppingCart;
import com.example.project.repository.CartRepository;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddToCart() {
        // Verilen test verisi
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(Long.valueOf("123"));
        orderDTO.setQuantity(2);

        ShoppingCart cart = new ShoppingCart();
        cart.setUserId("testUser");

        Product product = new Product();
        product.setId(Long.valueOf("123"));

        // Mock verileri ayarla
        when(cartRepository.findByUserId(any())).thenReturn(Optional.of(cart));
        when(productRepository.findProductById(Long.valueOf("123"))).thenReturn(Optional.of(product));

        // Metodu çağır
        String result = cartService.addToCart(orderDTO);

        // Sonucun doğru olup olmadığını kontrol et
        assertEquals("SUCCESS", result);

        // CartRepository.save() metodunun çağrıldığını kontrol et
        verify(cartRepository, times(1)).save(cart);

        // OrderRepository.save() metodunun çağrıldığını kontrol et
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    public void testGetProducts() {
        // Verilen test verisi
        ShoppingCart cart = new ShoppingCart();
        cart.setId(Long.valueOf("testCartId"));

        Product product1 = new Product();
        product1.setId(Long.valueOf("1"));
        product1.setE_arrival("old");
        product1.setF_discount(50);

        Product product2 = new Product();
        product2.setId(Long.valueOf("2"));
        product2.setE_arrival("new");
        product2.setD_price("Rs.100");

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProduct(product1);
        orderItem1.setQuantity(2);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProduct(product2);
        orderItem2.setQuantity(3);
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        HashMap<String, Object> expectedHashMap = new HashMap<>();
        expectedHashMap.put("products", orderItemList);
        expectedHashMap.put("total", 350);

        // Mock verileri ayarla
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .thenReturn(new UserDetails() {
                    @Override
                    public String getUsername() {
                        return "testUser";
                    }

                    // Diğer UserDetails metotlarını burada implemente edebilirsiniz
                });
        when(cartRepository.findByUserId(any())).thenReturn(Optional.of(cart));
        when(orderRepository.findByCart_Id(Long.valueOf("testCartId"))).thenReturn(orderItemList);

        // Metodu çağır
        HashMap<String, Object> result = cartService.getProducts();

        // Sonucun doğru olup olmadığını kontrol et
        assertEquals(expectedHashMap, result);
    }

    @Test
    public void testUpdateOrder() {
        // Verilen test verisi
        UpdateOrderDTO updateOrderDTO = new UpdateOrderDTO();
        updateOrderDTO.setId("testOrderId");
        updateOrderDTO.setQuantity(5);

        OrderItem orderItem = new OrderItem();
        orderItem.setId("testOrderId");

        // Mock verileri ayarla
        when(orderRepository.findById("testOrderId")).thenReturn(Optional.of(orderItem));

        // Metodu çağır
        String result = cartService.updateOrder(updateOrderDTO);

        // Sonucun doğru olup olmadığını kontrol et
        assertEquals("SUCCESS", result);

        // OrderRepository.save() metodunun çağrıldığını kontrol et
        verify(orderRepository, times(1)).save(orderItem);
    }

    @Test
    public void testDeleteOrder() {
        // Verilen test verisi
        String orderId = "testOrderId";
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderId);

        // Mock verileri ayarla
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderItem));

        // Metodu çağır
        String result = cartService.deleteOrder(orderId);

        // Sonucun doğru olup olmadığını kontrol et
        assertEquals("DELETED", result);

        // OrderRepository.delete() metodunun çağrıldığını kontrol et
        verify(orderRepository, times(1)).delete(orderItem);
    }
}
