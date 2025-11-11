package com.examenSpringBoot_Koc.Controller;

import com.examenSpringBoot_Koc.Dto.Request.MovieRequest;
import com.examenSpringBoot_Koc.Dto.ResponseBase;
import com.examenSpringBoot_Koc.Service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;


    @PostMapping("/save")
    public ResponseEntity<ResponseBase> createMovie(@RequestBody MovieRequest request) {
        ResponseBase response = movieService.createMovie(request);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<ResponseBase> updateMovie(@PathVariable int movieId,
                                                    @RequestBody MovieRequest request) {
        ResponseBase response = movieService.updateMovie(movieId, request);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseBase> deleteMovie(@RequestParam String title) {
        ResponseBase response = movieService.deleteMovie(title);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @GetMapping("/{title}")
    public ResponseEntity<ResponseBase> getMovieById(@PathVariable String title) {
        ResponseBase response = movieService.getMovieByTitle(title);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseBase> getAllMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "movieId") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir) {

        ResponseBase response = movieService.getAllMovies(title, categoryName, releaseYear,
                page, size, sortBy, sortDir);

        return ResponseEntity.status(response.getCodigo()).body(response);
    }
}