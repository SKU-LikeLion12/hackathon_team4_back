package com.example.admin.DTO;


import com.example.admin.domain.Medicine;
import lombok.Data;

public class MedicineDTO {

    @Data
    public static class RequestMedicine{
        private String name;
    }

    @Data
    public static class RequestGetMedicine{
        private String token;
    }
    @Data
    public static class RequestDelMedicine{
        private Long id;
    }

    @Data
    public static class ResponseMedicine{
        private Long id;
        private String name;


        public ResponseMedicine(Medicine medicine) {
            this.id = medicine.getId();
            this.name = medicine.getName();
        }
    }


}
