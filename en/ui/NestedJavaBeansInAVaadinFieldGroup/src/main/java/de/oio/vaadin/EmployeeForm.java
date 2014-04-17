package de.oio.vaadin;

import com.vaadin.data.Container;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
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

import de.oio.vaadin.model.Employee;

public class EmployeeForm extends CustomComponent implements Button.ClickListener {

  private static final long serialVersionUID = -4657076247283424408L;

  private TextField firstName;
  private TextField lastName;
  private NativeSelect department;
  private Button okBtn, discardBtn;
  private BeanFieldGroup<Employee> fieldGroup;
  private BeanItemContainer<Employee> employeeContainer;

  public EmployeeForm(BeanItemContainer<Employee> employeeContainer, Container departments, String selectionDescription) {
    this.employeeContainer = employeeContainer;

    FormLayout layout = new FormLayout();
    firstName = new TextField("First name");
    firstName.setRequired(true);
    lastName = new TextField("Last name");
    lastName.setRequired(true);

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

  public NativeSelect getDepartmentSelector() {
    return department;
  }

  @Override
  public void buttonClick(ClickEvent event) {
    if (event.getSource() == okBtn) {
      try {
        fieldGroup.commit();
        employeeContainer.addBean(fieldGroup.getItemDataSource().getBean());
        fieldGroup.setItemDataSource(new Employee());
      } catch (CommitException e) {
        Notification.show("Validation failed", "Unable to commit input. Did you fill out all required fields?",
            Notification.Type.ERROR_MESSAGE);
      }
    } else {
      fieldGroup.discard();
    }
  }
}
