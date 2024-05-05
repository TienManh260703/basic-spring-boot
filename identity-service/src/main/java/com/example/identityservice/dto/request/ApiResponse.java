package com.example.identityservice.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)// cái nào null thì ko kèm vào json
// Trả API và code nếu lỗi thì code và message
public class ApiResponse <T> {
     int code=1000;// thành công là 1000
     String message;
     T result;
}
