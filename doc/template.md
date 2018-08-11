# How to render the form within the template

After you called the "run" method on the Form, you get a FormResult. 
Ask the FormResult for a "View" and handle this view to the template.



```Java
// This creates a view object with a POPST-Method and HTML5 Validation 
model.addAttribute("form", formResult.getView());    

// This creates a view object with a POPST-Method and NO HTML5 Validation 
model.addAttribute("form", formResult.getView(View.Html5Validation.off));    

// This creates a view object with a GET-Method and NO HTML5 Validation 
model.addAttribute("form", formResult.getView(View.Html5Validation.off, View.Method.GET));    

```



## Available data within the view

The view object provides these methods (always with prefix "get"):

formId : ID of the form (important, if you have more than one form on your page)

method: Either POST or GET

isUploadEnctypeRequired: Indicates, if a special upload type is required (enctype="multipart/form-data")

isHtml5Validaiton: If the form should be validated with html 5

producerInfosContainer:  *ProducerInfosContainer* Object. 




## Available data within the ProducerInfosContainer


piList: List of *ProducerInfos* Object

piNao: Map of <Name, *ProducerInfos*>. For "manually" accessing the elements

names: List of the names of the elements


## Available data within the ProducerInfos

name: name of the element

html: rendered html

formId: Id of the form

value: the value as string

valueObject: the value as native object (for example the date or a boolean)

validationResult: the validation result

childs: childs as *ProducerInfos* if this is a complex element like date

tabIndex: the tabIndex

nameOfInput: The type-name (for example "SelectInput", "TextInput"...)

element: The element (type)

elementTypeName: fully classiefied class-name

decoration: The Decoration

validator: The validator object

elementNameInfoMap: a map that describes the element:

* "NameOfElement": true -> Holds a boolean "true" for this name. Allows in template engins "if elementNameInfo.XSRFProtectionType ..." 
* "type" -> {text|number|password|} (optional)
* "selected" -> List of select items, if type is select
* "radioElements" -> List of radio-elements if type is radio

## Available data within the ValidationResult

isValid: if the element is valid

message: Message that describes the problem, if not valid

errorVals: values, that can describe the error-message. Example: min/max values

## Available data within the Decoration

label: the label of the element
helptext: the helptext of the element
placeholder: the placeholder (if element supports this )


