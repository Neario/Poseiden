package io.project.poseiden.service;

import io.project.poseiden.model.BidList;
import io.project.poseiden.model.User;
import io.project.poseiden.repository.BidListRepository;
import io.project.poseiden.repository.UserRepository;
import io.project.poseiden.service.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractCrudService<User> {


    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

}