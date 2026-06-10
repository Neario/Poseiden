package io.project.poseiden.service;

import io.project.poseiden.model.Rating;
import io.project.poseiden.repository.RatingRepository;
import io.project.poseiden.service.interfaces.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl extends AbstractCrudService<Rating> implements RatingService {
    protected RatingServiceImpl(RatingRepository repository) {
        super(repository);
    }
}
