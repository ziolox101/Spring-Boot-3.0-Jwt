package com.ziolkowski.dawid.jwtauth.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "eee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestModel {
    public TestModel(String text) {
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
}
