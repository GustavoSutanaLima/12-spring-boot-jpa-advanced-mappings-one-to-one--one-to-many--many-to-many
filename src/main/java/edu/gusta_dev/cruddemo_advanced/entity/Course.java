package edu.gusta_dev.cruddemo_advanced.entity;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class Course {

    /*Defining fields: */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;
    

    /* Definindo o relacionamento entre a tabela course e a tabela instructor:
    /*  - Muitos para um: muitos cursos para um instrutor; 
     *  - ManyToOne entendimento genérico: muitos objetos desta classe (classe Course) 
     *  - para um único atributo abaixo (atributo do tipo Instructor):
     */
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
    /*
     * A anotação @JoinColumn é usada para especificar a coluna que deve ser usada para unir 
     * duas tabelas no banco de dados. Neste caso, @JoinColumn(name = "instructor_id") faz o seguinte:
     * name = "instructor_id": Especifica que a coluna instructor_id na tabela course será usada 
     * como chave estrangeira para referenciar a tabela instructor. Ou seja, a columna instructor_id da tabela
     * course irá referenciar o id de uma linha na tabela instructor pois essa anotação está sobre o
     * atributo instrutor:
     */

    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    /*
     * Na Coluna "course_id" da classe Review (dado pela lista de reviews abaixo) será feita uma JoinColumn    
     * baseada no Id da classe Course, de forma que um Course possa ter muitos Reviews (@OneToMany).     
     * Esse Id de um Course específico será uma chave estrangeira na tabela Review e apontará para o determinado Course (e
     * ficará salvo na coluna course_id da tabela review).    
     * Se um Course for deletado, os Reviews subsequentes serão deletados (não somente isso, qualquer outro tipo de operação    
     * cascade também será aplicada: cascade = CascadeType.ALL).
     */
    @JoinColumn(name = "course_id") 
    private List<Review> reviews;


    //Setting the relationship between course and students (course => students)

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "course_student", // Nome da tabela de junção
               joinColumns = @JoinColumn(name = "course_id"), // Define a coluna de junção que referencia a tabela 'course'
               inverseJoinColumns = @JoinColumn(name = "student_id") // Define a coluna de junção que referencia a tabela 'student'
                )
    private List<Student> students;

    //Construtor sem argumentos:
    public Course() {
        
    }
    
    //Construtor passando o título:
    public Course(String title) {
        this.title = title;
    }
    
    //Getters e setters:
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Instructor getInstructor() {
        return instructor;
    }
    
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    public void addReview(Review review) {
        
        if (reviews == null) {
            reviews = new ArrayList<Review>();
        }
        
        if(review != null) {
            this.reviews.add(review);
        }
    }
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<Student>();
        }

        if(student != null) {
            this.students.add(student);
        }
    }
    
    //toString method:
    @Override
    public String toString() {
        return "Course [id=" + id + ", title=" + title + "]";
    }


    




    


}
