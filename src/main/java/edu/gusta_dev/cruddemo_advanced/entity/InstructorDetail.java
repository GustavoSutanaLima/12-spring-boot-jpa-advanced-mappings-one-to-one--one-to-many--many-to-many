package edu.gusta_dev.cruddemo_advanced.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "instructor_detail")
public class InstructorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "youtube_channel")
    private String youtubeChannel;

    @Column(name = "hobby")
    private String hobby;

    @OneToOne(mappedBy = "instructorDetail", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}) //instructorDetail se refere ao atributido instructorDetail da Classe entidade: Instructor;
    /* mappedBy = "instructorDetail": Indica que o lado proprietário da relação é o atributo 
     * instructorDetail na entidade relacionada. Isso significa que a chave estrangeira que 
     * estabelece a relação está na entidade Instructor. */
    private Instructor instructor;

    //No args constructor:
    public InstructorDetail() {
        
    }
    //Constructor with args:
    public InstructorDetail(String youtubeChannel, String hobby) {
        this.youtubeChannel = youtubeChannel;
        this.hobby = hobby;
    }
    
    //getters and setters:
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getYoutubeChannel() {
        return youtubeChannel;
    }
    public void setYoutubeChannel(String youtubeChannel) {
        this.youtubeChannel = youtubeChannel;
    }
    public String getHobby() {
        return hobby;
    }
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    
    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    //toString Method:
    @Override
    public String toString() {
        return "InstructorDetail [id=" + id + ", youtubeChannel=" + youtubeChannel + ", hobby=" + hobby + "]";
    }
    
    

    

}
