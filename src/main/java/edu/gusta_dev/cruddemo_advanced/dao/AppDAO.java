package edu.gusta_dev.cruddemo_advanced.dao;

import edu.gusta_dev.cruddemo_advanced.entity.Instructor;
import edu.gusta_dev.cruddemo_advanced.entity.InstructorDetail;

public interface AppDAO {

    void saveInstructor(Instructor instructorToBeSaved);

    Instructor findInstructorById(int id);

    void deleteInstructorById(int id);

    InstructorDetail instructorDetailById(int id);

    void deleteInstrcutorDetailById(int id);
}
