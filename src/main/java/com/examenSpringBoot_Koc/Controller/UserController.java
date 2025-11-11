package com.examenSpringBoot_Koc.Controller;

import com.examenSpringBoot_Koc.Dto.Request.LoginRequest;
import com.examenSpringBoot_Koc.Dto.Request.UserRequest;
import com.examenSpringBoot_Koc.Dto.ResponseBase;
import com.examenSpringBoot_Koc.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "User Controller", description =
        "Controlador usado para manejar el login, create, update y delete de usuarios")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseBase register(@RequestBody UserRequest request){
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseBase login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PutMapping("/{userId}")
    public ResponseBase updateUser(@PathVariable int userId, @RequestBody UserRequest request) {
        return userService.updateUser(userId,request);
    }


    @DeleteMapping("/{userId}")
    public ResponseBase deleteUser(@PathVariable int userId) {
        return userService.deleteUser(userId);
    }
}
