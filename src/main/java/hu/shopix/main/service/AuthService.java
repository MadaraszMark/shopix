package hu.shopix.main.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import hu.shopix.main.dto.AuthResponse;
import hu.shopix.main.dto.LoginRequest;
import hu.shopix.main.dto.RegisterRequest;
import hu.shopix.main.mapper.UserMapper;
import hu.shopix.main.model.User;
import hu.shopix.main.repository.UserRepository;
import hu.shopix.main.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Konstruktor
public class AuthService {
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    
    public AuthResponse register(RegisterRequest request) {
    	if(userRepository.existsByEmail(request.getEmail())) {
    		throw new ResponseStatusException(HttpStatus.CONFLICT, "Ez az e-mail cím már használatban van");
    	}
    	String enCodedPassword = passwordEncoder.encode(request.getPassword());
    	User user = userMapper.toEntity(request, enCodedPassword);
    	user.setCreatedAt(LocalDateTime.now());
    	
    	User saved = userRepository.save(user);
    	
    	String token = jwtTokenUtil.generateToken(saved.getEmail());
    	return userMapper.toResponse(user, token);
    }
    
    public AuthResponse login(LoginRequest request) {
    	User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Hibás e-mail cím vagy jelszó."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Hibás e-mail cím vagy jelszó.");
        }

        String token = jwtTokenUtil.generateToken(user.getEmail());
        return userMapper.toResponse(user, token);
    }

}
