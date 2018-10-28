# jwebform changelog

### 0.0.11 

2018/xx/xx 

* Improve documentation (spring boot, more advantages)
* Move all jWebForm related projects to one single repository, introduce multi-module maven project
* Introduce Spring-Boot integration via jwebform-spring-boot-starter
* Introduce Bean2Form converter
* View can be exchanged by providing via ViewBuilder
* Bean can implement "JWebformBean" and offer some useful callbacks (like validation and post-Form process)


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
