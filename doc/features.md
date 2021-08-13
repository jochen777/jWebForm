# Features of jWebform

List of features:

## Core

* central form-definition for reuse and separation of concerns
* avoiding a lot of boilerplate code
* very compact output in sourcecode of template


## Details

* handling the request-response loop for forms
* handling error-messages and error-highlighting of the labels
* correct tab-order
* pre-fillments of form-fields
* automatically syntactical correct HTML (5) with label and "label-for"
* pre-fillments of user-input after submit
* max-lenght constraint on every field to avoid security issues (TBD)
* easy prebuild validation variants, easy custom validation
* all standard form fields and more (date-input)
* prepared for multilang-setups
* warn, if identical names are choosen
* allow groups of fields for complex types
* automatically set correct transfer mode if fileupload elment is used
* easy handling of mulitple forms on the same page
* form can be defined at runtime
* form can be build by a POJO. Annotations exist to tweak it. BeanValidation works
* allow more than one form per page

## Available extensions

* prebuild Bootstrap Styles via theme project
* prebuild ajax handling via integration project

## Architecture

* no dependencies
* runs in every web-framework
* mostly no nulls
* mostly immutable objects
* functional interfaces, lambdas
* clean code
* AAA rating in sonoarqube 


