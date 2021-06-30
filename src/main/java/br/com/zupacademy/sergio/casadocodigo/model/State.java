package br.com.zupacademy.sergio.casadocodigo.model;

import javax.persistence.*;

@Entity
public class State {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  private Country country;

  public State(String name, Country country) {
    this.name = name;
    this.country = country;
  }

  @Deprecated  // jpa
  protected State() {
  }

  public String getName() {
    return this.name;
  }

  public Long getCountryId() {
    return this.country.getId();
  }
}
