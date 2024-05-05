package com.example.identityservice.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)// cái nào null thì ko kèm vào json
//
public class ApiResponse <T> {
    private int code=1000;// thành công là 1000
    private String message;
    private T result;
}
