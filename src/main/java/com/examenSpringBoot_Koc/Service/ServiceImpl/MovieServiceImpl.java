package com.examenSpringBoot_Koc.Service.ServiceImpl;

import com.examenSpringBoot_Koc.Dto.Request.MovieRequest;
import com.examenSpringBoot_Koc.Dto.Response.MovieResponse;
import com.examenSpringBoot_Koc.Dto.ResponseBase;
import com.examenSpringBoot_Koc.Entity.CategoryEntity;
import com.examenSpringBoot_Koc.Entity.MovieEntity;
import com.examenSpringBoot_Koc.Entity.UserEntity;
import com.examenSpringBoot_Koc.Repository.CategoryRepository;
import com.examenSpringBoot_Koc.Repository.MovieRepository;
import com.examenSpringBoot_Koc.Repository.UserRepository;
import com.examenSpringBoot_Koc.Service.MovieService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper movieMapper;

    @Override
    public ResponseBase createMovie(MovieRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

            if(userEntityOptional.isEmpty()){
                return new ResponseBase(404, "Usuario no encontrado",null);
            }
            UserEntity user = userEntityOptional.get();


            // Buscar categorías ya existentes en BD
            List<CategoryEntity> categories = categoryRepository.findByCategoryNameIn(request.getCategoryNames());
            if (categories.isEmpty()) {
                return new ResponseBase(404, "Categorías no encontradas", null);
            }

            MovieEntity movieEntity = new MovieEntity();
            movieEntity.setTitle(request.getTitle());
            movieEntity.setSynopsis(request.getSynopsis());
            movieEntity.setReleaseYear(request.getReleaseYear());
            movieEntity.setCategories(new HashSet<>(categories));
            movieEntity.setCreatedBy(user.getUserId());
            movieEntity.setUpdatedBy(user.getUserId());
            movieEntity.setUserEntity(user);

            movieRepository.save(movieEntity);

            MovieResponse response = movieMapper.map(movieEntity, MovieResponse.class);

            return new ResponseBase(201, "Película registrada correctamente", Optional.of(response));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBase(500, "Error interno del servidor", null);
        }
    }

    @Override
    public ResponseBase updateMovie(int movieId, MovieRequest request) {

        try{

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

            if(userEntityOptional.isEmpty()){
                return new ResponseBase(404, "Usuario no encontrado",null);
            }
            UserEntity user = userEntityOptional.get();

            Optional<MovieEntity> movieEntityOptional = movieRepository.findById(movieId);
            if(movieEntityOptional.isEmpty()){
                return new ResponseBase(404,"Película no encontrada",null);
            }

            Optional<MovieEntity> existingMovie = movieRepository.findByTitle(request.getTitle());
            if (existingMovie.isPresent()) {
                MovieEntity movieFound = existingMovie.get();
                if (movieFound.getMovieId() != movieId) {
                    return new ResponseBase(400, "Ya existe una película con ese nombre", null);
                }
            }

            List<CategoryEntity> categories = categoryRepository.findByCategoryNameIn(request.getCategoryNames());
            if(categories.isEmpty()){
                return new ResponseBase(404,"Categorías no encontradas",null);
            }

            MovieEntity movieEntity = movieEntityOptional.get();
            movieEntity.setTitle(request.getTitle());
            movieEntity.setSynopsis(request.getSynopsis());
            movieEntity.setReleaseYear(request.getReleaseYear());
            movieEntity.setCategories(new HashSet<>(categories));
            movieEntity.setUserEntity(user);
            movieEntity.setUpdatedBy(user.getUserId());

            movieRepository.save(movieEntity);

            MovieResponse response = movieMapper.map(movieEntity, MovieResponse.class);

            return new ResponseBase(201,"Película actualizada correctamente",Optional.of(response));
        } catch (Exception e) {
            return new ResponseBase(500,"Error interno del servidor");
        }
    }

    @Override
    @Transactional

    public ResponseBase deleteMovie(String title) {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

            if(userEntityOptional.isEmpty()){
                return new ResponseBase(404, "Usuario no encontrado",null);
            }

            UserEntity user = userEntityOptional.get();

            Optional<MovieEntity> movieEntityOptional = movieRepository.findByTitle(title);
            if(movieEntityOptional.isEmpty()){
                return new ResponseBase(404,"Película no encontrada",null);
            }

            MovieEntity movie = movieEntityOptional.get();

            movie.getCategories().clear();
            movieRepository.deleteByTitle(title);

            return new ResponseBase(200,"Película eliminada correctamente");
        } catch (Exception e) {
            return new ResponseBase(500,"Error interno del servidor");
        }
    }

    @Override
    public ResponseBase getMovieByTitle(String title) {
        try{
            Optional<MovieEntity> movieEntityOptional = movieRepository.findByTitle(title);

            if(movieEntityOptional.isEmpty()){
                return new ResponseBase(404,"Película no encontrada",null);
            }

            MovieEntity movieEntity = movieEntityOptional.get();
            MovieResponse response = movieMapper.map(movieEntity, MovieResponse.class);

            return new ResponseBase(200,"Película encontrada", Optional.of(response));
        } catch (Exception e) {
            return new ResponseBase(500, "Error interno del servidor", null);
        }
    }

    @Override
    public ResponseBase getAllMovies(String title, String categoryName, Integer releaseYear,
                                     int page, int size, String sortBy, String sortDir) {
        try {
            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<MovieEntity> movies;

            if (title != null && !title.trim().isEmpty()) {
                movies = movieRepository.findByTitleContainingIgnoreCase(title, pageable);
            } else if (categoryName != null && !categoryName.trim().isEmpty()) {
                movies = movieRepository.findByCategoriesCategoryNameContainingIgnoreCase(categoryName, pageable);
            } else if (releaseYear != null) {
                movies = movieRepository.findByReleaseYear(releaseYear, pageable);
            } else {
                movies = movieRepository.findAll(pageable);
            }

            List<MovieResponse> responseList = new ArrayList<>();
            for (MovieEntity movie : movies.getContent()) {
                MovieResponse response = movieMapper.map(movie, MovieResponse.class);
                responseList.add(response);
            }

            return new ResponseBase(200, "Películas encontradas", Optional.of(responseList));

        } catch (Exception e) {
            return new ResponseBase(500, "Error interno", null);
        }
    }

}
