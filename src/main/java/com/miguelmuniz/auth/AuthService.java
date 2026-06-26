package com.miguelmuniz.auth;





import com.miguelmuniz.domain.User;
import com.miguelmuniz.repository.UserRepository;
import com.miguelmuniz.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

    @Service
    public class AuthService {

        private final UserRepository repo;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;

        public AuthService(
                UserRepository repo,
                JwtService jwtService,
                PasswordEncoder passwordEncoder) {

            this.repo = repo;
            this.jwtService = jwtService;
            this.passwordEncoder = passwordEncoder;
        }

        public AuthResponse login(LoginRequest request) {

            User user = repo.findByEmail(request.email())
                    .orElseThrow(() ->
                            new BadCredentialsException("Invalid email or password"));

            if (!passwordEncoder.matches(
                    request.password(),
                    user.getPassword())) {

                throw new BadCredentialsException("Invalid email or password");
            }

            String token = jwtService.generateToken(user);

            return new AuthResponse(token);
        }

        public AuthResponse register(RegisterRequest request) {

            if (repo.findByEmail(request.email()).isPresent()) {
                throw new RuntimeException("Email already registered");
            }

            User user = new User(
                    null,
                    request.name(),
                    request.email(),
                    passwordEncoder.encode(request.password())
            );

            repo.save(user);

            String token = jwtService.generateToken(user);

            return new AuthResponse(token);
        }
    }

