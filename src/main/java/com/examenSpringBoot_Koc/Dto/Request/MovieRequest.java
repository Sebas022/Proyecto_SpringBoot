package com.examenSpringBoot_Koc.Dto.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieRequest {

    private String title;
    private String synopsis;
    private Integer releaseYear;
    private List<String> categoryNames;
}