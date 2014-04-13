package de.oio.vaadin.demo.fieldgroupselectnestedjavabeans;

import org.vaadin.appbase.components.TranslatedCustomLayout;
import org.vaadin.appbase.view.IView;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

import de.oio.vaadin.demo.fieldgroupselectnestedjavabeans.model.Department;
import de.oio.vaadin.demo.fieldgroupselectnestedjavabeans.model.Employee;

public class FieldGroupSelectNestedJavaBeansView extends TranslatedCustomLayout {

  private Table employeeTable;
  private BeanItemContainer<Employee> employeeContainer;
  private BeanItemContainer<Department> departmentContainer;

  public FieldGroupSelectNestedJavaBeansView() {
    super("demos/FieldGroupSelectNestedJavaBeans");
  }

  @Override
  public IView buildLayout() {
    super.buildLayout();
    buildDepartmentContainer();
    employeeContainer = new BeanItemContainer<Employee>(Employee.class);
    getLayout().addComponent(new Label(), "tabsheet");
    getLayout().addComponent(buildEmployeeTable(), "employeeTable");

    return this;
  }

  private void buildDepartmentContainer() {
    departmentContainer = new BeanItemContainer<Department>(Department.class);
    departmentContainer.addBean(new Department("Human Resources", "John Smith"));
    departmentContainer.addBean(new Department("IT", "Dan Developer"));
    departmentContainer.addBean(new Department("Accounting", "Jane Doe"));
    departmentContainer.addBean(new Department("Engineering", "Marc Jones"));
  }

  private Table buildEmployeeTable() {
    employeeTable = new Table();
    employeeTable.setContainerDataSource(employeeContainer);
    return employeeTable;
  }

}
