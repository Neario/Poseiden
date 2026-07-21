package io.project.poseiden.service;

import io.project.poseiden.model.RuleName;
import io.project.poseiden.repository.RuleNameRepository;
import io.project.poseiden.service.interfaces.RuleNameService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link RuleNameService}  and extends {@link AbstractCrudService} for CRUD opération for
 * {@link RuleName} entities.
 * <p>
 * {@link AbstractCrudService}, using a {@link RuleNameRepository} for persistence.
 */
@Service
public class RuleNameServiceImpl extends AbstractCrudService<RuleName> implements RuleNameService {
    protected RuleNameServiceImpl(RuleNameRepository repository) {
        super(repository);
    }
}
