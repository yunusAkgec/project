package com.example.project.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShoppingCart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private String userId;


    @OneToMany( mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private Set<OrderItem> items = new HashSet<>();



}