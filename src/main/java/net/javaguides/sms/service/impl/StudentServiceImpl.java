package net.javaguides.sms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        super();
        this.studentRepository = studentRepository;
    }

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Student getStudentById(Long id) {
		return studentRepository.findById(id).get();
	}

	@Override
	public Student updateStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public void deleteStudentById(Long id) {
		studentRepository.deleteById(id);	
	}

	 @Override
	    public Student uploadStudent(Student student) {
	        return studentRepository.save(student);
	    }
	
	 @Override
	    public List<Student> saveBulkStudents(List<Student> students) {
	        return studentRepository.saveAll(students);
	 }
	 
	 @Override
	 public Student registerUser(String login,String password,String email1) {
		 if(login !=null && password!=null) {
			 if(studentRepository.findFirstByLogin(login).isPresent()) {
				 System.out.println("Duplicate login");
				 return null;
			 }
			 Student student =new Student ();
			 student.setLogin(login);
			 student.setPassword(password);
			 student.setEmail1(email1);
			  student.setFirstName("");
			  student.setEmail("");
			 return studentRepository.save(student);
		 }
		 else {
			 return null;
		 }
	 }
	 public Student authenticate(String login,String password) {
		 Student student = studentRepository.findByLoginAndPassword(login, password).orElse(null);
		    return student;
	 }
}
