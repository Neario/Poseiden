package io.project.poseiden.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
