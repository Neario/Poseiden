package io.project.poseiden.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
