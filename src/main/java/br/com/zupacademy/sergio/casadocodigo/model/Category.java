package br.com.zupacademy.sergio.casadocodigo.model;

import javax.persistence.*;

@Entity
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  private String name;

  @Deprecated  // jpa
  protected Category() {
  }

  public Category(String name) {
    this.name = name;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
}
