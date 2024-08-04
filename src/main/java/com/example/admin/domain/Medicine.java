package com.example.admin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;

import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Medicine {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "paremtId")
    private Parents parents;


    public Medicine(String name, Parents parents) {
        this.name = name;
        this.parents = parents;
    }

}
