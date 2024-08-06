package com.example.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class PersonChild {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String gender;
    private String birthDate;
    private double height;
    private double weight;
    @Setter
    private double bmi;
    private String uniqueKey;
    @Setter
    private Long age;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parents parent;

    public PersonChild(String name, String gender, String birthDate, double height, double weight, double bmi, Long age, String uniqueKey, Parents parent) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.uniqueKey = uniqueKey;
        this.parent = parent;
        this.bmi = bmi;
        this.age = age;
    }

    public boolean checkUniqueKey(String uniqueKey) {
        return this.uniqueKey.equals(uniqueKey);
    }
}