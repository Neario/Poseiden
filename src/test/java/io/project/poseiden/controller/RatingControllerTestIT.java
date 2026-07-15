package io.project.poseiden.controller;

import io.project.poseiden.model.Rating;
import io.project.poseiden.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Transactional
public class RatingControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    private Rating rating;

    @BeforeEach
    public void setup() {
        rating = new Rating();
        rating.setMoodysRating("MoodysRating")
                .setSandPRating("SandPRating")
                .setFitchRating("FitchRating")
                .setOrderNumber(1);

        ratingRepository.save(rating);
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayRating() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayAddView() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectRatingWhenValidRating() throws Exception {
        mockMvc.perform(post("/rating/validate")
                    .with(csrf())
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRating")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnAddViewWhenInvalidRating() throws Exception {
        mockMvc.perform(post("/rating/validate")
                    .with(csrf())
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("orderNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayUpdateViewWithRating() throws Exception {
        mockMvc.perform(get("/rating/update/" + rating.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectRatingWhenUpdateSuccess() throws Exception {
        mockMvc.perform(post("/rating/update/" + rating.getId())
                    .with(csrf())
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRatingUpdate")
                        .param("fitchRating", "fitchRatingUpdate")
                        .param("orderNumber", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnUpdateViewWhenInvalidRating() throws Exception {
        mockMvc.perform(post("/rating/update/" + rating.getId())
                .with(csrf())
                        .param("moodysRating", "moodysRating")
                        .param("sandPRating", "sandPRatingUpdate")
                        .param("fitchRating", "")
                        .param("orderNumber", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectToListWhenRatingNotFound() throws Exception {
        mockMvc.perform(get("/rating/update/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("errors"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectRatingWhenDeleteSuccess() throws Exception {
        mockMvc.perform(get("/rating/delete/" + rating.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectToListWhenDeleteRatingNotFound() throws Exception {
        mockMvc.perform(get("/rating/delete/99")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("errors"));
    }
}
