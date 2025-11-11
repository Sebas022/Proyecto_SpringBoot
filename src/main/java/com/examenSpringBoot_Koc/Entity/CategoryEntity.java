package com.examenSpringBoot_Koc.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @Column(name = "categories_id")
    private int categoriesId;
    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private Set<MovieEntity> movies = new HashSet<>();
}
