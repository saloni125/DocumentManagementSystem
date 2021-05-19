package com.comviva.DocumentManagementSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.comviva.DocumentManagementSystem.bean.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUserNameAndPassword(String userName, String password);

	public User findByUserName(String userName);

}
