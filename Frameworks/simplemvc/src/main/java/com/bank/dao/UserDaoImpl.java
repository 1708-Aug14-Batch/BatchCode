package com.bank.dao;

import java.util.ArrayList;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bank.domain.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao{
	
	public UserDaoImpl() {}
	
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public User findUserByUsername(String username) {
		System.out.println("in find by username" + username);
		if(username == null) return null;
		ArrayList<User> list = (ArrayList<User>) sessionFactory
				.getCurrentSession()
				.createQuery("from User where lower(username)=?")
				.setParameter(0, username.toLowerCase()).list();
		if(list.size()==0)return null;
		else {System.out.println(list.get(0)); 
			return list.get(0);}
	}

	@Override
	public User createUser(User user) {
		System.out.println(user.toString());
		sessionFactory.getCurrentSession().save(user);
		return user;
	}

}
