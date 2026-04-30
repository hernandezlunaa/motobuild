package com.motobuild.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "motorcycles")
public class Motorcycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "motorcycle_id")
    private Integer motorcycleId;

    @Column(name = "make", nullable = false, length = 50)
    private String make;

    @Column(name = "model", nullable = false, length = 80)
    private String model;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "engine_cc")
    private Integer engineCc;

    @Column(name = "bike_type", length = 50)
    private String bikeType;

    @Column(name = "image_url")
    private String imageUrl;

    public Motorcycle() {
    }

}
