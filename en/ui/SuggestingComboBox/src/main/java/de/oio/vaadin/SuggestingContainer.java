package de.oio.vaadin;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.UnsupportedFilterException;

import java.util.List;

/**
 * This is a specialized {@link BeanItemContainer} which redefines the filtering
 * functionality by overwriting method
 * {@link com.vaadin.data.util.AbstractInMemoryContainer#addFilter(Filter)}.
 * This method is called internally by the filtering code of a ComboBox.
 */
public class SuggestingContainer extends BeanItemContainer<CountryBean> {

  private CountryBean defaultCountry;
  private DatabaseAccessService service;

  public SuggestingContainer(DatabaseAccessService service) throws IllegalArgumentException {
    super(CountryBean.class);
    this.service = service;
  }

  public SuggestingContainer(DatabaseAccessService service, CountryBean defaultCountry) throws IllegalArgumentException {
    this(service);
    addBean(defaultCountry);
    this.defaultCountry = defaultCountry;
  }

  /**
   * This method will be called by ComboBox each time the user has entered a new
   * value into the text field of the ComboBox. For our custom ComboBox class
   * {@link SuggestingComboBox} it is assured by
   * {@link SuggestingComboBox#buildFilter(String, com.vaadin.shared.ui.combobox.FilteringMode)}
   * that only instances of {@link SuggestionFilter} are passed into this
   * method. We can therefore safely cast the filter to this class. Then we
   * simply get the filterString from this filter and call the database service
   * with this filterString. The database then returns a list of country objects
   * whose country names begin with the filterString. After having removed all
   * existing items from the container we add the new list of freshly filtered
   * country objects.
   */
  @Override
  protected void addFilter(Filter filter) throws UnsupportedFilterException {
    SuggestionFilter suggestionFilter = (SuggestionFilter) filter;
    filterItems(suggestionFilter.getFilterString());
  }

  private void filterItems(String filterString) {
    if ("".equals(filterString)) {
      if (defaultCountry != null) {
        // if "nothing" has been selected from the dropdown list and a default value is defined, add this default
        // value to this container so that it can be selected as the current value.
        addBean(defaultCountry);
      }
      return;
    }

    removeAllItems();
    List<CountryBean> countries = service.filterCountryTableInDatabase(filterString);
    addAll(countries);
  }

  /**
   * This method makes sure that the selected value is the only value shown in the dropdown list of the ComboBox when
   * this is explicitly opened with the arrow icon. If such a method is omitted, the dropdown list will contain the
   * most recently suggested items.
   */
  public void setSelected_this_is_a_custom_method(CountryBean country) {
    removeAllItems();
    addBean(country);
  }

  /**
   * The sole purpose of this {@link Filter} implementation is to transport the
   * current filterString (which is a private property of ComboBox) to our
   * custom container implementation {@link SuggestingContainer}. Our container
   * needs that filterString in order to fetch a filtered country list from the
   * database.
   */
  public static class SuggestionFilter implements Container.Filter {

    private String filterString;

    public SuggestionFilter(String filterString) {
      this.filterString = filterString;
    }

    public String getFilterString() {
      return filterString;
    }

    @Override
    public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
      // will never be used and can hence always return false
      return false;
    }

    @Override
    public boolean appliesToProperty(Object propertyId) {
      // will never be used and can hence always return false
      return false;
    }
  }
}
