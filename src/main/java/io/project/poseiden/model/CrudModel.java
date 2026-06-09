package io.project.poseiden.model;

public interface CrudModel<MODEL extends CrudModel<MODEL>> {

    MODEL update(MODEL model);

    Long getId();

}
