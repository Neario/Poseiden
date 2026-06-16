package io.project.poseiden.service;

import io.project.poseiden.exception.NotFoundException;
import io.project.poseiden.model.RuleName;
import io.project.poseiden.repository.RuleNameRepository;
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
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository repository;

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    public void setup() {
        ruleName = new RuleName();
        ruleName.setId(1L)
                .setName("test")
                .setDescription("description")
                .setJson("json")
                .setTemplate("template")
                .setSqlPart("sqlPart")
                .setSqlStr("sqlStr");
    }

    @Test
    public void shouldReturnRuleName() {
        when(repository.findById(ruleName.getId())).thenReturn(Optional.of(ruleName));

        Optional<RuleName> result = ruleNameService.findById(ruleName.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(ruleName.getId(), result.get().getId());
        verify(repository).findById(ruleName.getId());
    }

    @Test
    public void shouldReturnEmptyWhenRuleNameIsNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<RuleName> result = ruleNameService.findById(2L);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnRuleNameWhenExists() {
        when(repository.findById(ruleName.getId())).thenReturn(Optional.of(ruleName));

        RuleName result = ruleNameService.getById(ruleName.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(ruleName.getId(), result.getId());
    }

    @Test
    public void shouldReturnThrowWhenRuleNameNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> ruleNameService.getById(2L));
    }

    @Test
    public void shouldReturnAllRuleNames() {
        when(repository.findAll()).thenReturn(List.of(ruleName));

        List<RuleName> result = ruleNameService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenAllRuleNamesNotFound() {
        when(repository.findAll()).thenReturn(List.of());

        List<RuleName> result = ruleNameService.findAll();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldRegisterRuleName() {
        RuleName ruleName = new RuleName();

        ruleNameService.save(ruleName);

        verify(repository).save(ruleName);
    }

    @Test
    public void shouldUpdateRuleName() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1L).setName("test");

        RuleName ruleNameUpdated = new RuleName();
        ruleNameUpdated.setId(ruleName.getId()).setName("testUpdated");

        when(repository.findById(ruleNameUpdated.getId())).thenReturn(Optional.of(ruleName));

        ruleNameService.update(ruleNameUpdated);

        verify(repository).save(ruleNameUpdated);
    }

    @Test
    public void shouldReturnThrowWhenUpdateRuleNameNotFound() {
        RuleName ruleNameUpdated = new RuleName();
        ruleNameUpdated.setId(2L).setName("testUpdated");

        when(repository.findById(ruleNameUpdated.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> ruleNameService.update(ruleNameUpdated));
        verify(repository, never()).save(ruleNameUpdated);
    }

    @Test
    public void shouldDeleteRuleName() {
        ruleNameService.deleteById(1L);

        verify(repository).deleteById(1L);
    }
}
