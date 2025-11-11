package com.examenSpringBoot_Koc.Service.ServiceImpl;

import com.examenSpringBoot_Koc.Dto.Request.RateRequest;
import com.examenSpringBoot_Koc.Dto.Response.RateResponse;
import com.examenSpringBoot_Koc.Dto.ResponseBase;
import com.examenSpringBoot_Koc.Entity.MovieEntity;
import com.examenSpringBoot_Koc.Entity.RateEntity;
import com.examenSpringBoot_Koc.Entity.UserEntity;
import com.examenSpringBoot_Koc.Repository.MovieRepository;
import com.examenSpringBoot_Koc.Repository.RateRepository;
import com.examenSpringBoot_Koc.Repository.UserRepository;
import com.examenSpringBoot_Koc.Service.RateService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RateServiceImpl implements RateService {

    private MovieRepository movieRepository;
    private UserRepository userRepository;
    private RateRepository rateRepository;
    private ModelMapper rateMapper;

    @Override
    public ResponseBase createRate(RateRequest rateRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()){
            return new ResponseBase(404,"Usuario no encontrado");
        }

        UserEntity userEntity = userOpt.get();

        String title = rateRequest.getTitle();
        Optional<MovieEntity> movieOpt = movieRepository.findByTitle(title);
        if(movieOpt.isEmpty()){
            return new ResponseBase(404,"Película no encontrada");
        }


        MovieEntity movieEntity = movieOpt.get();


        if(rateRequest.getRating() < 1 || rateRequest.getRating() > 5){
            return new ResponseBase(400,"La calificación debe de ser entre 1 y 5");
        }

        Optional<RateEntity> rateOpt = rateRepository.findByUserEntityAndMovieEntity(userEntity, movieEntity);
        if(rateOpt.isPresent()){
            return new ResponseBase(400,"Ya calificaste esta película");
        }

        RateEntity rateEntity = new RateEntity();
        rateEntity.setRating(rateRequest.getRating());
        rateEntity.setUserEntity(userEntity);
        rateEntity.setMovieEntity(movieEntity);

        rateRepository.save(rateEntity);

        RateResponse rateResponse = rateMapper.map(rateEntity, RateResponse.class);

        return new ResponseBase(201,"La película se calificó correctamente",Optional.of(rateResponse));
    }

    @Override
    public ResponseBase getRatesByUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()){
            return new ResponseBase(404,"Usuario no encontrado");
        }

        UserEntity userEntity = userOpt.get();

        List<RateEntity> rateEntityList = rateRepository.findByUserEntity(userEntity);

        if(rateEntityList.isEmpty()){
            return new ResponseBase(200,"No tienes películas calificadas",Optional.empty());
        }

        List<RateResponse> responseList = new ArrayList<>();
        for(RateEntity rate : rateEntityList){
            RateResponse rateResponse = rateMapper.map(rate, RateResponse.class);
            responseList.add(rateResponse);
        }

        return new ResponseBase(200,"Calificaciones encontradas",Optional.of(responseList));
    }

    @Override
    public ResponseBase deleteRate(String title) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()){
            return new ResponseBase(404,"Usuario no encontrado");
        }
        UserEntity userEntity = userOpt.get();

        Optional<MovieEntity> movieOpt = movieRepository.findByTitle(title);
        if(movieOpt.isEmpty()){
            return new ResponseBase(404, "Película no encontrada");
        }
        MovieEntity movieEntity = movieOpt.get();

        Optional<RateEntity> rateOpt = rateRepository.findByUserEntityAndMovieEntity(userEntity, movieEntity);
        if(rateOpt.isEmpty()){
            return new ResponseBase(404,"No has calificado esta película");
        }

        RateEntity rateEntity = rateOpt.get();

        RateResponse rateResponse = rateMapper.map(rateEntity, RateResponse.class);

        rateRepository.delete(rateEntity);

        return new ResponseBase(200,"Calificación eliminada correctamente",Optional.of(rateResponse));
    }
}
