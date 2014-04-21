Select Nested JavaBeans With a Vaadin FieldGroup
====================================================================

Abstract
--------

This tutorial shows how to configure a FieldGroup that contains a selection component for selecting a nested JavaBean property of another JavaBean entity. The selection component for choosing the referenced JavaBean instance can be backed by different implementations of com.vaadin.data.Container. Depending on this container implementation, it can be necessary to define an own implementation of Vaadin's Converter interface in order for the FieldGroup's data binding to work properly.

-------------------

* __Tutorial__: [Select Nested JavaBeans With a Vaadin FieldGroup](http://blog.oio.de/)

* __Author__: Roland Krüger

* __Publishing Date__: 

* __Tutorial Language__: English

* __Vaadin Version__: 7.0+

Build
-----

To run the example, [Apache Maven](http://maven.apache.org) is needed. You can simply start the application by issuing the following command:

$ mvn package jetty:run

After the Jetty container was successfully started, you can visit the demo application with your browser at the following address: http://localhost:8080/NestedJavaBeansInAVaadinFieldGroup/

- - - - - - - - - -
This project is hosted on GitHub: https://github.com/rolandkrueger/vaadin-by-example
