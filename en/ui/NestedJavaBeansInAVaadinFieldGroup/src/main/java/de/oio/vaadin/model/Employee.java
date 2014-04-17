package de.oio.vaadin.model;

import java.io.Serializable;

public class Employee implements Serializable {

  private String firstName = "";
  private String lastName = "";
  private Department department;

  public Employee() {
  }

  public Employee(String firstName, String lastName, Department department) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.department = department;
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

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }
}
