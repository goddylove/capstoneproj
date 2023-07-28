package com.godinho.empl_mgt_App.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
@Entity
@Table(name = "role")

@NoArgsConstructor

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    public Role(String name) {
        this.name = name;
    }
}
