package de.oio.vaadin.demo.fieldgroupselectnestedjavabeans;


import com.vaadin.data.Container;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import de.oio.vaadin.demo.fieldgroupselectnestedjavabeans.model.Employee;
import de.oio.vaadin.services.MessageProvider;

public class EmployeeForm extends CustomComponent implements Button.ClickListener {

  private static final long serialVersionUID = -4657076247283424408L;

  private TextField firstName;
  private TextField lastName;
  private NativeSelect department;
  private Button okBtn, discardBtn;
  private BeanFieldGroup<Employee> fieldGroup;
  private BeanItemContainer<Employee> employeeContainer;
  private MessageProvider messageProvider;

  public EmployeeForm(BeanItemContainer<Employee> employeeContainer, Container departments,
                      String selectionDescriptionKey, MessageProvider messageProvider) {
    this.employeeContainer = employeeContainer;
    this.messageProvider = messageProvider;

    FormLayout layout = new FormLayout();
    firstName = new TextField(messageProvider.getMessage("FieldGroupSelectNestedJavaBeans.firstname"));
    firstName.setRequired(true);
    lastName = new TextField(messageProvider.getMessage("FieldGroupSelectNestedJavaBeans.lastname"));
    lastName.setRequired(true);

    department = new NativeSelect(messageProvider.getMessage("FieldGroupSelectNestedJavaBeans.department"));
    department.setContainerDataSource(departments);
    department.setNullSelectionAllowed(false);
    department.setRequired(true);

    layout.addComponent(firstName);
    layout.addComponent(lastName);
    layout.addComponent(new Label(messageProvider.getMessage(selectionDescriptionKey), ContentMode.HTML));
    layout.addComponent(department);

    // add form buttons
    HorizontalLayout buttonBar = new HorizontalLayout();
    buttonBar.setSpacing(true);
    okBtn = new Button(messageProvider.getMessage("buttons.ok"));
    okBtn.addClickListener(this);
    discardBtn = new Button(messageProvider.getMessage("buttons.discard"));
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
        Notification.show(messageProvider.getMessage("FieldGroupSelectNestedJavaBeans.validationfailed"),
            messageProvider.getMessage("FieldGroupSelectNestedJavaBeans.validationerrormessage"),
            Notification.Type.ERROR_MESSAGE);
      }
    } else {
      fieldGroup.discard();
    }
  }
}
