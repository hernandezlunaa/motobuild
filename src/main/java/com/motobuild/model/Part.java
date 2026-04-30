package com.motobuild.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "parts")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Integer partId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private PartCategory category;

    @Column(name = "part_name", nullable = false, length = 120)
    private String partName;

    @Column(name = "brand", nullable = false, length = 80)
    private String brand;

    @Column(name = "part_number", length = 80)
    private String partNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "install_difficulty")
    private String installDifficulty;

    @Column(name = "image_url")
    private String imageUrl;

}