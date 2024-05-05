package com.example.identityservice.service;

import com.example.identityservice.configuration.MapperConfiguration;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
//    private final ModelMapper modelMapper;

    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user ->
                        UserResponse
                                .builder()
                                .id(user.getId())
                                .userName(user.getUserName())
                                .password(user.getPassword())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .dateOfBirth(user.getDateOfBirth())
                                .build())
                .toList();

    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("User not found"))
        );
    }

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User existingUser = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));
        userMapper.updateUser(existingUser, request);
        return userMapper.toUserResponse(userRepository.save(existingUser));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
