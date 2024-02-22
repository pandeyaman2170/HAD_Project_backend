package com.example.backadmin.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Doctor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long hospitalId;

}
