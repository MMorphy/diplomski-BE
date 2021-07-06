package hr.petkovic.diplomskibe.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import hr.petkovic.diplomskibe.entity.User;
import hr.petkovic.diplomskibe.repository.UserRepo;

@Service
public class UserService {

	private UserRepo userRepo;

	public UserService(UserRepo r) {
		userRepo = r;
	}

	public User findUserByUsername(String username) throws NoSuchElementException {
		return userRepo.findByUsername(username).get();
	}

	public User findUserById(Long id) throws NoSuchElementException{
		return userRepo.findById(id).get();
	}
}
