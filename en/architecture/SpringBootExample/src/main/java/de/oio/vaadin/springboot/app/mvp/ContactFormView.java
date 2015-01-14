package de.oio.vaadin.springboot.app.mvp;

import com.vaadin.data.fieldgroup.FieldGroup;

import de.oio.vaadin.springboot.app.data.Contact;
import de.oio.vaadin.springboot.app.services.MessageProvider;

public interface ContactFormView {

  public void setPresenter(ContactFormView.Presenter presenter);

  public void buildLayout();

  public interface Presenter {
    public void saveContact(Contact contact);

    public MessageProvider getMessageProvider();

    public FieldGroup getFieldGroup();

    public void onSaveClicked();

    public void onResetClicked();

    public void onDiscardClicked();
  }
}
