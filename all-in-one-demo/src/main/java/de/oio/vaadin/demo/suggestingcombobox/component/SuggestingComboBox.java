package de.oio.vaadin.demo.suggestingcombobox.component;

import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;

public class SuggestingComboBox extends ComboBox {

  public SuggestingComboBox() {
    // the item caption mode has to be PROPERTY for the filtering to work
    setItemCaptionMode(ItemCaptionMode.PROPERTY);

    // define the property name of the CountryBean to use as item caption
    setItemCaptionPropertyId("title");
  }

  /**
   * Overwrite the protected method
   * {@link com.vaadin.ui.ComboBox#buildFilter(String, com.vaadin.shared.ui.combobox.FilteringMode)} to return a custom
   * {@link de.oio.vaadin.demo.suggestingcombobox.component.SuggestingContainer.SuggestionFilter} which is only
   * needed to pass the given
   * filterString on to the {@link SuggestingContainer}.
   */
  @Override
  protected Filter buildFilter(String filterString, FilteringMode filteringMode) {
    return new SuggestingContainer.SuggestionFilter(filterString);
  }
}
