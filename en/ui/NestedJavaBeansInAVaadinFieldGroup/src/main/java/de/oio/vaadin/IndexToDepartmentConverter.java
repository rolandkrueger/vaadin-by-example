package de.oio.vaadin;

import java.util.Locale;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;

import de.oio.vaadin.model.Department;

/**
 * {@link Converter} implementation for converting the item IDs of an
 * {@link IndexedContainer} into the corresponding domain model, which is the
 * {@link Department} entity bean class in this example.
 */
public class IndexToDepartmentConverter implements Converter<Object, Department> {

  private static final String BEAN_PROPERTY_ID = "bean";

  private static final long serialVersionUID = -4854942758136647077L;

  /**
   * {@link IndexedContainer} instance that contains the Department beans in an
   * item property with ID "bean".
   */
  private IndexedContainer container;

  public IndexToDepartmentConverter(IndexedContainer container) {
    this.container = container;
  }

  @Override
  public Department convertToModel(Object itemID, Class<? extends Department> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {

    // return null if the given item ID object is null to prevent
    // NullPointerExceptions
    if (itemID == null) {
      return null;
    }

    // fetch the item referenced by the given item ID from the container
    Item item = container.getItem(itemID);
    if (item == null) {
      return null;
    }

    // read the property with ID "bean" from this item and return the property's
    // value as a Department instance
    return (Department) item.getItemProperty(BEAN_PROPERTY_ID).getValue();
  }

  @Override
  public Integer convertToPresentation(Department model, Class<? extends Object> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {

    // return null if the given model object is null to prevent
    // NullPointerExceptions
    if (model == null) {
      return null;
    }

    // walk over all item IDs in the container
    for (Object itemId : container.getItemIds()) {
      // fetch the current item
      Item item = container.getItem(itemId);
      // if the item's "bean" property contains the Department object passed in
      // as parameter then the item's ID is returned.
      if (model.equals((Department) item.getItemProperty(BEAN_PROPERTY_ID).getValue())) {
        return (Integer) itemId;
      }
    }

    return null;
  }

  @Override
  public Class<Department> getModelType() {
    return Department.class;
  }

  @Override
  public Class<Object> getPresentationType() {
    return Object.class;
  }
}
