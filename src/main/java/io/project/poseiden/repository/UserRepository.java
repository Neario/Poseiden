package io.project.poseiden.repository;

import io.project.poseiden.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
