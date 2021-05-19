package com.comviva.DocumentManagementSystem.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comviva.DocumentManagementSystem.bean.User;
import com.comviva.DocumentManagementSystem.dao.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	private final UserRepository userRepository;

	public UserService(UserRepository ur) {
		this.userRepository = ur;
	}

	public void saveUserToDB(User user) {
		userRepository.save(user);
	}

	public User findByUserNameAndPassword(String username, String password) {
		return userRepository.findByUserNameAndPassword(username, password);
	}

	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}
	
	public User finduser(Integer id)
	{
		return userRepository.getOne(id);
	}
	
}
