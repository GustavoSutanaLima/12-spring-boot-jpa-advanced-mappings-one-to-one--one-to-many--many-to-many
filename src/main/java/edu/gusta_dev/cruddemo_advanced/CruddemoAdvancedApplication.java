package edu.gusta_dev.cruddemo_advanced;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.gusta_dev.cruddemo_advanced.dao.AppDAO;
import edu.gusta_dev.cruddemo_advanced.entity.Instructor;
import edu.gusta_dev.cruddemo_advanced.entity.InstructorDetail;

@SpringBootApplication
public class CruddemoAdvancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoAdvancedApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner (AppDAO appDAO) {
		return runner -> {
			saveInstructor(appDAO);
			//System.out.println(findInstructorById(appDAO));	
			//deleteInstructorById(appDAO);
			//findInstructorDetailById(appDAO);
			//deleteInstructorDetailById(appDAO);
		};
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
