package com.examenSpringBoot_Koc.Controller;

import com.examenSpringBoot_Koc.Dto.Request.RateRequest;
import com.examenSpringBoot_Koc.Dto.ResponseBase;
import com.examenSpringBoot_Koc.Service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rate")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping("/save")
    public ResponseEntity<ResponseBase> createRate(@RequestBody RateRequest rateRequest){
        ResponseBase response = rateService.createRate(rateRequest);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<ResponseBase> deleteRate(@RequestParam String title){
        ResponseBase response = rateService.deleteRate(title);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseBase> rateList(){
        ResponseBase response = rateService.getRatesByUser();
        return ResponseEntity.status(response.getCodigo()).body(response);
    }
}
