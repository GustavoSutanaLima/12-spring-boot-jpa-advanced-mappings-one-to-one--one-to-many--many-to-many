package edu.gusta_dev.cruddemo_advanced.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.gusta_dev.cruddemo_advanced.entity.Instructor;
import edu.gusta_dev.cruddemo_advanced.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

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
        
        Query queryToFindInstrcutorById = entityManager //dentro do argumento de createQuery é preciso usar o nome da classe como ela é no java e não no BD
                                            .createQuery("SELECT inst FROM Instructor inst WHERE inst.id= :idToFindInstructorBy")
                                            // na hora de referenciar o parâmetro a ser passado abaixo, é preciso adicionar ":" ao nome 
                                            // do parâmetro na query;
                                            .setParameter("idToFindInstructorBy", id);

        
        try {
            return (Instructor) queryToFindInstrcutorById.getSingleResult();

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



}
