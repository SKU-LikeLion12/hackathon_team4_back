package com.example.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Data
public class WorkoutRecommendationDTO {
    private Map<String, List<ExerciseDTO>> recommendations;

    @Data
    public static class RequestWorkoutDTO{
        private String token;
    }
    @Data
    public static class ResponseWorkoutDTO {
        private Map<String, List<ExerciseDTO>> recommendations;

        public ResponseWorkoutDTO(WorkoutRecommendationDTO workoutRecommendationDTO) {
            this.recommendations = workoutRecommendationDTO.getRecommendations();
        }
    }
}
