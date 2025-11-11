package com.examenSpringBoot_Koc.Repository;

import com.examenSpringBoot_Koc.Entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {

    Page<MovieEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<MovieEntity> findByCategoriesCategoryNameContainingIgnoreCase(String categoryName, Pageable pageable);
    Page<MovieEntity> findByReleaseYear(int releaseYear, Pageable pageable);
    Optional<MovieEntity> findByTitle(String title);
    void deleteByTitle(String title);

}
