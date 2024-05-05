package project.kg.onotation.auth;



import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.kg.onotation.config.JwtService;
import project.kg.onotation.entity.Role;
import project.kg.onotation.entity.User;
import project.kg.onotation.repo.UserRepository;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
    User user = User.builder()
            .username(registerRequest.getUsername())
            .firstName(registerRequest.getFirstName())
            .lastName(registerRequest.getLastName())
            .email(registerRequest.getEmail())
            .registeredAt(LocalDateTime.now())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .role(Role.USER)
            .occupation(registerRequest.getOccupation())
            .yearsOfExperience(registerRequest.getYearsOfExperience())
            .build();
      userRepository.save(user);
      var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
              .token(jwtToken)
              .build();
    }


    public AuthenticationResponse authenticate(AuthencticationRequest authencticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authencticationRequest.getEmail(),
                authencticationRequest.getPassword())
        );
        var user = userRepository.findByEmail(authencticationRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
