package io.project.poseiden.service;

import io.project.poseiden.exception.NotFoundException;
import io.project.poseiden.model.User;
import io.project.poseiden.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L)
                .setUsername("Test")
                .setPassword("Testpassword1!")
                .setFullname("Test")
                .setRole("USER");
    }

    @Test
    public void shouldReturnUser() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(user.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.get().getId());
        verify(repository).findById(user.getId());
    }

    @Test
    public void shouldReturnEmptyWhenUserIsNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(2L);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnUserWhenExists() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = userService.getById(user.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
    }

    @Test
    public void shouldReturnThrowWhenUserNotFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.getById(2L));
    }

    @Test
    public void shouldReturnAllUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnEmptyWhenAllUsersNotFound() {
        when(repository.findAll()).thenReturn(List.of());

        List<User> result = userService.findAll();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldRegisterUser() {
        User user = new User();
        user.setPassword("Testpassword1!");

        userService.save(user);

        verify(repository).save(user);
    }

    @Test
    public void shouldUpdateUser() {
        User userUpdated = new User();
        userUpdated.setId(user.getId()).setUsername("Updated");

        when(repository.findById(userUpdated.getId())).thenReturn(Optional.of(user));

        userService.update(userUpdated);

        verify(repository).save(userUpdated);
    }

    @Test
    public void shouldReturnThrowWhenUpdateUserNotFound() {
        User userUpdated = new User();
        userUpdated.setId(2L).setUsername("Updated");

        when(repository.findById(userUpdated.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.update(userUpdated));
        verify(repository, never()).save(userUpdated);
    }

    @Test
    public void shouldDeleteUser() {
        when(repository.existsById(1L)).thenReturn(true);

        userService.deleteById(1L);

        verify(repository).deleteById(1L);
    }
}
