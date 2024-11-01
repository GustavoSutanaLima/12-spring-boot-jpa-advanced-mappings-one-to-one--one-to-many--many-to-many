package edu.gusta_dev.cruddemo_advanced.dao;

import java.util.List;

import edu.gusta_dev.cruddemo_advanced.entity.Course;
import edu.gusta_dev.cruddemo_advanced.entity.Student;
import edu.gusta_dev.cruddemo_advanced.entity.Instructor;
import edu.gusta_dev.cruddemo_advanced.entity.InstructorDetail;

public interface AppDAO {

    void saveInstructor(Instructor instructorToBeSaved);

    Instructor findInstructorById(int id);

    void deleteInstructorById(int id);

    InstructorDetail instructorDetailById(int id);

    void deleteInstrcutorDetailById(int id);

    List<Course> findCoursesByInstructorId(int id);

    Instructor findInstructorByIdJoinFetch(int id);

    void updateInstructor(Instructor instructorToUpdate);

    void updateCourse(Course courseToUpdate);

    Course findCourseById(int id);

    void deleteInstructor(Instructor instructorToDelete);

    void deleteCourseById(int id);

    void saveCourse(Course courseToBeSaved);

    Course findCourseAndReviewsByCourseId(int courseId);

    Course findCourseWithStudents(int courseId);

    Student findStudentWithCourses(int studentId);

    void updateStudent(Student studentToUpdate);

    void deleteStudentById(int sutdentId);
    
}
