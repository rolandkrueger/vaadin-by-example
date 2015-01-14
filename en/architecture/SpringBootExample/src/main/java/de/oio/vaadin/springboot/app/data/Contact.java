package de.oio.vaadin.springboot.app.data;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * JavaBean that holds the data for a contact.
 */
@Entity
public class Contact {
  private String firstName = "";

  // Validation is done with Bean Validation (Hibernate Validator)
  @NotBlank
  private String lastName = "";
  private String address = "";

  private String telephone = "";
  private Date dateOfBirth;
  private String info = "";

  @Email
  private String email = "";

  @Id
  @GeneratedValue
  private int id;

  public Contact() {
  }

  public Contact(String firstName, String lastName, String address,
      String telephone, Date dateOfBirth, String info, String email) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.telephone = telephone;
    this.dateOfBirth = dateOfBirth;
    this.info = info;
    this.email = email;
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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public int hashCode() {
    return id;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Contact other = (Contact) obj;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Contact [firstName=" + firstName + ", lastName=" + lastName
        + ", address=" + address + ", telephone=" + telephone + ", birthdate="
        + dateOfBirth + ", info=" + info + ", email=" + email + "]";
  }
}
