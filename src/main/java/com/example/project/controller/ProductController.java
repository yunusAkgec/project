package com.example.project.controller;

import com.example.project.model.Product;
import com.example.project.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "dresses")
    public HashMap<String, Object> getProductsByDressTypeOnSearch(@RequestParam(name = "dress") String dress){
        HashMap<String, Object> hs = new HashMap<>();
        List<Product> products = productService.getProductsByDressType(dress);
        hs.put("products", products);
        hs.put("length" , products.size());
        return hs;
    }


    @GetMapping( path = "clothes")
    public HashMap<String, Object> getProductsBySexAndSession(
            @RequestParam(name = "sex") String sex,
            @RequestParam(defaultValue = "10") Integer items,
            @RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request){

        List<String> messages = (List<String>) request.getSession().getAttribute("SESSION_STORE");

        if ( messages == null){
            messages = new ArrayList<>();
        }

        Map<Boolean, Long> countBySex = messages.stream().collect(
                Collectors.partitioningBy(
                        (String msg) -> (msg.equals("O")), Collectors.counting() ));

        return productService.getProductsBySex(sex,  items, page, countBySex);

    }




}