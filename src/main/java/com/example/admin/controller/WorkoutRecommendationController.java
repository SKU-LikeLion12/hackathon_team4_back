package com.example.admin.controller;

import com.example.admin.DTO.WorkoutRecommendationDTO;
import com.example.admin.DTO.WorkoutRecommendationDTO.RequestWorkoutDTO;
import com.example.admin.DTO.WorkoutRecommendationDTO.ResponseWorkoutDTO;
import com.example.admin.service.WorkoutRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WorkoutRecommendationController {

    private final WorkoutRecommendationService workoutRecommendationService;

    @PostMapping("/workout/recommend")
    public ResponseWorkoutDTO getWorkoutRecommendations(@RequestBody RequestWorkoutDTO request) {
        String token = request.getToken();
        WorkoutRecommendationDTO workoutRecommendationDTO = workoutRecommendationService.recommendExercises(token);
        return new ResponseWorkoutDTO(workoutRecommendationDTO);
    }
}
