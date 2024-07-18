package com.example.admin.DTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

public class ParentsDTO {

    @Data
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ParentsCreateRequest{
        @Schema(description = "닉네임", example = "test_nickname")
        private String nickname;
        @Schema(description = "아이디", example = "test_id")
        private String userId;
        @Schema(description = "비밀번호", example = "test_password")
        private String password;
        private String phoneNumber;
        private String email;
    }
    @Data
    public static class ParentsLoginRequest {
        private String userId;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ParentsResponse{
        @Schema(description = "회원 아이디", example = "test_id")
        private String userId;
        @Schema(description = "닉네임", example = "hello")
        private String nickname;
        private String phoneNumber;
        private String email;
    }

    @Data
    public static class ParentsUpdateRequest {
        private String token;
        private String nickname;
        private String phoneNumber;
        private String email;
    }

    @Data
    public static class ParentsDeleteRequest {
        private String token;
    }
}
