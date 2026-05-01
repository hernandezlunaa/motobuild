package com.motobuild.repository;

import com.motobuild.model.Build;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BuildRepository extends JpaRepository<Build, Integer> {

    @EntityGraph(attributePaths = {"motorcycle", "buildParts", "buildParts.part", "buildParts.part.category"})
    @Query("""
        SELECT DISTINCT b
        FROM Build b
        WHERE b.user.userId = :userId
        ORDER BY b.createdAt DESC
    """)
    List<Build> findBuildsForUserWithDetails(@Param("userId") Integer userId);

    @EntityGraph(attributePaths = {"user", "motorcycle", "buildParts", "buildParts.part", "buildParts.part.category"})
    @Query("""
        SELECT DISTINCT b
        FROM Build b
        WHERE b.buildId = :buildId
    """)
    Optional<Build> findBuildWithDetails(@Param("buildId") Integer buildId);
}