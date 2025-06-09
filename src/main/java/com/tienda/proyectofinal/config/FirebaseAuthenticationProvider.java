package com.tienda.proyectofinal.config;

import com.tienda.proyectofinal.service.FirebaseAuthRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private FirebaseAuthRestService authRestService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (authRestService.authenticate(email, password)) {
            return new UsernamePasswordAuthenticationToken(
                    email, password,
                    Collections.singletonList(new SimpleGrantedAuthority("USER"))
            );
        }

        throw new BadCredentialsException("Correo o contraseña inválidos");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}