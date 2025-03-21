package com.example.newsfeed2.domain.user.controller;

import com.example.newsfeed2.domain.auth.annotation.Auth;
import com.example.newsfeed2.domain.auth.dto.AuthUser;
import com.example.newsfeed2.domain.user.dto.request.UpdatePasswordRequest;
import com.example.newsfeed2.domain.user.dto.request.UpdateUserRequest;
import com.example.newsfeed2.domain.user.dto.response.UserResponse;
import com.example.newsfeed2.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{name}/profile")
    public UserResponse userProfile(@PathVariable String name) {
        return userService.getProfile(name);
    }

    @PatchMapping("/users/profile")
    public void updateUserProfile(@Auth AuthUser authUser, @RequestBody UpdateUserRequest request) {
        userService.updateProfile(authUser, request);
    }

    @PatchMapping("/users/profile/password")
    public void changePassword(@Auth AuthUser authUser, @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(authUser,request);
    }
}
