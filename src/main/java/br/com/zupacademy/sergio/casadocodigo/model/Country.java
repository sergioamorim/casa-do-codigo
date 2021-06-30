package br.com.zupacademy.sergio.casadocodigo.model;

import javax.persistence.*;

@Entity
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;

  public Country(String name) {
    this.name = name;
  }

  @Deprecated  // jpa
  protected Country() {
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
}
