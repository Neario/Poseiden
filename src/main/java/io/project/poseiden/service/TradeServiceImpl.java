package io.project.poseiden.service;

import io.project.poseiden.model.Trade;
import io.project.poseiden.repository.TradeRepository;
import io.project.poseiden.service.interfaces.TradeService;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl extends AbstractCrudService<Trade> implements TradeService {
    protected TradeServiceImpl(TradeRepository repository) {
        super(repository);
    }
}
