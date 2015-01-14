package de.oio.vaadin.springboot.app;

import de.oio.vaadin.springboot.app.data.Contact;
import de.oio.vaadin.springboot.app.jpa.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Create example data after the application's start.
 */
@Configuration
public class SpringApplicationInitializer implements ServletContextInitializer {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        createDataContainer();
    }

    /**
     * Create data for the contact table.
     */
    private void createDataContainer() {
        // Persist two entries
        Contact contact = new Contact("Dieter", "Develop",
                "Weinheimer Str. 68, 68309 Mannheim", "0621/555-1234", null,
                "Java Expert", "dieter.develop@example.com");
        contactRepository.save(contact);

        contact = new Contact("Jane", "Doe", "Ulmenstr. 23, 69488 Birkenau",
                "06201/555-9876", null, "Vaadin Pro", "jane.doe@example.com");
        contactRepository.save(contact);
    }
}
