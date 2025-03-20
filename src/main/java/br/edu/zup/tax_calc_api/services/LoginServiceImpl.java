package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.LoginRequestDTO;
import br.edu.zup.tax_calc_api.dtos.LoginResponseDTO;
import br.edu.zup.tax_calc_api.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResponseDTO(jwtTokenProvider.generateToken(authentication));
    }
}
