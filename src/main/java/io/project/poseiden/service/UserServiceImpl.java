package io.project.poseiden.service;

import io.project.poseiden.model.User;
import io.project.poseiden.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends AbstractCrudService<User> {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public void save(User model) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Assert.isTrue(PASSWORD_PATTERN.matcher(model.getPassword()).matches(), "le mot de " +
                "passe doit contenir 8 caractère , 1 majuscule , un symbole et un chiffre");
        model.setPassword(encoder.encode(model.getPassword()));
        super.save(model);
    }
}