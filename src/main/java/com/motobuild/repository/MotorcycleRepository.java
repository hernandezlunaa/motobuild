package com.motobuild.repository;

import com.motobuild.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Integer> {
}
