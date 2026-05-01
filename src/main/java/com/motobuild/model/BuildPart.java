package com.motobuild.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "build_parts")
public class BuildPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build_part_id")
    private Integer buildPartId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "build_id", nullable = false)
    private Build build;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "part_id", nullable = false)
    private Part part;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "planned";

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

    @Column(name = "custom_price")
    private BigDecimal customPrice;

    @Column(name = "notes")
    private String notes;

    @Column(name = "added_at", insertable = false, updatable = false)
    private LocalDateTime addedAt;

    public BuildPart() {
    }

    @Transient
    public BigDecimal getEffectivePrice() {
        if (customPrice != null) {
            return customPrice;
        }

        if (part != null && part.getPrice() != null) {
            return part.getPrice();
        }

        return BigDecimal.ZERO;
    }

    @Transient
    public BigDecimal getLineTotal() {
        int safeQuantity = quantity == null ? 1 : quantity;
        return getEffectivePrice().multiply(BigDecimal.valueOf(safeQuantity));
    }
}