package com.examenSpringBoot_Koc.Repository;

import com.examenSpringBoot_Koc.Entity.MovieEntity;
import com.examenSpringBoot_Koc.Entity.RateEntity;
import com.examenSpringBoot_Koc.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<RateEntity, Integer> {

        Optional<RateEntity> findByRatingId(int ratingId);
        Optional<RateEntity> findByUserEntityAndMovieEntity(UserEntity userEntity, MovieEntity movieEntity);
        List<RateEntity> findByUserEntity(UserEntity user);
}
