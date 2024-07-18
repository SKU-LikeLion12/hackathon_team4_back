package com.example.admin.DTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

public class MemberDTO {

    @Data
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MemberCreateRequest{
        @Schema(description = "닉네임", example = "test_nickname")
        private String nickname;
        @Schema(description = "아이디", example = "test_id")
        private String userId;
        @Schema(description = "비밀번호", example = "test_password")
        private String password;
    }
    @Data
    public static class MemberLoginRequest {
        private String userId;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MemberResponse{
        @Schema(description = "회원 아이디", example = "test_id")
        private String userId;
        @Schema(description = "닉네임", example = "hello")
        private String nickname;
    }

    @Data
    public static class MemberUpdateRequest {
        private String token;
        private String nickname;
    }

    @Data
    public static class MemberDeleteRequest {
        private String token;
    }
}
