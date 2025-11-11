package com.examenSpringBoot_Koc.Service;

import com.examenSpringBoot_Koc.Dto.Request.RateRequest;
import com.examenSpringBoot_Koc.Dto.ResponseBase;

public interface RateService {

    ResponseBase createRate(RateRequest rateRequest);
    ResponseBase getRatesByUser();
    ResponseBase deleteRate(String title);

}
