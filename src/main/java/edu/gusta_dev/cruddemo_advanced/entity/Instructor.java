package edu.gusta_dev.cruddemo_advanced.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;


@Entity
@Table(name = "instructor")
public class Instructor {   

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;
    
    
    /* Definindo a relação entre as duas tabelas Instructor e InstructorDetail */
    
    /* A tabela Instructor possui uma foreign key de nome igual à "instructor_detail_id" */
    /* Para definir o relacionamento dessas duas tabelas em Java (no Banco de Dados SQL esse relacionamento já está configurado)  */
    /* Será preciso criar um atributo dentro da classe Instructor do tipo InstructorDetail (objeto da foreign key no banco de dados) */
    
    // @OneToOne faz o mapeamento do relacionado Um para um, cada objeto do tipo Instructor tem um único InstructorDetail viculado à ele e nenhum outro;
    // CascadeType.ALL basicamente configura esta classe para que: quando um objeto do tipo Instructor sofrer algum tipo de processamento
    // (um DELETE, por exemplo), o seu complemento do tipo Instructor detail também sofrerá este mesmo tipo de processamento (também será deletado);
    
    @OneToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "instructor_detail_id")
    private InstructorDetail instructorDetail;
    /*
     * A anotação @JoinColumn é usada para especificar a coluna de chave estrangeira que será utilizada para a relação 
     * entre duas tabelas no banco de dados. Neste código, @JoinColumn(name = "instructor_detail_id") faz o seguinte:
     * name = "instructor_detail_id": Especifica o nome da coluna na tabela Instructor que será usada como 
     * chave estrangeira para fazer referência à chave primária da tabela InstructorDetail.
     * Em resumo, essa anotação liga a entidade Instructor à entidade InstructorDetail usando a coluna instructor_detail_id 
     * como chave estrangeira. Isso permite o mapeamento bidirecional entre os dois objetos no banco de dados.
     */


    /* mappedBy se refere ao atribuito instructor da classe Course */
    @OneToMany(mappedBy = "instructor", 
               cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
               fetch = FetchType.LAZY) 
    private List<Course> courses;

    //No args construtor:
    public Instructor() {

    }
    
    //Constructor with args:
    public Instructor(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    //Getters and Setters:
    public int getId() {
        return id;
    }


    public void setId(int id) {
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


    public InstructorDetail getInstructorDetail() {
        return instructorDetail;
    }


    public void setInstructorDetail(InstructorDetail instructorDetail) {
        this.instructorDetail = instructorDetail;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void add(Course courseToAdd) {
        if(this.courses == null) {
            this.courses = new ArrayList<Course>();
        }
        
        this.courses.add(courseToAdd);

        courseToAdd.setInstructor(this);
    }



    @Override
    public String toString() {
        return "Instructor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", instructorDetail=" + instructorDetail + "]";
    }

    
    
    

    

    


}
