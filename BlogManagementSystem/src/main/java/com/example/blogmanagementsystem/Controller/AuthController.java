package com.example.blogmanagementsystem.Controller;

import com.example.blogmanagementsystem.Api.ApiResponse;
import com.example.blogmanagementsystem.Model.User;
import com.example.blogmanagementsystem.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(){
        return ResponseEntity.status(200).body(new ApiResponse("Welcome Back"));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(200).body(new ApiResponse("Thank you"));
    }

    // All
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid User user){
        authService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("User registered successfully"));
    }

    // Admin
    @GetMapping("/get-all")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(200).body(authService.getAllUsers());
    }

    // User
    @PutMapping("/update")
    public ResponseEntity updateUser(@AuthenticationPrincipal User auth, @RequestBody @Valid User updatedUser){
        authService.updateUser(auth.getId(), updatedUser);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    // Admin and User
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteUser(@AuthenticationPrincipal User auth, @PathVariable Integer userId){
        authService.deleteUser(auth.getId(), userId);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }
}
