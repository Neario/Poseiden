package io.project.poseiden.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Accessors(chain=true)
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
        setName(ruleName.getName());
        setDescription(ruleName.getDescription());
        setJson(ruleName.getJson());
        setTemplate(ruleName.getTemplate());
        setSqlStr(ruleName.getSqlStr());
        setSqlPart(ruleName.getSqlPart());
        return this;
    }
}
