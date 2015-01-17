Building a ComboBox which autocompletes with suggestions from the database
====================================================================

Abstract
--------

This code demonstrates a simple hack for writing a specialized ComboBox which gets its suggestions directly from the database without having to load all possible selection items into the user session up front.

-------------------

* __Tutorial__: [How to get autocomplete suggestions from the database for Vaadin’s ComboBox](http://blog.oio.de/2015/01/17/write-simple-auto-complete-combobox-vaadin/)

* __Author__: Roland Krüger

* __Publishing Date__: January 17th, 2015 

* __Tutorial Language__: English

* __Vaadin Version__: 7.0+

Build
-----

To run the example, [Apache Maven](http://maven.apache.org) is needed. You can simply start the application by issuing the following command:

$ mvn package jetty:run

After the Jetty container was successfully started, you can visit the demo application with your browser at the following address: http://localhost:8080/SuggestingComboBox/

- - - - - - - - - -
This project is hosted on GitHub: https://github.com/rolandkrueger/vaadin-by-example
