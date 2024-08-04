package com.example.admin.DTO;

import com.example.admin.domain.PersonChild;
import lombok.Data;

public class PersonChildDTO {
    @Data
    public static class ResponsePersonChild {
        private Long id;
        private String name;
        private String gender;
        private String birthDate;
        private double height;
        private double weight;
        private String uniqueKey;

        public ResponsePersonChild(PersonChild personChild) {
            this.id = personChild.getId();
            this.name = personChild.getName();
            this.gender = personChild.getGender();
            this.birthDate = personChild.getBirthDate();
            this.height = personChild.getHeight();
            this.weight = personChild.getWeight();
            this.uniqueKey = personChild.getUniqueKey();
        }
    }

    @Data
    public static class RequestPersonChild {
        private String name;
        private String gender;
        private String birthDate;
        private double height;
        private double weight;
        private String token;
    }

    @Data
    public static class RequestChildId {
        private Long childId;
    }

}
