package com.example.newsfeed2.domain.auth.service;

import com.example.newsfeed2.common.jwt.JwtUtil;
import com.example.newsfeed2.common.resolver.PasswordEncoder;
import com.example.newsfeed2.domain.auth.dto.request.LoginRequest;
import com.example.newsfeed2.domain.auth.dto.request.SignupRequest;
import com.example.newsfeed2.domain.auth.dto.response.LoginResponse;
import com.example.newsfeed2.domain.user.entity.User;
import com.example.newsfeed2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        String password = passwordEncoder.encode(signupRequest.getPassword());

        User user = new User(
                signupRequest.getName(),
                signupRequest.getEmail(),
                password
        );
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        String bearJwt = jwtUtil.createToken(user.getId(), loginRequest.getEmail());
        return new LoginResponse(bearJwt);
    }
}
