# Compare jWebform to jFormchecker

Why did I rewrote the jFormchecker lib?

In short: jFormchecker was not clean code as we know it. 
The library itself did too much things at once, the classes were too big,
the API too bloated.

I tried to design jWebform more carefully and the result is far better:

The core library

## Core

* central form-definition for reuse and separation of concerns
* avoiding a lot of boilerplate code
* very compact output in sourcecode of template

## Details

* handling the request-response loop for forms
* handling error-messages and error-highlighting of the labels
* correct tab-order
* pre-fillments of form-elements
* automatically syntactical correct HTML (5) with label and "label-for"
* pre-fillments of user-input after submit
* max-lenght constraint on every element to avoid security issues (TBD)
* easy prebuild validation variants, easy custom validation
* all standard form fields and more (date-input)
* prepared for multilang-setups
* warn, if identical names are choosen
* automatically set correct transfer mode if fileupload elment is used

## Available extensions

* prebuild Bootstrap Styles via theme project
* prebuild ajax handling via integration project

## Architecture

* no dependencies
* mostly no nulls
* mostly immutable objects
* functional interfaces, lambdas
* clean code
* AAA rating in sonoarqube 
