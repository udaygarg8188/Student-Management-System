package net.javaguides.sms.entity;

import jakarta.persistence.*;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "students")
public class Student {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name",nullable=false)
	@NotEmpty(message = "First name is required")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email",nullable=false)
	private String email;
	
	@Lob
    @Column(name = "upload_data", columnDefinition = "mediumblob")
    private byte[] uploadData;
	
	@Column(name = "file_name")
	private String fileName;
	
	
	private Integer id1;
	private String login;
	private String password;
	private String email1;
	
		
	
	
	
	
	
	@Override
	public String toString() {
		return "Student [id1=" + id1 + ", login=" + login + ", email1=" + email1 + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(email1, id1, login, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(email1, other.email1) && Objects.equals(id1, other.id1)
				&& Objects.equals(login, other.login) && Objects.equals(password, other.password);
	}
	public Student(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public Student() {
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte[] getUploadData() {
		return uploadData;
	}
	public void setUploadData(byte[] uploadData) {
		this.uploadData = uploadData;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Integer getId1() {
		return id1;
	}
	public void setId1(Integer id1) {
		this.id1 = id1;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	
}
