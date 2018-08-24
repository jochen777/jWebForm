# jWebForm

HTML forms in Java made easy.  

[Quickstart](doc/quickstart.md)

[Documentation](doc/start.md)

[Changelog](doc/CHANGELOG.md)


## Overview

Form-handling support in current Java MVC frameworks is often basic. jWebForm tries to fill this gap by providing these features:

* central form-definition for reuse and separation of concerns
* avoiding a lot of boilerplate code
* very compact output in sourcecode of template
* build in validation and security

More here: [Overview Features](doc/features.md)


jWebForm is for java what Symfony-Forms is for PHP or Rose::HTML for Perl.

Example project (Spring-Web MVC):

https://github.com/jochen777/jWebForm-integration 



## The jWebForm way

The concept is simple: 

1. Define a form with every element in it

2. "run" it

3. pass the result to the template




## Maven Dependency
```xml
...
<dependency>
    <groupId>de.cyclon-softworx</groupId>
    <artifactId>jWebForm</artifactId>
    <version>0.0.9</version> <!-- check on maven central for the latest version -->
</dependency>
...
```

## A note about jFormchecker

What about jFormChecker? After a few months in production, 
I realised some points to improve and wanted to change the name,  
so I just rewrote it. jWebForm is jFormchecker2. 

[Improvements over jFormchecker](doc/jformchecker_compare.md)

## Documentation

[Documentation](doc/start.md)

## Requirements

Java 8 required.

## Thanks

Thanks to Arman Sharif for his work on jreform.sourceforge.net and the great Critera Classes
