package io.project.poseiden.service;

import io.project.poseiden.model.CurvePoint;
import io.project.poseiden.repository.CurvePointRepository;
import io.project.poseiden.service.interfaces.CurvePointService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link CurvePointService}  and extends {@link AbstractCrudService} for CRUD opération for
 * {@link CurvePoint} entities.
 * <p>
 * {@link AbstractCrudService}, using a {@link CurvePointRepository} for persistence.
 */
@Service
public class CurvePointServiceImpl extends AbstractCrudService<CurvePoint> implements CurvePointService {
    public CurvePointServiceImpl(CurvePointRepository repository) {
        super(repository);
    }
}
