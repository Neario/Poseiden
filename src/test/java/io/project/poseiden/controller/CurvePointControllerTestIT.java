package io.project.poseiden.controller;

import io.project.poseiden.model.CurvePoint;
import io.project.poseiden.repository.CurvePointRepository;
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

import java.sql.Timestamp;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Transactional
public class CurvePointControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setup() {
        curvePoint = new CurvePoint();
        curvePoint.setCurveId(1L)
                .setAsOfDate(new Timestamp(System.currentTimeMillis()))
                .setTerm(10.0)
                .setValue(10.0)
                .setCreationDate(new Timestamp(System.currentTimeMillis()));

        curvePointRepository.save(curvePoint);
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayCurvePoint() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().attribute("curvePoints", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayAddView() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectCurvePointWhenValidCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                    .with(csrf())
                    .param("curveId", "12")
                    .param("term", "14.0")
                    .param("value", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnAddViewWhenInvalidCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                    .with(csrf())
                    .param("curveId", "testAccount")
                    .param("term", "")
                    .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayUpdateViewWithCurvePoint() throws Exception {
        mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId())
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectCurvePointWhenUpdateSuccess() throws Exception {
        mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId())
                    .with(csrf())
                    .param("curveId", "12")
                    .param("term", "15.0")
                    .param("value", "11.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnUpdateViewWhenInvalidCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId())
                .with(csrf())
                .param("curveId", "updateAccount")
                .param("term", "")
                .param("value", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectToListWhenCurvePointNotFound() throws Exception {
        mockMvc.perform(get("/curvePoint/update/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("errors"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectCurvePointWhenDeleteSuccess() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/" + curvePoint.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectToListWhenDeleteCurvePointNotFound() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/99")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("errors"));
    }
}
