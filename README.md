Welcome to _vaadin-by-example_
==============================

__Learn Vaadin by working example projects.__
- - - - - - - - - - - - - - - - - - - - - - - - - - - 

The goal of this project is to provide learners of the  [Vaadin](http://www.vaadin.com/ "Vaadin") toolkit with working example projects for various problems which one might encounter while coding with Vaadin. The idea behind this is to complement articles and tutorials on Vaadin topics with code examples that will work out of the box.

Oftentimes, tutorials only provide code snippets to illustrate how the things they describe will work. These snippets only focus on the described problem. Beginners often have difficulties transferring these excerpts into a working piece of code.

This project aims at fixing that. It will provide working examples for tutorials that can be found either in the example project itself or at some given location in the web. To make understanding the concepts easier, the examples contain as much code as necessary and as litte code as possible.

Licensing
---------
Since the main intention of this project is to offer people example code that actually works, this code should also be usable as a template for copy & pasting parts of the examples into own projects. Therefore, the licensing for the examples' source code should be as unrestrictive as possible. People shall be able to use parts of the code as they see fit without having to bother with the requisites of more or less restrictive software licenses. The examples should therefore be licensed under unrestrictive licenses, such as the MIT license or similar.

Contribute!
-----------

Contributions to this project are welcome! This is not planned as a one-man-show, so go ahead and fork this project. There are some things to take into consideration, though, when contributing example projects.

* __Licensing__
You should use a license that is as unrestrictive as possible. See the section above about licensing for an explanation of the reason for that. Each example should contain the license text in a text file named 'LICENSE'.

* __Example Size__
Example projects should be kept as concise as possible so as to not distract learners too much from the core intention of the example code.

* __Working Out Of The Box__
Examples should be runnable without requiring a complex setup. Ideally, they should be accompanied with portable build mechanisms, such as a Maven pom.xml making the example quickly accessible with a simple _mvn jetty:run_.

* __No JAR Dependencies__
Examples should not contain the necessary dependencies as binary JAR files. These should be obtainable by users through other channels. Again, using Apache Maven or Ivy builds will facilitate that.

* __Accompanying Tutorials__
An example project should not stand all by itself. Every example should be accompanied by one or more tutorials that illustrate the background of the code. These tutorials could be contained directly in the example. Another option would be to provide a hyperlink to the location of the tutorial on the web in some read-me file. Besides that link, such a read-me file should contain an abstract of the respective tutorial.

* __Project Naming__
Names for example projects should be chosen such that the example's intention becomes clear already from the name. That said, refrain from generic project names such as 'HelloWorld' or 'VaadinExample'.

Disclaimer
----------
The name Vaadin and related trademarks/trade names are the property of Vaadin Ltd. and may not be used other than stated in [Vaadin's Terms of Service](https://vaadin.com/terms-of-service). Copyright to the Vaadin Framework is owned by Vaadin Ltd.