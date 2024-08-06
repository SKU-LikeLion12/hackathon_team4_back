package com.example.admin.DTO;

import com.example.admin.domain.MedicineCheck;
import lombok.AllArgsConstructor;
import lombok.Data;

public class MedicineCheckDTO {

    @Data
    public static class RequestMedicineCheck {
        private Long scheduleMedicineId;
        private boolean morningTaken;
        private boolean lunchTaken;
        private boolean dinnerTaken;
        private String timeOfDay; // timeOfDay 필드 추가
    }

    @Data
    public static class ResponseMedicineCheck {
        private Long scheduleMedicineId;
        private boolean morningTaken;
        private boolean lunchTaken;
        private boolean dinnerTaken;

        public ResponseMedicineCheck(MedicineCheck medicineCheck) {
            this.scheduleMedicineId = medicineCheck.getScheduleMedicine().getId();
            this.morningTaken = medicineCheck.isMorningTaken();
            this.lunchTaken = medicineCheck.isLunchTaken();
            this.dinnerTaken = medicineCheck.isDinnerTaken();
        }
    }
    @Data
    @AllArgsConstructor
    public static class IntakeRate {
        private double Rate;
    }

    @Data
    public static class MedicineCheckResponseDTO {
        private Long id; // ScheduleMedicine ID
        private String name; // Medicine name
        private Boolean morning; // Morning taken status
        private Boolean lunch; // Lunch taken status
        private Boolean dinner; // Dinner taken status
    }
}
