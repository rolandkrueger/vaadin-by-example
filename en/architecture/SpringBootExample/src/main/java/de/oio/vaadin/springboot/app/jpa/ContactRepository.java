package de.oio.vaadin.springboot.app.jpa;

import de.oio.vaadin.springboot.app.data.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * We need only basic CRUD functionality, so a standard Spring Data repository suffices.
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
