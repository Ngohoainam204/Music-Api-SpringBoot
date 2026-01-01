package com.ngohoainam.music_api.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.ngohoainam.music_api.exception.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults (level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>{
    String code;
    String message;
    T result;
    public static <T> ApiResponse <T> success(T result){
        return ApiResponse.<T>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(result).build();
    }

    public static <T> ApiResponse<T> created(T result) {
        return ApiResponse.<T>builder()
                .code(ErrorCode.CREATED.getCode())
                .message(ErrorCode.CREATED.getMessage())
                .result(result)
                .build();
    }
}
