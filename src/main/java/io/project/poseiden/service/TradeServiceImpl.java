package io.project.poseiden.service;

import io.project.poseiden.model.Trade;
import io.project.poseiden.repository.TradeRepository;
import io.project.poseiden.service.interfaces.TradeService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link TradeService}  and extends {@link AbstractCrudService} for CRUD opération for
 * {@link Trade} entities.
 * <p>
 * {@link AbstractCrudService}, using a {@link TradeRepository} for persistence.
 */
@Service
public class TradeServiceImpl extends AbstractCrudService<Trade> implements TradeService {
    protected TradeServiceImpl(TradeRepository repository) {
        super(repository);
    }
}
