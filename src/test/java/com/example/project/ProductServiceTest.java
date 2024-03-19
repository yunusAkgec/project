package com.example.project;

import com.example.project.service.ProductService;
import com.example.project.model.Product;
import com.example.project.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProductsByDressType() {
        // Verilen test verisi
        String dressType = "Regular Fit T-shirt";
        List<Product> productList = Arrays.asList(new Product(), new Product(), new Product());

        // Mock ProductRepository'ye belirli bir elbise türüne sahip ürünler döndürecek şekilde ayarla
        when(productRepository.findProductsByB_Dresstype(anyString())).thenReturn(productList);

        // Metodu çağır
        List<Product> result = productService.getProductsByDressType(dressType);

        // Sonucun doğru olup olmadığını kontrol et
        assertEquals(productList, result);
    }

    @Test
    public void testGetProductsBySex() {
        // Verilen test verisi
        String sex = "MALE";
        Integer pageSize = 10;
        Integer page = 0;
        Map<Boolean, Long> countBySex = new HashMap<>();
        countBySex.put(true, 50L);
        countBySex.put(false, 30L);

        List<Product> products = Arrays.asList(new Product(), new Product(), new Product());
        Page<Product> pageResult = new PageImpl<>(products);
        List<Product> newProds = Arrays.asList(new Product(), new Product());

        // Mock ProductRepository'ye belirli bir cinsiyete sahip ürünler ve yeni ürünler döndürecek şekilde ayarla
        when(productRepository.findProductByA_sex(sex, PageRequest.of(page, pageSize))).thenReturn(pageResult);
        when(productRepository.findProductsByE_arrivalAndA_sex("new", sex)).thenReturn(newProds);

        // Metodu çağır
        HashMap<String, Object> result = productService.getProductsBySex(sex, pageSize, page, countBySex);

        // Sonucun doğru olup olmadığını kontrol et
        assertEquals(page, result.get("current"));
        assertEquals(products, result.get("products"));
        assertEquals(pageResult.getTotalPages(), result.get("total"));
    }
}

