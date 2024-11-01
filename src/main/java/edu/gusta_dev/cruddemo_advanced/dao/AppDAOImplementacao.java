package edu.gusta_dev.cruddemo_advanced.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.gusta_dev.cruddemo_advanced.entity.Course;
import edu.gusta_dev.cruddemo_advanced.entity.Instructor;
import edu.gusta_dev.cruddemo_advanced.entity.InstructorDetail;
import edu.gusta_dev.cruddemo_advanced.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@Repository
public class AppDAOImplementacao implements AppDAO {

    private EntityManager entityManager;

    //Constructor Injection:
    @Autowired
    public AppDAOImplementacao (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional //Necessário quando se faz operações to tipo Save/Delete/Update no bando de dados;
    public void saveInstructor(Instructor instructor) {
        entityManager.persist(instructor);
        //Persiste() do EntityManager salva o objeto no BD. Como a classe deste objeto possui um atributo
        //do tipo InstructorDetail anotado com @OneToOne (cascade = CascadeType.ALL), ao salvar o objeto
        //Instructor no banco, o objeto InstructorDetail (dentro dele) também será salvo em sua respectiva tabela;
    }

    @Override
    public Instructor findInstructorById(int id) {
        
        TypedQuery<Instructor> queryToFindInstrcutorById = entityManager //dentro do argumento de createQuery é preciso usar o nome da classe como ela é no java e não no BD
                                            .createQuery("SELECT inst FROM Instructor inst WHERE inst.id= :idToFindInstructorBy", Instructor.class)
                                            // na hora de referenciar o parâmetro a ser passado abaixo, é preciso adicionar ":" ao nome 
                                            // do parâmetro na query;
                                            .setParameter("idToFindInstructorBy", id);

        
        try {
            return queryToFindInstrcutorById.getSingleResult();

        } 
        catch (NoResultException e) {
            throw new RuntimeException("No instructor found with ID: " + id);
        }
        
    }

    @Override
    @Transactional //Necessários em operação de SAVE, DELETE (no banco de dados);
    public void deleteInstructorById(int id) {

        //Outra forma de recuperar um dado no banco passando a Classe e o parâmtro chave primária:
        Instructor instructorToDelete = entityManager.find(Instructor.class, id);

        entityManager.remove(instructorToDelete);
    }

    @Override
    public InstructorDetail instructorDetailById(int id) {

        return entityManager.find(InstructorDetail.class, id);
    }

    @Override
    @Transactional //Operação DELETE precisa do Transactional;
    public void deleteInstrcutorDetailById(int id) {
        
        InstructorDetail instructorDetailDetailToDelete = entityManager.find(InstructorDetail.class, id);
        
        /*Setando o Instructor do detailInstrutor para null com o objeto de remover o vínculo entre eles. */
        instructorDetailDetailToDelete.getInstructor().setInstructorDetail(null);

        //Eu não posso fazer o códgio comentado abaixo, porque se não, estaria transformando o objeto Instructor para null, e eu não quero isso.
        //A linha acima, dá um retrieve no Instructor depois seta o InstructorDetail dele para null, ou seja, "remove" a informação ateriormente associada;
        //instructorDetailDetailToDelete.setInstructor(null);

        entityManager.remove(instructorDetailDetailToDelete);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {
        
        TypedQuery<Course> queryToFindCourses = 
            entityManager.createQuery("SELECT c FROM Course c WHERE c.instructor.id= :data", Course.class)
                         .setParameter("data", id);
            //SELECT c FROM Course c WHERE c.instructor.id= :data
            /* Usando a JPQL: A Classe Course não tem um atributo que faz referencia direta ao id do instructor
             * Ela faz referência a um objeto instructor. Porém, dentro da Query, é possível dar um get
             * já que o objeto instrutor tem um método getId:
             * baser usar "Course.instructor.id" dentro da query que se traduz para java:
             * Course.getInstructor().getId();
             */

        return queryToFindCourses.getResultList();
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int id) {
        
        TypedQuery<Instructor> query = entityManager.createQuery(
          "SELECT inst FROM Instructor inst JOIN FETCH inst.courses where inst.id = :data",  
        Instructor.class).setParameter("data", id);

        //JOIN FETCH exclui a necessidade do método acima, ou seja, não é preciso dar um retrieve nos cursos,
        //setá-los ao Instructor e depois dar um get para printar o resultado;

        /* JOIN FETCH "modifica" o fetch da anotação @OneToMany */

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void updateInstructor(Instructor instructorToUpdate) {
        entityManager.merge(instructorToUpdate);
    }

    @Override
    @Transactional
    public void updateCourse(Course courseToUpdate) {
        entityManager.merge(courseToUpdate);
    }

    @Override
    public Course findCourseById(int id) {
        
        TypedQuery<Course> queryToFindCourseById = entityManager.createQuery(
            "SELECT co FROM Course co WHERE co.id = :data", Course.class)
            .setParameter("data", id);

        return queryToFindCourseById.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteInstructor(Instructor instructorToDelete) {
        
        int idOfInstructor = instructorToDelete.getId();

        Instructor foundInstrcutor =  entityManager.find(Instructor.class, idOfInstructor);

        List<Course> courses = foundInstrcutor.getCourses();

        for (Course course : courses) {
            course.setInstructor(null);
        }

        entityManager.remove(foundInstrcutor);


    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {
        Course courseToDelete = findCourseById(id);
        entityManager.remove(courseToDelete);
    }


    @Override
    @Transactional
    public void saveCourse(Course courseToBeSaved) {
        entityManager.persist(courseToBeSaved);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int courseId) {
        
        TypedQuery<Course> query = entityManager.createQuery(
            "SELECT cour FROM Course cour JOIN FETCH cour.reviews WHERE cour.id = :data", Course.class)
            .setParameter("data", courseId); //foi necessário fazer um JOIN FETCH aqui pois o fetch type na anotação sobre a lista de reviews foi setado para LAZY;

        return query.getSingleResult();
    }

    @Override
    public Course findCourseWithStudents(int courseId) {
        
        TypedQuery<Course> query = entityManager.createQuery(
            "SELECT cour FROM Course cour JOIN FETCH cour.students WHERE cour.id =:data", Course.class)
            .setParameter("data", courseId);
        
        return query.getSingleResult();
    }

    @Override
    public Student findStudentWithCourses(int studentId) {
        
        TypedQuery<Student> query = entityManager.createQuery(
            "SELECT stud FROM Student stud JOIN FETCH stud.courses WHERE stud.id =:data", Student.class)
            .setParameter("data", studentId);
        
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void updateStudent(Student studentToUpdate) {
        entityManager.merge(studentToUpdate);
    }

    @Override
    @Transactional
    public void deleteStudentById(int studentId) {
        Student foundStudent = findStudentWithCourses(studentId);
        entityManager.remove(foundStudent);
    }
    



}
