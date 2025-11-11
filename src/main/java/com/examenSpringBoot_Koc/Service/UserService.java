package com.examenSpringBoot_Koc.Service;

import com.examenSpringBoot_Koc.Dto.Request.LoginRequest;
import com.examenSpringBoot_Koc.Dto.Request.UserRequest;
import com.examenSpringBoot_Koc.Dto.ResponseBase;

public interface UserService {

    ResponseBase registerUser(UserRequest request);
    ResponseBase login(LoginRequest loginRequest);
    ResponseBase updateUser(int userId, UserRequest request);
    ResponseBase deleteUser(int userId);
}
