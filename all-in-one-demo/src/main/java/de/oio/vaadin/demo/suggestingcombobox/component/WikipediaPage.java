package de.oio.vaadin.demo.suggestingcombobox.component;

/**
 * Simple entity class to be used as item data for the ComboBox.
 */
public class WikipediaPage {

  private Long id;
  private String title;

  public WikipediaPage() {
    super();
  }

  public WikipediaPage(Long id, String title) {
    super();
    this.id = id;
    this.title = title;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return title;
  }

}
