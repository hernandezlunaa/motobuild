package com.motobuild.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "part_categories")
public class PartCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name", nullable = false, unique = true, length = 75)
    private String categoryName;

    @Column(name = "description")
    private String description;
}