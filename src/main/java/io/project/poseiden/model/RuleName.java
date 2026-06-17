package io.project.poseiden.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "description is mandatory")
    private String description;
    @NotBlank(message = "json is mandatory")
    private String json;
    @NotBlank(message = "template is mandatory")
    private String template;
    @NotBlank(message = "sqlStr is mandatory")
    private String sqlStr;
    @NotBlank(message = "sqlPart is mandatory")
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
