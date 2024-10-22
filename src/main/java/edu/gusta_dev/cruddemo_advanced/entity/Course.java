package edu.gusta_dev.cruddemo_advanced.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    
    //toString method:
    @Override
    public String toString() {
        return "Course [id=" + id + ", title=" + title + "]";
    }

    




    


}
