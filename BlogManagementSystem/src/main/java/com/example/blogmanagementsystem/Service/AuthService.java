package com.example.blogmanagementsystem.Service;

import com.example.blogmanagementsystem.Api.ApiException;
import com.example.blogmanagementsystem.Model.User;
import com.example.blogmanagementsystem.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public void register(User user) {
        user.setRole("USER");
        String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashPassword);

        authRepository.save(user);
    }

    public List<User> getAllUsers() {
        return authRepository.findAll();
    }

    public void updateUser(Integer authId, User updatedUser) {
        User authUser = authRepository.findUserById(authId);

        authUser.setUsername(updatedUser.getUsername());
        authUser.setPassword(new BCryptPasswordEncoder().encode(updatedUser.getPassword()));

        authRepository.save(authUser);
    }

    public void deleteUser(Integer authId, Integer userId) {
        User authUser = authRepository.findUserById(authId);
        User deletedUser = authRepository.findUserById(userId);

        if(deletedUser == null)
            throw new ApiException("User not found");

        if(authUser.getRole().equals("USER") && userId != authId)
            throw new ApiException("You are not authorized to delete this user");

        authRepository.delete(deletedUser);
    }
}
