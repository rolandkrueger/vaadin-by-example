package de.oio.vaadin;

/**
 * Simple entity class to be used as item data for the ComboBox.
 */
public class CountryBean {

  private Long id;
  private String name;

  public CountryBean() {
    super();
  }

  public CountryBean(Long id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

}
