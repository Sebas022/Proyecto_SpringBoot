package com.examenSpringBoot_Koc.Repository;

import com.examenSpringBoot_Koc.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findById(int categoryId);
    List<CategoryEntity> findByCategoryNameIn(List<String> categoryNames);
}
