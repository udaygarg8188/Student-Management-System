package net.javaguides.sms.controller;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@Controller
public class StudentController {
	
	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	
	@GetMapping("/students/register")
	public String getRegisterPage(Model model) {
	    model.addAttribute("registerRequest", new Student()); // Replace with appropriate object
	    return "register_page";
	}
	 
	 @GetMapping("/students/login")
	 public String getLoginPage(Model model) {
		 model.addAttribute("loginRequest", new Student());
		 return "login_page";
	 }
	 
	 @PostMapping("/students/register")
	 public String register(@ModelAttribute @Valid Student student, BindingResult bindingResult) {
	     if (bindingResult.hasErrors()) {
	         return "register_page"; // Return to the registration page if there are errors
	     }
	     System.out.println("register request: " + student);
	     
	     Student registeruser = studentService.registerUser(
	             ((Student) student).getLogin(),
	             ((Student) student).getPassword(),
	             ((Student) student).getEmail1()
	     );
	     return registeruser == null ? "error_page" : "redirect:/students/login";
	 }

	
	 @PostMapping("/students/login")
	 public String login(@ModelAttribute Student student,Model model,HttpSession session) {
		 System.out.println("login request: " + student);
		 Student authenticated=studentService.authenticate(((Student) student).getLogin(),((Student) student).getPassword());
	   if(authenticated !=null) {
		   model.addAttribute("userlogin", ((Student) authenticated).getLogin());
		   model.addAttribute("useremail", ((Student) authenticated).getEmail1());
		   
		   session.setAttribute("userlogin", ((Student) authenticated).getLogin());
	        session.setAttribute("useremail", ((Student) authenticated).getEmail1());
		   return "students";
	   }
	   else {
		   return "error_page";
	   }
	 }
	 
	@GetMapping("/students")
	public String listStudents(Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		return "students";
	}
	
	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		
		// create student object to hold student form data
		Student student = new Student();
		model.addAttribute("student", student);
		return "create_student";
		
	}
	
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute Student student) {
		studentService.saveStudent(student);
		return "redirect:/students";
	}
	
	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";
	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id,
            @ModelAttribute Student student,
			Model model) {
		
		
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());
				
		
		studentService.updateStudent(existingStudent);
		return "redirect:/students";		
	}
	
	
	
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}
	
	 @GetMapping("/students/upload/{id}")
	    public String uploadStudentForm(@PathVariable Long id, Model model) {
	        Student student = studentService.getStudentById(id);
	        model.addAttribute("student", student);
	        return "upload_student";
	    }
	 	
	 @PostMapping("/students/upload/{id}")
	 public String uploadStudent(@PathVariable Long id,
	         @RequestParam("file") MultipartFile file,
	         Model model) {
	     if (!file.isEmpty()) {
	         try {
	             Student student = studentService.getStudentById(id);
	             student.setUploadData(file.getBytes()); 
	             student.setFileName(file.getOriginalFilename()); 
	             studentService.updateStudent(student); 
	         } catch (IOException e) {
	             e.printStackTrace(); 
	         }
	     }

	     return "redirect:/students"; 
	 }

	    @GetMapping("/students/download/{id}")
	    public ResponseEntity<Resource> downloadStudentFile(@PathVariable Long id) {
	       
	    	System.out.println("Student "+ id );
	    	
	        Student student = studentService.getStudentById(id);

	        
	        ByteArrayResource resource = new ByteArrayResource(student.getUploadData());

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + student.getFileName() + "\"")
	                .body(resource);
	    }
	    
	    @PostMapping("/students/upload/bulk")
	    public String uploadBulkStudents(@RequestParam("file") MultipartFile file) {
	        if (!file.isEmpty()) {
	            try {
	                Workbook workbook = new XSSFWorkbook(file.getInputStream());
	                Sheet sheet = workbook.getSheetAt(0);

	                List<Student> students = new ArrayList<>();

	                for (Row row : sheet) {
	                    if (row.getRowNum() == 0) {
	                        // Skip the header row
	                        continue;
	                    }

	                    String firstName = row.getCell(0).getStringCellValue();
	                    String lastName = row.getCell(1).getStringCellValue();
	                    String email = row.getCell(2).getStringCellValue();

	                    students.add(new Student(firstName, lastName, email));
	                }

	                studentService.saveBulkStudents(students);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        return "redirect:/students";
	    }

	   
	    

}
