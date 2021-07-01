package br.com.zupacademy.sergio.casadocodigo.model;

import javax.persistence.*;

@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String givenName;
  private String familyName;
  private String nationalRegistryId;
  private String streetAddressLine1;
  private String streetAddressLine2;
  private String city;

  @ManyToOne
  private Country country;

  @ManyToOne
  private State state;

  private String phoneNumber;
  private String postalCode;

  public Customer(
    String email,
    String givenName,
    String familyName,
    String nationalRegistryId,
    String streetAddressLine1,
    String streetAddressLine2,
    String city,
    Country country,
    State state,
    String phoneNumber,
    String postalCode
  ) {
    this.email = email;
    this.givenName = givenName;
    this.familyName = familyName;
    this.nationalRegistryId = nationalRegistryId;
    this.streetAddressLine1 = streetAddressLine1;
    this.streetAddressLine2 = streetAddressLine2;
    this.city = city;
    this.country = country;
    this.state = state;
    this.phoneNumber = phoneNumber;
    this.postalCode = postalCode;
  }

  @Deprecated  // jpa
  protected Customer() {
  }

  public Long getId() {
    return this.id;
  }

}
