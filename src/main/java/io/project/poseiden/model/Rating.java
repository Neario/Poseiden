package io.project.poseiden.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Accessors(chain=true)
@DynamicUpdate
@Entity
@Table(name = "rating")
public class Rating implements CrudModel<Rating> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "moodysRating is mandatory")
    private String moodysRating;
    @NotBlank(message = "sandPRating is mandatory")
    private String sandPRating;
    @NotBlank(message = "fitchRating is mandatory")
    private String fitchRating;
    @NotNull(message = "must not be null")
    private Integer orderNumber;

    public Rating update(Rating rating) {
        setMoodysRating(rating.getMoodysRating());
        setSandPRating(rating.getSandPRating());
        setFitchRating(rating.getFitchRating());
        setOrderNumber(rating.getOrderNumber());
        return this;
    }
}
