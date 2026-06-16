package io.project.poseiden.exception;

public class NotFoundException extends AbstractDomainException {

    public NotFoundException(String simpleName, long id) {
        super("Resource '" + simpleName + "' not found with id = '" + id + "'");
    }
}
