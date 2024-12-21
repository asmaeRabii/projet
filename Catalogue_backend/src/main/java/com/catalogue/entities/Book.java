package com.catalogue.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true, nullable = false) // L'ISBN doit être unique
    private String isbn;

    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private String imageurl;

    @Column(nullable = false)
    private int stock;





}