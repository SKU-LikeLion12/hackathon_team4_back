package com.example.admin.DTO;

import com.example.admin.domain.DailyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

public class DailyCheckDTO {
    @Data
    public static class DailyCheckRequest {
        private LocalDate checkedDay;
    }

    @Data
    public static class DailyCheckUpdateRequest {
        private LocalDate checkedDay;
        private boolean niceSleepDay;
        private boolean hardWorkout;
        private boolean takingMedicine;
        private boolean niceDailyMood;
    }

    @Data
    @AllArgsConstructor
    public static class ResponseDailyCheck{
        private LocalDate checkedDay;
        private Long id;
        private boolean niceSleepDay;
        private boolean hardWorkout;
        private boolean takingMedicine;
        private boolean niceDailyMood;

        public ResponseDailyCheck(DailyCheck dailyCheck){
            this.id = dailyCheck.getId();
            this.checkedDay = dailyCheck.getCheckedDay();
            this.niceSleepDay = dailyCheck.isNiceSleepDay();
            this.hardWorkout = dailyCheck.isHardWorkout();
            this.takingMedicine = dailyCheck.isTakingMedicine();
            this.niceDailyMood = dailyCheck.isNiceDailyMood();
        }
    }
}
