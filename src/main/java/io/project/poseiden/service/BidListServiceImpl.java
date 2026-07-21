package io.project.poseiden.service;

import io.project.poseiden.model.BidList;
import io.project.poseiden.repository.BidListRepository;
import io.project.poseiden.service.interfaces.BidListService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link BidListService}  and extends {@link AbstractCrudService} for CRUD opération for
 * {@link BidList} entities.
 * <p>
 * {@link AbstractCrudService}, using a {@link BidListRepository} for persistence.
 */
@Service
public class BidListServiceImpl extends AbstractCrudService<BidList> implements BidListService {

    public BidListServiceImpl(BidListRepository repository) {
        super(repository);
    }

}
