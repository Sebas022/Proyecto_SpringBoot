package com.examenSpringBoot_Koc.Config;

import com.examenSpringBoot_Koc.Dto.Response.MovieResponse;
import com.examenSpringBoot_Koc.Dto.Response.RateResponse;
import com.examenSpringBoot_Koc.Entity.CategoryEntity;
import com.examenSpringBoot_Koc.Entity.MovieEntity;
import com.examenSpringBoot_Koc.Entity.RateEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class MapperConfig {

    @Bean(name = "movieMapper")
    public ModelMapper movieMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.createTypeMap(MovieEntity.class, MovieResponse.class)
                .addMapping(MovieEntity::getTitle, MovieResponse::setTitle)
                .addMapping(MovieEntity::getSynopsis, MovieResponse::setSynopsis)
                .addMapping(MovieEntity::getReleaseYear, MovieResponse::setReleaseYear)
                .addMappings(mapper -> mapper.using(ctx -> {
                    Set<CategoryEntity> categories = (Set<CategoryEntity>) ctx.getSource();
                    if (categories == null) return List.of();
                    return categories.stream()
                            .map(CategoryEntity::getCategoryName)
                            .toList();
                }).map(MovieEntity::getCategories, MovieResponse::setCategoryNames));
        return modelMapper;
    }

    @Bean(name = "rateMapper")
    public ModelMapper rateMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.createTypeMap(RateEntity.class, RateResponse.class)
                .addMapping(RateEntity::getRating, RateResponse::setRating)
                .addMapping(me -> me.getMovieEntity().getTitle(), RateResponse::setTitle);
        return modelMapper;
    }
}
