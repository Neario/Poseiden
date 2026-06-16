package io.project.poseiden.service;


import io.project.poseiden.exception.NotFoundException;
import io.project.poseiden.model.CrudModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public class AbstractCrudService<MODEL extends CrudModel> implements CrudService<MODEL> {

    protected final JpaRepository<MODEL, Long> repository;

    protected AbstractCrudService(JpaRepository<MODEL, Long> repository) {
        this.repository = repository;
    }

    public Optional<MODEL> findById(Long id) {
        return repository.findById(id);
    }

    public MODEL getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(getGenericName(), id));
    }

    public List<MODEL> findAll() {
        return repository.findAll();
    }

    public void save(MODEL model) {
        repository.save(model);
    }

    public void update(MODEL model) {
        final MODEL modelUpdated = findById(model.getId())
                .map(savedModel -> (MODEL) savedModel.update(model))
                .orElseThrow(() -> new NotFoundException(getGenericName(), model.getId()));

        repository.save(modelUpdated);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private String getGenericName() {
        return ((Class<MODEL>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName();
    }
}
