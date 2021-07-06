package hr.petkovic.diplomskibe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.diplomskibe.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	public Optional<User> findByUsername(String username);
}
