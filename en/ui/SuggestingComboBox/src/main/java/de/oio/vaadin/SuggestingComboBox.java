package de.oio.vaadin;

import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;

import de.oio.vaadin.SuggestingContainer.SuggestionFilter;

public class SuggestingComboBox extends ComboBox {

  public SuggestingComboBox(String caption) {
    super(caption);
    // the item caption mode has to be PROPERTY for the filtering to work
    setItemCaptionMode(ItemCaptionMode.PROPERTY);

    // define the property name of the CountryBean to use as item caption
    setItemCaptionPropertyId("name");
  }

  public SuggestingComboBox() {
    this(null);
  }

  /**
   * Overwrite the protected method
   * {@link ComboBox#buildFilter(String, FilteringMode)} to return a custom
   * {@link SuggestionFilter} which is only needed to pass the given
   * filterString on to the {@link SuggestingContainer}.
   */
  @Override
  protected Filter buildFilter(String filterString, FilteringMode filteringMode) {
    return new SuggestingContainer.SuggestionFilter(filterString);
  }
}
