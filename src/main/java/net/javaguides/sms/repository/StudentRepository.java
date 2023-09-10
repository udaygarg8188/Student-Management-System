package net.javaguides.sms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.sms.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

	
	Optional<Student>findByLoginAndPassword(String login,String password);
	
	Optional<Student> findFirstByLogin(String login);
}
 