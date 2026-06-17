package io.project.poseiden.service;

import io.project.poseiden.model.User;
import io.project.poseiden.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractCrudService<User> {


    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

}