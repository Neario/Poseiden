package io.project.poseiden.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@DynamicUpdate
@Entity
@Table(name = "rulename")
public class RuleName implements CrudModel<RuleName> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;

    public RuleName update(RuleName ruleName){
        return this;
    }
}
