package io.project.poseiden.controller;

import io.project.poseiden.model.User;
import io.project.poseiden.repository.UserRepository;
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
public class UserControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUsername("Test")
                .setPassword("Test1234@")
                .setFullname("Test")
                .setRole("USER");

        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayUser() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayAddView() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectUserWhenValidUser() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("fullname", "fullname")
                        .param("username", "username")
                        .param("password", "Test1234@")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnAddViewWhenInvalidUser() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .with(csrf())
                        .param("fullname", "fullname")
                        .param("username", "")
                        .param("password", "")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayUpdateViewWithUser() throws Exception {
        mockMvc.perform(get("/user/update/" + user.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectUserWhenUpdateSuccess() throws Exception {
        mockMvc.perform(post("/user/update/" + user.getId())
                        .with(csrf())
                        .param("fullname", "fullname")
                        .param("username", "usernameUpdated")
                        .param("password", "Test456@")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnUpdateViewWhenInvalidUser() throws Exception {
        mockMvc.perform(post("/user/update/" + user.getId())
                        .with(csrf())
                        .param("fullname", "fullname")
                        .param("username", "")
                        .param("password", "")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectToListWhenUserNotFound() throws Exception {
        mockMvc.perform(get("/user/update/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("errors"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectUserWhenDeleteSuccess() throws Exception {
        mockMvc.perform(get("/user/delete/" + user.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }
}
