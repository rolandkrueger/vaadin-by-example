package de.oio.vaadin;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import de.oio.vaadin.model.Department;
import de.oio.vaadin.model.Employee;

/**
 * Form for editing {@link Employee} instances. This form uses a
 * {@link FieldGroup} to bind an Employee {@link Item} to the form layout. This
 * form uses a {@link NativeSelect} in order to let the user choose one of the
 * available {@link Department} objects to be referenced by the edited
 * {@link Employee} instance. The container data source for this selection
 * component is externally provided through the constructor.
 */
public class EmployeeForm extends CustomComponent implements Button.ClickListener {

  private static final long serialVersionUID = -4657076247283424408L;

  private TextField firstName;
  private TextField lastName;
  private NativeSelect department;
  private Button okBtn, discardBtn;
  private BeanFieldGroup<Employee> fieldGroup;
  private BeanItemContainer<Employee> employeeContainer;

  /**
   * Constructor of the {@link EmployeeForm}.
   * 
   * @param employeeContainer
   *          the container that contains all {@link Employee} objects. This
   *          container is used as the container data source of the employee
   *          overview table
   * @param departments
   *          the container data source for the department selection component
   * @param selectionDescription
   *          descriptive text for the department selection component
   */
  public EmployeeForm(BeanItemContainer<Employee> employeeContainer, Container departments, String selectionDescription) {
    this.employeeContainer = employeeContainer;

    FormLayout layout = new FormLayout();
    firstName = new TextField("First name");
    firstName.setRequired(true);
    lastName = new TextField("Last name");
    lastName.setRequired(true);

    // create the department selection component
    department = new NativeSelect("Department");
    department.setContainerDataSource(departments);
    department.setNullSelectionAllowed(false);
    department.setRequired(true);

    layout.addComponent(firstName);
    layout.addComponent(lastName);
    layout.addComponent(new Label(selectionDescription, ContentMode.HTML));
    layout.addComponent(department);

    // add form buttons
    HorizontalLayout buttonBar = new HorizontalLayout();
    buttonBar.setSpacing(true);
    okBtn = new Button("Ok");
    okBtn.addClickListener(this);
    discardBtn = new Button("Discard");
    discardBtn.addClickListener(this);
    buttonBar.addComponent(okBtn);
    buttonBar.addComponent(discardBtn);
    layout.addComponent(buttonBar);

    // create FieldGroup
    fieldGroup = new BeanFieldGroup<Employee>(Employee.class);
    fieldGroup.setItemDataSource(new Employee());
    fieldGroup.bindMemberFields(this);

    setCompositionRoot(layout);
  }

  // make the department selection component available to the outside so that it
  // can be properly configured
  public NativeSelect getDepartmentSelector() {
    return department;
  }

  @Override
  public void buttonClick(ClickEvent event) {
    if (event.getSource() == okBtn) {
      try {
        fieldGroup.commit();
        // on successful commit, the currently edited Employee bean is added to
        // the table...
        employeeContainer.addBean(fieldGroup.getItemDataSource().getBean());
        // ... and a new Employee bean is created and set on the form
        fieldGroup.setItemDataSource(new Employee());
      } catch (CommitException e) {
        Notification.show("Validation failed", "Unable to commit input. Did you fill out all required fields?",
            Notification.Type.ERROR_MESSAGE);
      }
    } else {
      // discard all input if the discard button was clicked
      fieldGroup.discard();
    }
  }
}
