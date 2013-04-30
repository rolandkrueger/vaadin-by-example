Vaadin’s Variable Scopes: VaadinSession and UI
==============================================================

Abstract
--------

This demo application demonstrates the two different scopes of a Vaadin 7 session. These are the common session scope and the new UI scope. The UI scope represents the data needed for the contents of one browser window or tab opened from the same session. This application lets you edit one session-scoped and one UI-scoped variable. It then demonstrates that for each browser window opened from the same session, a new UI object is created that contains its own version of the UI-scoped variable.

- - - - - - - - -

* __Tutorial__: [Vaadin’s Variable Scopes: VaadinSession and UI](http://blog.oio.de/2013/02/22/vaadins-variable-scopes-vaadinsession-and-ui/)

* __Author__: Roland Krüger

* __Publishing Date__: February 17th, 2013

* __Tutorial Language__: English

* __Vaadin Version__: 7.0.0 or higher

Build
-----

To run the example, [Apache Maven](http://maven.apache.org) is needed. You can simply start the application by issuing the following command:

$ mvn package jetty:run

After the Jetty container was successfully started, you can visit the demo application with your browser at the following address: http://localhost:8080/SessionAndUIScope/

- - - - - - - - - -
This project is hosted on GitHub: https://github.com/rolandkrueger/vaadin-by-example
