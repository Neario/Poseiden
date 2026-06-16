package io.project.poseiden.model;

import jakarta.persistence.*;
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
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;

    public Rating update(Rating rating) {
        setMoodysRating(rating.getMoodysRating());
        setSandPRating(rating.getSandPRating());
        setFitchRating(rating.getFitchRating());
        setOrderNumber(rating.getOrderNumber());
        return this;
    }
}
