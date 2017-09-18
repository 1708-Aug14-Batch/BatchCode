package com.ex.dao;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ex.beans.Course;
import com.ex.beans.Instructor;
import com.ex.beans.Students;
import com.ex.beans.Transcript;
import com.ex.util.ConnectionUtil;

public class HibDAO {
	
	
	public Students addStudent(Students student){
		Session session = ConnectionUtil.getSession();
		Students s = new Students();
		Transcript script = new Transcript();
		
		try{
			Transaction tx = (Transaction) session.beginTransaction();
			
			int scriptId = (Integer) session.save(script);
			script.setId(scriptId);
			
			student.setTranscript(script);
			
			int studentId = (Integer) session.save(student);
			student.setId(studentId);
			
			tx.commit();
		}
		finally{
			session.close();
		}
		return student;
	}
	
	public void addInstructor(Instructor instructor){
		Session session = ConnectionUtil.getSession();
		try{
			Transaction tx = (Transaction) session.beginTransaction();
			session.save(instructor);
			tx.commit();
		}
		finally{
			session.close();
		}
	}
	
	public <T> void addSimple(final Class<T> obj){
		Session session = ConnectionUtil.getSession();
		
		try{
			Transaction tx = (Transaction) session.beginTransaction();
			session.save(obj);
			tx.commit();
		}
		finally{
			session.close();
		}
	}
	public  void addSimple(final Course c){
		Session session = ConnectionUtil.getSession();
		
		try{
			Transaction tx = (Transaction) session.beginTransaction();
			session.save(c);
			tx.commit();
		}
		finally{
			session.close();
		}
	}
	//Read
	public Instructor getInstructorById(int id){
		Session session = ConnectionUtil.getSession();
		Instructor i = null;
		try{
			i = (Instructor) session.get(Instructor.class, id);
		}
		catch(HibernateException e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return i;
	}
	
	public List<Students> getAllStudents(){
		Session session = ConnectionUtil.getSession();
		Criteria criteria = session.createCriteria(Students.class)
				.add(Restrictions.ilike("firstname", "gen"));
		List<Students> students = criteria.list();
		session.close();
		return students;
	}
	public List<Students> criteriaDemo(){
		Session session = ConnectionUtil.getSession();
		Criteria criteria = session.createCriteria(Students.class).add(Restrictions.ilike("firstname", "kid%"));
		List<Students> students = criteria.list();
		session.close();
		return students;
		
	}
	public List<Students> queryDemo(String like){
		Session session = ConnectionUtil.getSession();
		String hql = "from Students where lower (firstname) like :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", like);
		List<Students> students = query.list();
		return students;
				
	}
	
	public void deleteStudentsById(int id){
		Session session = ConnectionUtil.getSession();
		Students s = getStudentById(id);
		Transaction tx = (Transaction) session.beginTransaction();
		session.delete(s.getTranscript());
		session.delete(s);
		session.close();
		
	}

	public Students getStudentById(int id){
		Session session = ConnectionUtil.getSession();
		Students s = null;
		try{
			s = (Students) session.get(Students.class, id);
		}
		catch(HibernateException e){
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return s;
	}
	//Update

}