package com.example.admin.DTO;

import com.example.admin.domain.DailyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public class DailyCheckDTO {
    @Data
    public static class DailyCheckRequest {
        private Date checkedDay;
        private String uniqueKey;
    }

    @Data
    public static class DailyCheckUpdateRequest {
        private Date checkedDay;
        private String uniqueKey;
        private boolean niceSleepDay;
        private boolean hardWorkout;
        private boolean takingMedicine;
        private boolean niceDailyMood;
    }

    @Data
    @AllArgsConstructor
    public static class ResponseDailyCheck{
        private Date checkedDay;
        private String uniqueKey;
        private boolean niceSleepDay;
        private boolean hardWorkout;
        private boolean takingMedicine;
        private boolean niceDailyMood;

        public ResponseDailyCheck(DailyCheck dailyCheck){
            this.checkedDay = dailyCheck.getCheckedDay();
            this.uniqueKey = dailyCheck.getChild().getUniqueKey();
            this.niceSleepDay = dailyCheck.isNiceSleepDay();
            this.hardWorkout = dailyCheck.isHardWorkout();
            this.takingMedicine = dailyCheck.isTakingMedicine();
            this.niceDailyMood = dailyCheck.isNiceDailyMood();
        }
    }
}
