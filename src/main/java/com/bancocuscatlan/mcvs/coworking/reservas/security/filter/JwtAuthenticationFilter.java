package com.bancocuscatlan.mcvs.coworking.reservas.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bancocuscatlan.mcvs.coworking.reservas.entities.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import static com.bancocuscatlan.mcvs.coworking.reservas.security.filter.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

      //setRequiresAuthenticationRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher("/api/auth/login"));
      //Para cambiar la opcion de login;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        Users user = null;
        String correo = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), Users.class); // convertir el json que llega en
                                                                                       // un obj java
            correo = user.getCorreo();
            password = user.getContrasenia();
        } catch (JacksonException e) {
            
            e.printStackTrace();
        } catch (IOException e) {
        
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(correo,
                password);
        return authenticationManager.authenticate(authenticationToken);
    }

    // Si es valido las credenciales creamos el token y lo devolvemos
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
                .getPrincipal();
        String username = user.getUsername();

         //Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        List<String> roles = authResult.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Claims claims = Jwts.claims().add("authorities",roles)
                .add("username", username)
                .build();

        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Hola %s has iniciado sesion con exito! ", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body)); // convertimos el map generado a json
                                                                                 // con ObjMapper
        response.setContentType(CONTENT_TYPE);
        response.setStatus(200);

    }

    // Si no son validas las credenciales procedemos a enviar message error
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en la autenticacion username o passaword incorrectos! ");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }

}
