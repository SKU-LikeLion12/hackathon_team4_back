package com.example.admin.DTO;

import com.example.admin.domain.WorkoutCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

public class WorkoutCheckDTO {
    @Data
    public static class WorkoutCheckRequest {
        private LocalDate checkedDay;
        private String workoutType;
        private String workoutName;
    }

    @Data
    public static class WorkoutCheckUpdateRequest {
        private LocalDate checkedDay;
        private Long workoutId;
        private String workoutType;
        private String workoutName;
    }

    @Data
    @AllArgsConstructor
    public static class ResponseWorkoutCheck {
        private LocalDate checkedDay;
        private Long id;
        private String workoutType;
        private String workoutName;

        public ResponseWorkoutCheck(WorkoutCheck workoutCheck){
            this.id = workoutCheck.getId();
            this.checkedDay = workoutCheck.getCheckedDay();
            this.workoutType = workoutCheck.getWorkoutType();
            this.workoutName = workoutCheck.getWorkoutName();
        }
    }
}
