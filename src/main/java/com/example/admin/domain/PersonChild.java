package com.example.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String uniqueKey;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parents parent;

    public PersonChild(String name, String gender, String birthDate, double height, double weight, String uniqueKey, Parents parent) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.uniqueKey = uniqueKey;
        this.parent = parent;
    }

    public boolean checkUniqueKey(String uniqueKey) {
        return this.uniqueKey.equals(uniqueKey);
    }
}