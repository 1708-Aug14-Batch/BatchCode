package com.ex.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ex.bean.Student;
import com.ex.util.ConnectionUtil;

public class HibernateDAO {

	//create
	public void addStudent(Student student){
		Session session = ConnectionUtil.getSession();
		
		try{
		Transaction tx = (Transaction) session.beginTransaction();
		session.save(student);
		tx.commit();
			
		}finally{
			session.close();
		}
	}
}
