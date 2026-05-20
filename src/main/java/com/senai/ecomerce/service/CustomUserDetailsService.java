package com.senai.ecomerce.service;

import com.senai.ecomerce.entity.Usuario;
import com.senai.ecomerce.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario user = usuarioRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario nao encontrado para o email: " + email);
        }
        return User.builder()
                // Para autenticacao, o "username" precisa ser o mesmo identificador usado no login.
                .username(user.getEmail())
                .password(user.getSenha())
                .roles(user.getRoles().name().replace("ROLE",""))
                .build()
                ;
    }
}
