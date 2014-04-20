package de.oio.vaadin.demo.fieldgroupselectnestedjavabeans;

import java.util.Locale;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;

import de.oio.vaadin.demo.fieldgroupselectnestedjavabeans.model.Department;

public class IndexToDepartmentConverter implements Converter<Object, Department> {

  private static final long serialVersionUID = -4854942758136647077L;

  private IndexedContainer container;

  public IndexToDepartmentConverter(IndexedContainer container) {
    this.container = container;
  }

  @Override
  public Department convertToModel(Object itemID, Class<? extends Department> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {
    if (itemID == null) {
      return null;
    }

    Item item = container.getItem(itemID);
    if (item == null) {
      return null;
    }

    Department model = (Department) item.getItemProperty("bean").getValue();
    return model;
  }

  @Override
  public Integer convertToPresentation(Department model, Class<? extends Object> targetType, Locale locale)
      throws com.vaadin.data.util.converter.Converter.ConversionException {

    for (Object itemId : container.getItemIds()) {
      Item item = container.getItem(itemId);
      if (model == null) {
        return null;
      }

      if (model.equals((Department) item.getItemProperty("bean").getValue())) {
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
