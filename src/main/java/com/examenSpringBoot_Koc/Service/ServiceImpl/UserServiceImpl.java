package com.examenSpringBoot_Koc.Service.ServiceImpl;

import com.examenSpringBoot_Koc.Dto.Request.LoginRequest;
import com.examenSpringBoot_Koc.Dto.Request.UserRequest;
import com.examenSpringBoot_Koc.Dto.ResponseBase;
import com.examenSpringBoot_Koc.Entity.RoleEntity;
import com.examenSpringBoot_Koc.Entity.UserEntity;
import com.examenSpringBoot_Koc.Repository.RoleRepository;
import com.examenSpringBoot_Koc.Repository.UserRepository;
import com.examenSpringBoot_Koc.Service.JwtService;
import com.examenSpringBoot_Koc.Service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
        private final UserDetailsService userDetailService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           AuthenticationManager authenticationManager, JwtService jwtService,
                           UserDetailsService userDetailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
    }

    @Override
    public ResponseBase registerUser(UserRequest request) {

        try{
            String email = request.getEmail();
            String roleName = request.getRoleName();
            Optional<UserEntity> userOptional = userRepository.findByEmail(email);
            Optional<RoleEntity> roleOptional = roleRepository.findByRoleName(roleName);

            if(userOptional.isPresent()){
                return new ResponseBase(400,"Email ya existe",Optional.empty());
            }
            if(roleOptional.isEmpty()){
                return new ResponseBase(400,"Rol no encontrado",Optional.empty());
            }

            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(request.getEmail());
            userEntity.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
            RoleEntity roleEntity = roleOptional.get();
            userEntity.setRoleEntity(roleEntity);

            userRepository.save(userEntity);

            return new ResponseBase(200,"Usuario registrado correctamente",Optional.empty());
        } catch (Exception e) {
            return new ResponseBase(500, "Error interno del servidor", Optional.empty());
        }

    }

    @Override
    public ResponseBase login(LoginRequest loginRequest) {
        try{
             System.out.println("=== INICIO LOGIN ===");
            System.out.println("Email del request: '" + loginRequest.getEmail() + "'");
            System.out.println("Password del request: '" + loginRequest.getPassword() + "'");

            System.out.println("=== INTENTANDO AUTENTICAR ===");
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));
            System.out.println("=== AUTENTICACIÓN EXITOSA ===");
        if (auth.isAuthenticated()){
            UserDetails user = userDetailService.loadUserByUsername(loginRequest.getEmail());
            String token = jwtService.generateToken(user);
            return new ResponseBase(200,"Login exitoso",Optional.of(token));
        }

        return new ResponseBase(401,"Credenciales incorrectas",Optional.empty());

        } catch (Exception e) {

            return new ResponseBase(500, "Error interno del servidor", Optional.empty());
        }

    }

    @Override
    public ResponseBase updateUser(int userId, UserRequest request) {
        try {

            Optional<UserEntity> userOptional = userRepository.findById(userId);
            if(userOptional.isEmpty()){
                return new ResponseBase(404, "Usuario no encontrado", Optional.empty());
            }

            Optional<UserEntity> email = userRepository.findByEmail(request.getEmail());
            if(email.isPresent() && email.get().getUserId() != userId){
                return new ResponseBase(400, "Email ya está en uso por otro usuario", Optional.empty());
            }

            Optional<RoleEntity> roleOptional = roleRepository.findByRoleName(request.getRoleName());
            if(roleOptional.isEmpty()){
                return new ResponseBase(400, "Rol no encontrado", Optional.empty());
            }

            UserEntity userEntity = userOptional.get();
            userEntity.setEmail(request.getEmail());


            if(request.getPassword() != null && !request.getPassword().isEmpty()){
                userEntity.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
            }

            RoleEntity roleEntity = roleOptional.get();
            userEntity.setRoleEntity(roleEntity);
            userRepository.save(userEntity);

            return new ResponseBase(200, "Usuario actualizado correctamente", Optional.empty());

        } catch (Exception e) {
            return new ResponseBase(500, "Error interno del servidor", Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteUser(int userId) {
        try{
            Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
            if(optionalUserEntity.isEmpty()){
                return new ResponseBase(404, "Usuario no encontrado",Optional.empty());
            }
            userRepository.deleteById(userId);
            return new ResponseBase(200,"Usuario eliminado correctamente",Optional.empty());
        } catch (Exception e) {
            return new ResponseBase(500, "Error interno del servidor", Optional.empty());
        }
    }

}
