package com.example.admin.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {

    private String type;         // 운동 유형 (스트레칭, 근력운동, 유산소)
    private String name;         // 운동 이름
    private String description;  // 운동 설명
    private String repetitions;  // 추천 횟수
    private String imageUrl;     // 사진 URL
    private String videoUrl;     // 참고 영상 링크

    public ExerciseDTO(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }
}