package net.javaguides.sms.service;

import java.util.List;

import net.javaguides.sms.entity.Student;

public interface StudentService {
	List<Student> getAllStudents();
	
	Student saveStudent(Student student);
	
	Student getStudentById(Long id);
	
	Student updateStudent(Student student);
	
	void deleteStudentById(Long id);

	Student uploadStudent(Student student);
	
	List<Student> saveBulkStudents(List<Student> students);
	   
	Student registerUser(String login,String password,String email1);

	Student authenticate(String login,String password);
}