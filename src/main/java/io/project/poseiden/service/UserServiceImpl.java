package io.project.poseiden.service;

import io.project.poseiden.model.User;
import io.project.poseiden.repository.UserRepository;
import io.project.poseiden.service.interfaces.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

/**
 * Implementation of {@link UserService}  and extends {@link AbstractCrudService} for CRUD opération for
 * {@link User} entities.
 * <p>
 * {@link AbstractCrudService}, using a {@link UserRepository} for persistence.
 */
@Service
public class UserServiceImpl extends AbstractCrudService<User> implements UserService {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    /**
     * Save user in BDD , if the password matches with pattern
     * @param model is user for save
     */
    @Override
    public void save(User model) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Assert.isTrue(PASSWORD_PATTERN.matcher(model.getPassword()).matches(), "le mot de " +
                "passe doit contenir 8 caractère , 1 majuscule , un symbole et un chiffre");
        model.setPassword(encoder.encode(model.getPassword()));
        repository.save(model);
    }
}