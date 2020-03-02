package com.ascending.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users {
    public Users(){}
    public Users(String name, String firstName, String lastName, String email){
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    public Long getId(){return this.id;}

    public void setId(Long id) {this.id = id;}

    public String getName(){return this.name;}

    public void setName(String name){this.name = name;}

    public String getFirstName(){return this.firstName;}

    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getLastName(){return this.lastName;}

    public void setLastName(String lastName){this.lastName = lastName;}

    public String getEmail(){return this.email;}

    public void setEmail(String email){this.email = email;}
}