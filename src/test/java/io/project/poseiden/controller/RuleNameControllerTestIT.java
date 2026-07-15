package io.project.poseiden.controller;

import io.project.poseiden.model.RuleName;
import io.project.poseiden.repository.RuleNameRepository;
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
public class RuleNameControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    private RuleName ruleName;

    @BeforeEach
    public void setup() {
        ruleName = new RuleName();
        ruleName.setName("test")
                .setDescription("description")
                .setJson("json")
                .setTemplate("template")
                .setSqlPart("sqlPart")
                .setSqlStr("sqlStr");

        ruleNameRepository.save(ruleName);
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayRuleName() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().attribute("ruleNames", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayAddView() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectRuleNameWhenValidRuleName() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                    .with(csrf())
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnAddViewWhenInvalidRuleName() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                    .with(csrf())
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldDisplayUpdateViewWithRuleName() throws Exception {
        mockMvc.perform(get("/ruleName/update/" + ruleName.getId())
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectRuleNameWhenUpdateSuccess() throws Exception {
        mockMvc.perform(post("/ruleName/update/" + ruleName.getId())
                    .with(csrf())
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "jsonUpdate")
                        .param("template", "templateUpdate")
                        .param("sqlStr", "sqlStrUpdate")
                        .param("sqlPart", "sqlPartUpdate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldReturnUpdateViewWhenInvalidRuleName() throws Exception {
        mockMvc.perform(post("/ruleName/update/" + ruleName.getId())
                .with(csrf())
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "jsonUpdate")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectToListWhenRuleNameNotFound() throws Exception {
        mockMvc.perform(get("/ruleName/update/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("errors"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectRuleNameWhenDeleteSuccess() throws Exception {
        mockMvc.perform(get("/ruleName/delete/" + ruleName.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser(username = "User", roles = "USER")
    public void shouldRedirectToListWhenDeleteRuleNameNotFound() throws Exception {
        mockMvc.perform(get("/ruleName/delete/99")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("errors"));
    }
}
