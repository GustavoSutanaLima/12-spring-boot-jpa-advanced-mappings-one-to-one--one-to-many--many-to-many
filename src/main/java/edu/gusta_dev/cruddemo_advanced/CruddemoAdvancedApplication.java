package edu.gusta_dev.cruddemo_advanced;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.gusta_dev.cruddemo_advanced.dao.AppDAO;
import edu.gusta_dev.cruddemo_advanced.entity.Course;
import edu.gusta_dev.cruddemo_advanced.entity.Instructor;
import edu.gusta_dev.cruddemo_advanced.entity.InstructorDetail;
import edu.gusta_dev.cruddemo_advanced.entity.Review;
import edu.gusta_dev.cruddemo_advanced.entity.Student;

@SpringBootApplication
public class CruddemoAdvancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoAdvancedApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (AppDAO appDAO) {
		return runner -> {
			//saveInstructor(appDAO);
			//System.out.println(findInstructorById(appDAO));	
			//deleteInstructorById(appDAO);
			//findInstructorDetailById(appDAO);
			//deleteInstructorDetailById(appDAO);
			//createInstructorWithCourses(appDAO);
			//findInstructorWithCourses(appDAO);
			//findInstructorWithCoursesEnhanced(appDAO);
			//updateInstructor(appDAO);
			//updateCourse(appDAO);
			//deleteInstructorOne(appDAO);
			//deleteCourse(appDAO);
			//createCourseAndReview(appDAO);
			//findCourseWithReviews(appDAO);
			//delteCourseAndReviews(appDAO);
			//createCourseAndStudents(appDAO);
			//findCourseWithStudents(appDAO);
			//findStudentWithCourses(appDAO);
			//addsMoreCoursesToStudent(appDAO);
			deleteStudent(appDAO);

		};
	}

	private void deleteStudent(AppDAO appDAO) {
		
		appDAO.deleteStudentById(2);

	}

	private void addsMoreCoursesToStudent(AppDAO appDAO) {
		int studentId = 2;

		Student foundStudent = appDAO.findStudentWithCourses(studentId);

		Course russianCourse = new Course("Russian to Go: From Basic to Advenced.");
		Course pythonCourse = new Course("Python: learn how to program without using semicolons!");

		foundStudent.addCourse(pythonCourse);
		foundStudent.addCourse(russianCourse);

		appDAO.updateStudent(foundStudent);
	}

	private void findStudentWithCourses(AppDAO appDAO) {
		int studentId = 1;

		Student foundStudent = appDAO.findStudentWithCourses(studentId);

		System.out.println("Student found:" + foundStudent);
		System.out.println("Courses that this Studend is enrolled in:" + foundStudent.getCourses());
	}

	private void findCourseWithStudents(AppDAO appDAO) {
		
		int courseId = 10;

		Course foundCourse = appDAO.findCourseWithStudents(courseId);

		System.out.println("Course found:" + foundCourse);
		System.out.println("Students in this course:" + foundCourse.getStudents());

		
	}

	private void createCourseAndStudents(AppDAO appDAO) {
		Course math = new Course("This is a Math course");
		//Course port = new Course("This is a Portuguese course");

		Student gustavo = new Student("Gustavo", "Lima", "gustavo@gmail.com");
		Student jonn = new Student("Jonn", "Lima", "jonn@gmail.com");
		Student leo = new Student("Leonardo", "Bigogno", "leonardo@gmail.com");

		math.addStudent(leo);
		math.addStudent(jonn);
		math.addStudent(gustavo);

		appDAO.saveCourse(math);

	}

	private void delteCourseAndReviews(AppDAO appDAO) {
		int courseId = 12;

		appDAO.deleteCourseById(courseId);
	}

	private void findCourseWithReviews(AppDAO appDAO) {
		int courseId = 12;

		Course courseFound = appDAO.findCourseAndReviewsByCourseId(12);

		System.out.println(courseFound);
		System.out.println(courseFound.getReviews());
	}

	private void createCourseAndReview(AppDAO appDAO) {
		Course javaCourse = new Course("Spring Boot");

		javaCourse.addReview(new Review("The course is great, but I anxious to be done with it as soon as i can!"));
		javaCourse.addReview(new Review("The teacher is great and high-spirited"));
		javaCourse.addReview(new Review("4 out of 5, awesome course"));

		System.out.println("Saving the course...");
		System.out.println(javaCourse);
		System.out.println(javaCourse.getReviews());

		appDAO.saveCourse(javaCourse);

	}

	private void deleteCourse(AppDAO appDAO) {

		int idToDelete = 10;

		appDAO.deleteCourseById(idToDelete);
	}

	private void deleteInstructorOne(AppDAO appDAO) {
		
		int idToFind = 3;

		System.out.println("Finding the Instrcutor of id to delete: "+ idToFind);

		Instructor foundInstructor = appDAO.findInstructorById(idToFind);

		appDAO.deleteInstructor(foundInstructor);
	}

	private void updateCourse(AppDAO appDAO) {
		//id of the course to update;
		int id = 11;

		System.out.println("Fiding course id of: " + id);

		Course foundCourse = appDAO.findCourseById(id);

		System.out.println("Found Course: " + foundCourse);

		foundCourse.setTitle("Things are not what they seem anymore");

		appDAO.updateCourse(foundCourse);

		Course newCourseName = appDAO.findCourseById(id);

		System.out.println("New title: " + newCourseName.getTitle());
	}

	private void updateInstructor(AppDAO appDAO) {
		int idToFind = 1;

		System.out.println("Finding the Instrcutor of id: "+ idToFind);

		Instructor foundInstructor = appDAO.findInstructorById(idToFind);

		System.out.println("Instructor found: " + foundInstructor);

		foundInstructor.setFirstName("Heather");
		foundInstructor.setLastName("Swamson");
		foundInstructor.setEmail("heather@luv2code.com");

		appDAO.updateInstructor(foundInstructor);

		Instructor updatedInstructor = appDAO.findInstructorById(idToFind);

		System.out.println("Instructor of id " + idToFind + " is now: " + updatedInstructor);

		System.out.println("Done!");
	}

	private void findInstructorWithCoursesEnhanced(AppDAO appDAO) {
		int instructorId = 1;

		System.out.println("Finding instructor of ID: " + instructorId );

		System.out.println("...");

		Instructor instructorFound = appDAO.findInstructorByIdJoinFetch(instructorId);

		System.out.println("Found Instructor: " + instructorFound);
		System.out.println("Courses: " + instructorFound.getCourses());
	}

	private void findInstructorWithCourses(AppDAO appDAO) {
		int instructorId = 1;

		System.out.println("Finding instructor of ID: " + instructorId );

		Instructor foundInstructor = appDAO.findInstructorById(instructorId);

		System.out.println("Temp Instructor = " + foundInstructor);

		List<Course> courses = appDAO.findCoursesByInstructorId(instructorId);

		System.out.println(courses);

		foundInstructor.setCourses(courses);

		System.out.println("Associated courses: " + foundInstructor.getCourses());

		System.out.println("DONE!");
	}

	private void createInstructorWithCourses(AppDAO appDAO) {
		
		Instructor tempInstructor = 
			new Instructor("Gustavo", "Sutana", "gustavosutanalima@gmail.com");

		InstructorDetail tempInstructorDetail = 
			new InstructorDetail("https://www.youtube.com/users/gustavosutanalima", "Cycling");
	
		Course mathCourse = new Course("Math Course");
		Course portCourse = new Course("Portuguese Course");
		Course engCourse = new Course("English Course");
		Course progCourse = new Course("Programming Course");
		
		tempInstructor.add(mathCourse);
		tempInstructor.add(portCourse);
		tempInstructor.add(engCourse);
		tempInstructor.add(progCourse);
		
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		System.out.println("Instructor to be saved: " + tempInstructor);
		
		appDAO.saveInstructor(tempInstructor);

	}

	private void deleteInstructorDetailById(AppDAO appDAO) {
		
		int instructorDetailId = 3;

		InstructorDetail deletedInstructorDetail = appDAO.instructorDetailById(instructorDetailId);

		appDAO.deleteInstrcutorDetailById(instructorDetailId);

		System.out.println(deletedInstructorDetail + " was deleted successfully!");
	}

	private void findInstructorDetailById(AppDAO appDAO) {
		// gets the instructorDetail of id = 1
		InstructorDetail instructorDetailFound = appDAO.instructorDetailById(1);

		System.out.println(instructorDetailFound);

		System.out.println(instructorDetailFound.getInstructor());

	}

	private void deleteInstructorById(@Qualifier("appDAOImplementacao") AppDAO appDAO) {
		appDAO.deleteInstructorById(2);
	}

	private Instructor findInstructorById(@Qualifier("appDAOImplementacao") AppDAO appDAO) {
		
		int id = 2;

		return appDAO.findInstructorById(id);
	}

	private void saveInstructor(AppDAO appDAO) {
		Instructor tempInstructor = new Instructor("Gustavo", "Sutana", "gustavo@luv2code.com");
		
		InstructorDetail tempInstructorDetail = new InstructorDetail("https://youtube.com/user/gustavos", "Motorcycling");
		
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		System.out.println("Saving..." + tempInstructor);

		appDAO.saveInstructor(tempInstructor);
	}

}
