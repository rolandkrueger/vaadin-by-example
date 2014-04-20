package de.oio.vaadin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.oio.vaadin.model.Department;
import de.oio.vaadin.model.Employee;

@PreserveOnRefresh
public class NestedJavaBeansInAVaadinFieldGroupUI extends UI {

  private static final long serialVersionUID = -6733290242808872455L;

  private static final String DESCRIPTION = "This demo shows how to configure a FieldGroup that contains a selection component for "
      + "selecting a nested JavaBean property for another JavaBean entity. In this example, there is an entity bean 'Employee' "
      + "which contains a nested JavaBean property 'Department'. In the two forms shown on the tab-sheet you can add new Employee "
      + "objects to the employee table. The first form uses a BeanItemContainer as the container data source of the department "
      + "selection component. The second form uses an IndexedContainer as data model. For this second FieldGroup to work, it is "
      + "necessary to set a specific converter on the selection component. Of course, for the user both forms behave exactly the same "
      + "which is the whole purpose of this tutorial, after all. The difference between these two forms is only visible in code.";

  // @formatter:off
  private static List<Department> DEPARTMENTS = new ArrayList<Department>(Arrays.asList(
      new Department("Human Resources", "John Smith"),
      new Department("IT", "Dan Developer"),
      new Department("Accounting", "Jane Doe"),
      new Department("Engineering", "Marc Jones")
      ));
  // @formatter:on

  private EmployeeForm beanItemContainerForm;
  private EmployeeForm indexedContainerForm;
  private BeanItemContainer<Employee> employeeContainer;
  private BeanItemContainer<Department> departmentContainer;
  private IndexedContainer indexedContainer;

  @Override
  protected void init(VaadinRequest request) {
    buildDepartmentContainer();
    buildIndexedContainer();
    buildEmployeeContainer();
    setContent(buildLayout());
  }

  private Component buildLayout() {
    VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    layout.setSpacing(true);

    beanItemContainerForm = new EmployeeForm(employeeContainer, departmentContainer,
        "This selection component uses a <strong>com.vaadin.data.util.BeanItemContainer</strong> as its container data source:");
    buildIndexedContainerForm();

    TabSheet tabsheet = new TabSheet();
    tabsheet.addTab(beanItemContainerForm, "Department selector's container data source: BeanItemContainer");
    tabsheet.addTab(indexedContainerForm, "Department selector's container data source: IndexedContainer");

    layout.setWidth("100%");
    layout.addComponent(new Label(DESCRIPTION));
    layout.addComponent(tabsheet);
    layout.addComponent(buildEmployeeTable());
    return layout;
  }

  private Table buildEmployeeTable() {
    Table employeeTable = new Table();
    employeeTable.setWidth("100%");
    employeeTable.setContainerDataSource(employeeContainer);
    employeeTable.setVisibleColumns("firstName", "lastName", "department");
    employeeTable.setSelectable(true);
    return employeeTable;
  }

  private void buildIndexedContainerForm() {
    indexedContainerForm = new EmployeeForm(employeeContainer, indexedContainer,
        "This selection component uses a <strong>com.vaadin.data.util.IndexedContainer</strong> as its container data source:");
    indexedContainerForm.getDepartmentSelector().setConverter(new IndexToDepartmentConverter(indexedContainer));
    indexedContainerForm.getDepartmentSelector().setItemCaptionMode(ItemCaptionMode.ID);
    indexedContainerForm.getDepartmentSelector().setItemCaptionPropertyId("name");
  }

  @SuppressWarnings("unchecked")
  private void buildIndexedContainer() {
    indexedContainer = new IndexedContainer();
    indexedContainer.addContainerProperty("name", String.class, "");
    indexedContainer.addContainerProperty("bean", Department.class, null);

    for (Department department : DEPARTMENTS) {
      Object itemId = indexedContainer.addItem();
      Item item = indexedContainer.getItem(itemId);
      item.getItemProperty("name").setValue(department.getName());
      item.getItemProperty("bean").setValue(department);
    }
  }

  private void buildDepartmentContainer() {
    departmentContainer = new BeanItemContainer<Department>(Department.class);
    departmentContainer.addAll(DEPARTMENTS);
  }

  private void buildEmployeeContainer() {
    employeeContainer = new BeanItemContainer<Employee>(Employee.class);
    employeeContainer.addBean(new Employee("Mary", "Poppins", DEPARTMENTS.get(0)));
    employeeContainer.addBean(new Employee("Max", "Cromwell", DEPARTMENTS.get(1)));
  }
}
