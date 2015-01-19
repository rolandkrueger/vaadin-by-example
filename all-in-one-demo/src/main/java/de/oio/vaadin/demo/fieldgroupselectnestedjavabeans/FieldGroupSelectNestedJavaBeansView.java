package de.oio.vaadin.demo.fieldgroupselectnestedjavabeans;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import de.oio.vaadin.demo.fieldgroupselectnestedjavabeans.model.Department;
import de.oio.vaadin.demo.fieldgroupselectnestedjavabeans.model.Employee;
import org.vaadin.appbase.components.TranslatedCustomLayout;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.service.templating.TemplateData;
import org.vaadin.appbase.view.IView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldGroupSelectNestedJavaBeansView extends TranslatedCustomLayout {

  private final IMessageProvider messageProvider;

  // @formatter:off
  private static List<Department> DEPARTMENTS = new ArrayList<Department>(Arrays.asList(
      new Department("Human Resources", "John Smith"),
      new Department("IT", "Dan Developer"),
      new Department("Accounting", "Jane Doe"),
      new Department("Engineering", "Marc Jones")
      ));
  // @formatter:on
  private Table employeeTable;
  private TabSheet tabsheet;
  private BeanItemContainer<Employee> employeeContainer;
  private BeanItemContainer<Department> departmentBeanItemContainer;
  private IndexedContainer departmentIndexedContainer;

  public FieldGroupSelectNestedJavaBeansView(TemplateData layoutData, IMessageProvider messageProvider) {
    super(layoutData);
    this.messageProvider = messageProvider;
  }

  @Override
  public IView buildLayout() {
    super.buildLayout();
    buildDepartmentContainer();
    buildIndexedContainer();
    buildEmployeeContainer();
    VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    layout.setSpacing(true);

    layout.addComponent(buildTabsheet());
    layout.addComponent(buildEmployeeTable());
    getLayout().addComponent(layout, "layout");

    return this;
  }

  @SuppressWarnings("unchecked")
  private void buildIndexedContainer() {
    departmentIndexedContainer = new IndexedContainer();
    departmentIndexedContainer.addContainerProperty("name", String.class, "");
    departmentIndexedContainer.addContainerProperty("bean", Department.class, null);

    for (Department department : DEPARTMENTS) {
      Object itemId = departmentIndexedContainer.addItem();
      Item item = departmentIndexedContainer.getItem(itemId);
      item.getItemProperty("name").setValue(department.getName());
      item.getItemProperty("bean").setValue(department);
    }
  }

  private TabSheet buildTabsheet() {
    tabsheet = new TabSheet();
    tabsheet.addTab(buildBeanItemContainerTab(),
        messageProvider.getMessage("FieldGroupSelectNestedJavaBeans.beanItemContainerTab"));
    tabsheet.addTab(buildIndexedContainerTab(),
        messageProvider.getMessage("FieldGroupSelectNestedJavaBeans.indexedContainerTab"));
    return tabsheet;
  }

  private void buildEmployeeContainer() {
    employeeContainer = new BeanItemContainer<Employee>(Employee.class);
    employeeContainer.addBean(new Employee("Mary", "Poppins", DEPARTMENTS.get(0)));
    employeeContainer.addBean(new Employee("Max", "Cromwell", DEPARTMENTS.get(1)));
  }

  private void buildDepartmentContainer() {
    departmentBeanItemContainer = new BeanItemContainer<Department>(Department.class);
    departmentBeanItemContainer.addAll(DEPARTMENTS);
  }

  private Table buildEmployeeTable() {
    employeeTable = new Table();
    employeeTable.setWidth("100%");
    employeeTable.setContainerDataSource(employeeContainer);
    employeeTable.setVisibleColumns("firstName", "lastName", "department");
    employeeTable.setColumnHeaders(messageProvider.getMessage("FieldGroupSelectNestedJavaBeans.firstname"),
        messageProvider.getMessage("FieldGroupSelectNestedJavaBeans.lastname"),
        messageProvider.getMessage("FieldGroupSelectNestedJavaBeans.department"));
    employeeTable.setSelectable(true);
    return employeeTable;
  }

  private Component buildBeanItemContainerTab() {
    EmployeeForm form = new EmployeeForm(employeeContainer, departmentBeanItemContainer,
        "FieldGroupSelectNestedJavaBeans.beanItemContainerInfo", messageProvider);
    return form;
  }

  private Component buildIndexedContainerTab() {
    EmployeeForm form = new EmployeeForm(employeeContainer, departmentIndexedContainer,
        "FieldGroupSelectNestedJavaBeans.indexedContainerInfo", messageProvider);
    form.getDepartmentSelector().setConverter(new IndexToDepartmentConverter(departmentIndexedContainer));
    form.getDepartmentSelector().setItemCaptionMode(ItemCaptionMode.ID);
    form.getDepartmentSelector().setItemCaptionPropertyId("name");
    return form;
  }
}
