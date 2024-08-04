package com.example.admin.DTO;

import com.example.admin.domain.ScheduleMedicine;
import lombok.Data;

public class ScheduleMedicineDTO {

    @Data
    public static class RequestSchedule {
        private String title;
        private boolean morning;
        private boolean lunch;
        private boolean dinner;

        public boolean getMorning() {
            return this.morning;
        }
        public boolean getLunch() {
            return this.lunch;
        }
        public boolean getDinner() {
            return this.dinner;
        }
    }

    @Data
    public static class ResponseSchedule {
        private String title;
        private boolean morning;
        private boolean lunch;
        private boolean dinner;

        public ResponseSchedule(ScheduleMedicine scheduleMedicine) {
            this.title = scheduleMedicine.getMedicineName();
            this.morning = scheduleMedicine.getMorning();
            this.lunch = scheduleMedicine.getLunch();
            this.dinner = scheduleMedicine.getDinner();
        }
    }

    @Data
    public static class RequestGetSchedule {
        private Long childId;
    }

}
