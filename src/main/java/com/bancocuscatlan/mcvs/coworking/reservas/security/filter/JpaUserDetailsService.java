package com.bancocuscatlan.mcvs.coworking.reservas.security.filter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancocuscatlan.mcvs.coworking.reservas.entities.Users;
import com.bancocuscatlan.mcvs.coworking.reservas.repositories.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    public JpaUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> userOptional = repository.findByCorreo(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema", username));
        }

        Users user = userOptional.orElseThrow();

        // Convertimos los roles que existen en nuestro user a una lista de
        // GrantedAuthority
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getCorreo(),
                user.getContrasenia(),
                authorities);
    }

}
