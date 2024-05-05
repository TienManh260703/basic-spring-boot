package com.example.identityservice.mapper;

import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserMapper {
   public User toUser(UserCreationRequest request) {
        return User.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }

  public   void updateUser(User user, UserUpdateRequest request) {
       user.setPassword(request.getPassword());
       user.setFirstName(request.getFirstName());
       user.setLastName(request.getLastName());
       user.setDateOfBirth(request.getDateOfBirth());

    }

    public UserResponse toUserResponse(User user) {
       return UserResponse
               .builder()
               .id(user.getId())
               .userName(user.getUserName())
               .password(user.getPassword())
               .firstName(user.getFirstName())
               .lastName(user.getLastName())
               .dateOfBirth(user.getDateOfBirth())
               .build();
    }
}
