package io.project.poseiden.service;

import io.project.poseiden.model.CurvePoint;
import io.project.poseiden.repository.CurvePointRepository;
import io.project.poseiden.service.interfaces.CurvePointService;
import org.springframework.stereotype.Service;

@Service
public class CurvePointServiceImpl extends AbstractCrudService<CurvePoint> implements CurvePointService {
    public CurvePointServiceImpl(CurvePointRepository repository) {
        super(repository);
    }
}
