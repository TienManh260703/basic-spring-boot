package com.example.identityservice.controller;

import com.example.identityservice.dto.request.ApiResponse;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping
    public List<UserResponse> getUser (){
        return  userService.getUsers();
    }
    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId){
        return userService.getUser(userId);
    }

    @PostMapping
  public   ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser (@PathVariable("userId") String userId , @RequestBody UserUpdateRequest request ){
        return userService.updateUser( userId ,request);
    }
    @DeleteMapping("/{userId}")
    public String  deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }
}
