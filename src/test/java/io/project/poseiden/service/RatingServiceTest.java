package io.project.poseiden.service;

import io.project.poseiden.exception.NotFoundException;
import io.project.poseiden.model.Rating;
import io.project.poseiden.repository.RatingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository repository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    private Rating rating;

    @BeforeEach
    public void setup() {
        rating = new Rating();
        rating.setId(1L)
                .setMoodysRating("MoodysRating")
                .setSandPRating("SandPRating")
                .setFitchRating("FitchRating")
                .setOrderNumber(1);

    }

    @Test
    public void shouldReturnRating() {
        when(repository.findById(rating.getId())).thenReturn(Optional.of(rating));

        Optional<Rating> result = ratingService.findById(rating.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(rating.getId(), result.get().getId());
        verify(repository).findById(rating.getId());
    }

    @Test
    public void shouldReturnEmptyWhenRatingIsNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<Rating> result = ratingService.findById(2L);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnRatingWhenExists() {
        when(repository.findById(rating.getId())).thenReturn(Optional.of(rating));

        Rating result = ratingService.getById(rating.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(rating.getId(), result.getId());
    }

    @Test
    public void shouldReturnThrowWhenRatingNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> ratingService.getById(2L));
    }

    @Test
    public void shouldReturnAllRatings() {
        when(repository.findAll()).thenReturn(List.of(rating));

        List<Rating> result = ratingService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenAllRatingsNotFound() {
        when(repository.findAll()).thenReturn(List.of());

        List<Rating> result = ratingService.findAll();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldRegisterRating() {
        Rating rating = new Rating();

        ratingService.save(rating);

        verify(repository).save(rating);
    }

    @Test
    public void shouldUpdateRating() {
        Rating rating = new Rating();
        rating.setId(1L).setMoodysRating("MoodysRating");

        Rating ratingUpdated = new Rating();
        ratingUpdated.setId(rating.getId()).setMoodysRating("MoodysRatingUpdated");

        when(repository.findById(ratingUpdated.getId())).thenReturn(Optional.of(rating));

        ratingService.update(ratingUpdated);

        verify(repository).save(ratingUpdated);
    }

    @Test
    public void shouldReturnThrowWhenUpdateRatingNotFound() {
        Rating ratingUpdated = new Rating();
        ratingUpdated.setId(2L).setMoodysRating("MoodysRatingUpdated");

        when(repository.findById(ratingUpdated.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> ratingService.update(ratingUpdated));
        verify(repository, never()).save(ratingUpdated);
    }

    @Test
    public void shouldDeleteRating() {
        when(repository.existsById(1L)).thenReturn(true);

        ratingService.deleteById(1L);

        verify(repository).deleteById(1L);
    }
}
