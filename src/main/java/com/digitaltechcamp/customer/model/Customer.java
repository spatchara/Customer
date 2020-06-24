package com.digitaltechcamp.customer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min =1,max = 100,message = "Please type firstName")
    private String firstname;

    @NotNull
    @Size(min =1,max = 100,message = "Please type lastName")
    private  String lastname;

    @NotNull
    @Email(message = "Please type email")
    private String email;

    @NotNull
    private String phoneNo;

    private Integer age;



}