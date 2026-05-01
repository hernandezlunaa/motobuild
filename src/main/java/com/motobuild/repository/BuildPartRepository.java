package com.motobuild.repository;

import com.motobuild.model.BuildPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildPartRepository extends JpaRepository<BuildPart, Integer> {
    Optional<BuildPart> findByBuild_BuildIdAndPart_PartId(Integer buildId, Integer partId);
}