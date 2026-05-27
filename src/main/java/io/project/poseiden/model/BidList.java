package io.project.poseiden.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
