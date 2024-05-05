package com.example.identityservice.service;

import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.entity.User;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found")
        );
    }

    public User createUser(UserCreationRequest request)  {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = User.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(String userId, UserUpdateRequest request) {
        User existingUser = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));
        existingUser.setPassword(request.getPassword());
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setDateOfBirth(request.getDateOfBirth());
        return userRepository.save(existingUser);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
