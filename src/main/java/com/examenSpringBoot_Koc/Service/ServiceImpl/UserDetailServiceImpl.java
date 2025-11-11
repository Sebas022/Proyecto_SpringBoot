package com.examenSpringBoot_Koc.Service.ServiceImpl;

import com.examenSpringBoot_Koc.Entity.UserEntity;
import com.examenSpringBoot_Koc.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> optionalUsuarioEntity = userRepository.findByEmail(username);
        if(optionalUsuarioEntity.isEmpty()){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        UserEntity usuarioEntity = optionalUsuarioEntity.get();

        Set<GrantedAuthority> roles = new HashSet<>();
        SimpleGrantedAuthority roleUsuario = new SimpleGrantedAuthority(usuarioEntity.getRoleEntity().getRoleName());
        roles.add(roleUsuario);
        System.out.println("Usuario: " + usuarioEntity.getEmail());
        System.out.println("Rol"+usuarioEntity.getRoleEntity().getRoleName());
        return new User(usuarioEntity.getEmail(), usuarioEntity.getPassword(), roles);
    }
}