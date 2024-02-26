package com.example.backadmin.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Table(name = "Hospital")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
}