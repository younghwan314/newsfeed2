package com.example.newsfeed2.domain.user.service;

import com.example.newsfeed2.common.resolver.PasswordEncoder;
import com.example.newsfeed2.domain.auth.dto.AuthUser;
import com.example.newsfeed2.domain.user.dto.request.UpdatePasswordRequest;
import com.example.newsfeed2.domain.user.dto.request.UpdateUserRequest;
import com.example.newsfeed2.domain.user.dto.response.UserResponse;
import com.example.newsfeed2.domain.user.entity.User;
import com.example.newsfeed2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public User getUser(AuthUser authUser) {
        return userRepository.findById(authUser.getId()).orElseThrow(
                ()-> new IllegalArgumentException("유저를 찾을 수 없습니다")
        );
    }

    @Transactional(readOnly = true)
    public User userInfo(String name) {
        return userRepository.findByName(name).orElseThrow(
                ()-> new IllegalArgumentException("유저를 찾을 수 없습니다 네임을 확인해주세요")
        );
    }

    @Transactional(readOnly = true)
    public UserResponse getProfile (String name) {
        User user = userRepository.findByName(name).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다")
        );
        return new UserResponse(user.getName(), user.getEmail());
    }

    @Transactional
    public void updateProfile (AuthUser authUser, UpdateUserRequest request) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저는 존재하지 않습니다")
        );

        if (userRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다");
        };

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다");
        }
        user.updateUser(request.getName(), request.getEmail());
    }

    @Transactional
    public void updatePassword (AuthUser authUser, UpdatePasswordRequest request) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저는 존재하지 않습니다")
        );

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호와 일치하지 않습니다");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다");
        }

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }


}
