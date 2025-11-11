package com.examenSpringBoot_Koc.Service;

import com.examenSpringBoot_Koc.Dto.ResponseBase;
import com.examenSpringBoot_Koc.Dto.Request.*;

public interface MovieService {

    ResponseBase createMovie(MovieRequest request);
    ResponseBase updateMovie(int movieId, MovieRequest request);
    ResponseBase deleteMovie(String title);
    ResponseBase getMovieByTitle(String title);
    ResponseBase getAllMovies(String title, String categoryName, Integer releaseYear,
                              int page, int size, String sortBy, String sortDir);
}
