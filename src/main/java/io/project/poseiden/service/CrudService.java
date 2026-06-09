package io.project.poseiden.service;

import io.project.poseiden.model.CrudModel;

import java.util.List;
import java.util.Optional;

public interface CrudService<MODEL extends CrudModel> {

    Optional<MODEL> findById(Long id);

    MODEL getById(Long id);

    List<MODEL> findAll();

    void save(MODEL model);

    void update(MODEL model);

    void deleteById(Long id);

}
