package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.models.UserEntity;
import br.edu.zup.tax_calc_api.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("O nome de usuário " + username + " não foi encontrado.")
        );

        GrantedAuthority authorities = new SimpleGrantedAuthority(userEntity.getRole().getRoleName());

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.singletonList(authorities)
        );
    }
}
