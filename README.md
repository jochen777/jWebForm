# jWebForm

HTML Forms in Java made easy. (currently in experimental stage!!)  

[Quickstart](doc/quickstart.md)

[Documentation](doc/start.md)

[See changelog](doc/CHANGELOG.md)

[![Build Status](https://travis-ci.org/jochen777/jFormchecker.svg?branch=master)](https://travis-ci.org/jochen777/jFormchecker)

What about jFormChecker? After some months in production, I realised some points to improve, so I just rewrote it. So jWebForm is "jFormchecker 2". 

## Overview

The form-handling support in current java MVC frameworks is very basic. jWebForm tries to fill this gap by providing these features:

* central form-definition for reuse and separation of concerns
* handling the request-response loop for forms
* handling error-messages and error-highlighting of the label
* correct tab-order
* pre-fillments of form-elements
* automatically syntactical correct HTML (5) with label and "label-for"
* pre-fillments of user-input after submit
* max-lenght constraint on every element to avoid security issues
* easy validation, easy custom validation
* all standard form fields and more (date-input)
* very compact output in sourcecode
* prebuild Bootstrap Styles
* prebuild ajax handling
* avoiding a lot of boilerplate code
* translateable error-messages.
* minimal dependencies!
* automatically set correct transfer mode if fileupload elment is used
* warn, if identical names are choosen


jWebForm is for java what Symfony-Forms is for PHP or Rose::HTML for Perl.

See this project for integration into servlet-based frameworks:

https://github.com/jochen777/jWebForm-integration (to be done)



## The jWebForm way

The concept is simple: 

1. Define a form with every element in it

2. "run" it

3. use a simple tag in the template to generate the html




## Maven Dependency
```xml
...
<dependency>
    <groupId>de.cyclon-softworx</groupId>
    <artifactId>jWebForm</artifactId>
    <version>x.x.x</version> <!-- check on maven central for the latest version -->
</dependency>
...
```

## Sourcecode formatting

Please use the sourcecodeformatting from google for java:

https://github.com/google/styleguide/blob/gh-pages/eclipse-java-google-style.xml

## Thanks

Thanks to Arman Sharif for his work on jreform.sourceforge.net and the great Critera Classes