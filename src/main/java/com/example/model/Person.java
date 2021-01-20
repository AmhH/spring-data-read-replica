package com.example.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Transient
  private String firstName;

  private String lastname;
  private String email;
  private Integer age;
  private Integer salary;

  @OneToMany
  private List<Department> department;
  @OneToOne
  private Organization organization;

}
