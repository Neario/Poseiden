package io.project.poseiden.service;

import io.project.poseiden.model.BidList;
import io.project.poseiden.repository.BidListRepository;
import io.project.poseiden.service.interfaces.BidListService;
import org.springframework.stereotype.Service;

@Service
public class BidListServiceImpl extends AbstractCrudService<BidList> implements BidListService {

    public BidListServiceImpl(BidListRepository repository) {
        super(repository);
    }

}
