package com.example.admin.DTO;

import com.example.admin.domain.DailyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public class DailyCheckDTO {
    @Data
    public static class DailyCreateRequest{
        private Date checkedDay;
        private String uniqueKey;
    }

    @Data
    public static class DailyUpdateRequest{
        private Date checkedDay;
        private String uniqueKey;
        private boolean niceSleepDay;
        private boolean hardWorkout;
        private boolean takingMedicine;
        private boolean niceDailyMood;
    }

    @Data
    public static class DailyCheckDeleteRequest{
        private Date checkedDay;
        private String uniqueKey;
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
