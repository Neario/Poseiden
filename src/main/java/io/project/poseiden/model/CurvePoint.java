package io.project.poseiden.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Data
@DynamicUpdate
@Entity
@Table(name = "curvepoint")
public class CurvePoint implements CrudModel<CurvePoint> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "must not be null")
    private Long curveId;
    private Timestamp asOfDate;
    private Double term;
    private Double value;
    private Timestamp creationDate;

    public CurvePoint update(CurvePoint curvePoint) {
        setId(curvePoint.getId());
        setAsOfDate(curvePoint.getAsOfDate());
        setTerm(curvePoint.getTerm());
        setValue(curvePoint.getValue());
        setCreationDate(curvePoint.getCreationDate());
        return this;
    }
}
