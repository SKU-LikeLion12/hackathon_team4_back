package com.example.admin.DTO;
import com.example.admin.domain.Parents;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

public class ParentsDTO {

    @Data
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ParentsCreateRequest{
        private String nickname;
        private String userId;
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
    public static class ResponseParents{
        private String userId;
        private String nickname;
        private String phoneNumber;
        private String email;

        public ResponseParents(Parents parents) {
            this.userId = parents.getUser_id();
            this.nickname = parents.getNickname();
            this.phoneNumber = parents.getPhone_number();
            this.email = parents.getEmail();
        }
    }

    @Data
    public static class ParentsUpdateRequest {
        private String nickname;
        private String password;
        private String phoneNumber;
        private String email;
    }
}
