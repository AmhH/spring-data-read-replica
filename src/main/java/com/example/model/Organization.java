package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Organization {

  @Id
  private Long id;
  @Transient
  private String organizationName;
}
