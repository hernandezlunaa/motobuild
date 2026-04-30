package com.motobuild.repository;

import com.motobuild.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {

    @Query("""
        SELECT p
        FROM Part p
        WHERE (:search IS NULL OR :search = ''
            OR LOWER(p.partName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:categoryId IS NULL OR p.category.categoryId = :categoryId)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        ORDER BY
            CASE WHEN :sort = 'priceAsc' THEN p.price END ASC,
            CASE WHEN :sort = 'priceDesc' THEN p.price END DESC,
            p.category.categoryName ASC,
            p.partName ASC
    """)
    List<Part> filterParts(
            @Param("search") String search,
            @Param("categoryId") Integer categoryId,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("sort") String sort
    );
}