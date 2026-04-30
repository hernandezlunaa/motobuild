package com.motobuild.repository;

import com.motobuild.model.PartCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartCategoryRepository extends JpaRepository<PartCategory, Integer> {
}