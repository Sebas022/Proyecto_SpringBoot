package com.examenSpringBoot_Koc.Dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieResponse {
        private Integer movieId;
        private String title;
        private Integer releaseYear;
        private String synopsis;
        private List<String> categoryNames;
}
