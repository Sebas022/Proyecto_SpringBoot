package com.examenSpringBoot_Koc.Dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Schema(description = "Email del usuario")
    private String email;
    @Schema(description = "Contraseña del usuario")
    private String password;
}
