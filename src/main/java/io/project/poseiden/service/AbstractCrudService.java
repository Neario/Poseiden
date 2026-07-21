package io.project.poseiden.service;

import io.project.poseiden.exception.NotFoundException;
import io.project.poseiden.model.CrudModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

/**
 * Generic and abstract implementation of CRUD (Create, Read, Update, Delete)
 * operations for an entity of type {@link MODEL}.
 * <br>
 * This class relies on a {@link JpaRepository} to delegate data access
 * and provides the common implementations of the methods defined by
 * {@link CrudService}. Extend this class to automatically obtain
 * CRUD for a given entity.
 *
 * @param <MODEL> the type of entity, which must implement {@link CrudModel}
 */
public class AbstractCrudService<MODEL extends CrudModel> implements CrudService<MODEL> {

    protected final JpaRepository<MODEL, Long> repository;

    protected AbstractCrudService(JpaRepository<MODEL, Long> repository) {
        this.repository = repository;
    }

    /**
     * Finds an entity by its identifier.
     * @param id identifier of the entity
     * @return an {@link Optional} entity or an empty
     */
    public Optional<MODEL> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Finds an entity by its identifier.
     *
     * @param id identifier of the entity
     * @return the matching entity
     * @throws NotFoundException if no entity matches the given identifier
     */
    public MODEL getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(getGenericName(), id));
    }

    /**
     * Returns all entities present in the database.
     *
     * @return the list of all entities of type {@link MODEL}
     */
    public List<MODEL> findAll() {
        return repository.findAll();
    }

    /**
     * Saves a new entity.
     *
     * @param model the entity to save
     */
    public void save(MODEL model) {
        repository.save(model);
    }

    /**
     * Updates an existing entity.
     * <p>
     * The existing entity is finds by its identifier.
     * Entity is update before being saved.
     *
     * @param model the entity containing the new data for update
     * @throws NotFoundException if no entity matches the identifier.
     */
    public void update(MODEL model) {
        final MODEL modelUpdated = findById(model.getId())
                .map(savedModel -> (MODEL) savedModel.update(model))
                .orElseThrow(() -> new NotFoundException(getGenericName(), model.getId()));

        repository.save(modelUpdated);
    }

    /**
     * Deletes an entity by its identifier.
     *
     * @param id the identifier of the entity
     * @throws NotFoundException if no entity matches with identifier
     */
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(getGenericName(), id);
        }
        repository.deleteById(id);
    }

    /**
     * Returns the generic name of the {@link MODEL}.
     * <p>
     * This name is used to build explicit error messages
     * (for example in {@link NotFoundException}).
     *
     * @return the fully name of the {@link MODEL}
     */
    private String getGenericName() {
        return ((Class<MODEL>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName();
    }
}
