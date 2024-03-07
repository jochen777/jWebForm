# jwebform changelog

### 0.0.15

2024/03/07

* Fix bug in FormRunner in Spring integration (rendered form can be accessed via form.html)
* fix label id generation in sourcecode-renderer (click on label works again)
* New formRunner.runWithFormSupplier method
* Fix Session-Management in Spring integration
* Move to Spring 3x
* Move to Java11 (remove javax dependencies)

### 0.0.14 

2021/07/01

* Allow untranslated Messages in beans. ( Example @Decoration(label="Dein Name", isTranslated=true) )
* Allow uninitialised variables in beans.
* Improved documentation
* Bump dependencies (security...)

### 0.0.13 

2019/04/03

* Fix email regexp (allow lowercase chars)
* Fix typo in method
* Allow invidiudal order in years on year-select (ie: 2000 - 2002 will result in 2000,2001,2002 and 2002-2000 will result in 2002,2001,2000)



### 0.0.12 

2019/01/22 

* Improve theme via sourcecode renderer. Adaption to 4.1.3 Bootstrap 
* Architectural shift from subclassing FormResult to ResultProcessors. Improves extensibility and results in less code
* Ajax support 
* SubmitType now have a value from the request. This allows handling more than one submit button per form.

### 0.0.11 

2018/11/25 

* Improve documentation (spring boot, more advantages)
* Move all jWebForm related projects to one single repository, introduce multi-module maven project
* Introduce Spring-Boot integration via jwebform-spring-boot-starter
* Introduce Bean2Form converter
* View can be exchanged by providing via ViewBuilder
* Bean can implement "JWebformBean" and offer some useful callbacks (like validation and post-Form process)
* Rename View to FormModel
* SelectDate now with optional initial value

### 0.0.10 

2018/08/31 

* Improve documentation
* Refactor for better naming
* Small performance improvement in Validator
* Fields are getting list of categories instead of validator object
* Bugfixes in Date and Number handling
* Date and Number return Optional<> 
* Convenience method for LoggingFormResult
* Requests can be chained with "andThen"
* Add more tests


### 0.0.9 

2018/08/18 Initial release
