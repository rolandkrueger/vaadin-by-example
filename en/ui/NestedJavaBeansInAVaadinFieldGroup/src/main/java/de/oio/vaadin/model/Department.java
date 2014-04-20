package de.oio.vaadin.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A simple JavaBean which is referenced by the {@link Employee} entity bean.
 */
public class Department implements Serializable {

  private String name;
  private String departmentManager;

  public Department() {
  }

  public Department(String name, String departmentManager) {
    super();
    this.name = name;
    this.departmentManager = departmentManager;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDepartmentManager() {
    return departmentManager;
  }

  public void setDepartmentManager(String departmentManager) {
    this.departmentManager = departmentManager;
  }

  @Override
  public String toString() {
    return String.format("%s (Manager: %s)", name, departmentManager);
  }

  @Override
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(name);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Department other = (Department) obj;

    return Objects.equals(name, other.name);
  }

}
