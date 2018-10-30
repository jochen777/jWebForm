# jWebForm

HTML forms in Java made easy.  

(Please be aware, that this is beta software. API can still change in a greater detail. 
Wait for the version 1.0.0 to adapt it.)

[Quickstart](doc/quickstart.md)

[Documentation](doc/start.md)

[Changelog](doc/CHANGELOG.md)


## Overview

With jWebform you can define HTML forms in a clean way, 
fill them with request-variables, validate and build HTML out of them.
It gets you away from the tedious work around form handling in HTML projects.
It is framework agnostic, so it should work with *every* Java/Kotlin... Webframework around. 

Form-handling support in current Java MVC frameworks is often basic.
 jWebForm tries to fill this gap by providing these features:

* central form-definition for reuse and separation of concerns
* avoiding a lot of boilerplate code
* very compact output in sourcecode of template
* build in validation and security

More here: [Overview Features](doc/features.md)


jWebForm is for java what Symfony-Forms is for PHP or Rose::HTML for Perl.

Example project (Spring-Web MVC):

https://github.com/jochen777/jWebformExample 

## When to use it

jWebForm can be used everywhere you have to implement HTML or Ajax HTML-Forms.
Especially useful:
* When you define Forms in a generic way (in a CMS, where users can define Forms)
* When you have a lot of forms
* When you have form based web-apps
* When you have to implement professional, good looking HTML-Forms but don' want to spend much time for it.  



## Maven Dependency
```xml
...
<dependency>
    <groupId>de.cyclon-softworx</groupId>
    <artifactId>jWebForm</artifactId>
    <version>0.0.10</version> <!-- check on maven central for the latest version -->
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
