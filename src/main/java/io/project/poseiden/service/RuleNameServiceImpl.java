package io.project.poseiden.service;

import io.project.poseiden.model.RuleName;
import io.project.poseiden.repository.RuleNameRepository;
import io.project.poseiden.service.interfaces.RuleNameService;
import org.springframework.stereotype.Service;

@Service
public class RuleNameServiceImpl extends AbstractCrudService<RuleName> implements RuleNameService {
    protected RuleNameServiceImpl(RuleNameRepository repository) {
        super(repository);
    }
}
