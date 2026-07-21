package io.project.poseiden.service;

import io.project.poseiden.model.CrudModel;

import java.util.List;
import java.util.Optional;

/**
 * The standard CRUD operations (Create, Read, Update, Delete) for an entity of type {@link MODEL}.
 *
 * @param <MODEL> the type of the entity, which must implement {@link CrudModel}
 */
public interface CrudService<MODEL extends CrudModel> {

    Optional<MODEL> findById(Long id);

    MODEL getById(Long id);

    List<MODEL> findAll();

    void save(MODEL model);

    void update(MODEL model);

    void deleteById(Long id);

}
