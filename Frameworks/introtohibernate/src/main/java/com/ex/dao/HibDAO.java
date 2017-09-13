package com.ex.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ex.beans.Course;
import com.ex.beans.Instructor;
import com.ex.beans.Student;
import com.ex.beans.Transcript;
import com.ex.util.ConnectionUtil;

public class HibDAO {


	//CREATE 
	public Student addStudent(Student student){
		Session session = ConnectionUtil.getSession();
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



	public Course addCourse(Course c){
		Session session = ConnectionUtil.getSession();
		Course ret = null;
		try{
			Transaction tx = (Transaction) session.beginTransaction();
			int id = (Integer)session.save(c);
			c.setId(id);
			tx.commit();
			ret = c;
		}
		finally{
			session.close();
		}
		return ret;
	}



	//READ
	public Instructor getInstructorByID(int id){
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

	/*Criteria is a simplified API for retrieving entities 
	by composing Criterion objects. This is a very 
	convenient approach for functionality like "search" screens 
	where there is a variable number of conditions to be placed 
	upon the result set.								
	 */

	public List<Student> getAllStudents(){
		Session session = ConnectionUtil.getSession();
		Criteria criteria = session.createCriteria(Student.class);
		List<Student> students = criteria.list();
		session.close();
		return students;
	}

	//Criteria Demo for more specifics
	/*
	 * What the SQL Query looks like:
	 * Hibernate: select this_.STUDENT_ID as 
	 * STUDENT1_0_0_, this_.email as email0_0_, 
	 * this_.firstname as firstname0_0_, this_.lastname
	 *  as lastname0_0_, this_.TRANSCRIPT_ID as 
	 *  TRANSCRIPT5_0_0_ from STUDENTS this_ where 
	 *  this_.firstname like ?

	 */
	public List<Student> criteriaDemo(){
		Session session = ConnectionUtil.getSession();
		Criteria criteria = 
				session.createCriteria(Student.class).
				add(Restrictions.ilike("firstname","test%"));
		List<Student> students = criteria.list();

		session.close();
		return students;
	}

	public List<Student> queryDemo(String like){
		//select* from students where lower(firstname) like '%t%';
		Session session = ConnectionUtil.getSession();
		String hql = "from Student where lower(firstname) like :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", like);
		List<Student> students = query.list();

		return students;
	}



	//UPDATE
	public void addCourseToTranscript(Transcript t, Course c){
		Set<Course> courses = t.getCourses();
		if(courses != null){
			for(Course temp :courses){
				courses.add(temp);
			} 
		}
		courses.add(c);

		t.setCourses(courses);
		Session session = ConnectionUtil.getSession();
		try{
			Transaction tx = session.beginTransaction();
			session.update(t);
			tx.commit();
		}
		finally{
			session.close();
		}
	}


}
