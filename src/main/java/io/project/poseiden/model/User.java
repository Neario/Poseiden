package io.project.poseiden.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Accessors(chain = true)
@DynamicUpdate
@Entity
@Table(name = "users")
public class User implements CrudModel<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User update(User user) {
        setUsername(user.username);
        setPassword(user.password);
        setFullname(user.fullname);
        setRole(user.role);
        return this;
    }
}
