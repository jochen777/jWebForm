# How to render the form within the template

After you called the "run" method on the Form, you get a FormResult. 
Pass the formResult to the template.



```Java
model.addAttribute("form", formResult);    

```



## Available data within the formResult

The formResult object provides the formModel with these methods (always with prefix "get"):

formId : ID of the form (important, if you have more than one form on your page)

method: Either POST or GET

isUploadEnctypeRequired: Indicates, if a special upload type is required (enctype="multipart/form-data")

isHtml5Validaiton: If the form should be validated with html 5

producerInfosContainer:  *ProducerInfosContainer* Object. 




## Available data within the ProducerInfosContainer


piList: List of *ProducerInfos* Object

piNao: Map of <Name, *ProducerInfos*>. For "manually" accessing the fields

names: List of the names of the fields


## Available data within the ProducerInfos

name: name of the field

html: rendered html

formId: Id of the form

value: the value as string

valueObject: the value as native object (for example the date or a boolean)

validationResult: the validation result

childs: childs as *ProducerInfos* if this is a complex field like date

tabIndex: the tabIndex

nameOfInput: The type-name (for example "SelectInput", "TextInput"...)

type: The type

typeName: fully classiefied class-name

decoration: The Decoration

validator: The validator object

fieldNameInfoMap: a map that describes the field:

* "NameOfField": true -> Holds a boolean "true" for this name. Allows in template engins "if fieldNameInfo.XSRFProtectionType ..." 
* "type" -> {text|number|password|} (optional)
* "selected" -> List of select items, if type is select
* "radioFields" -> List of radio-Fields if type is radio

## Available data within the ValidationResult

isValid: if the field is valid

message: Message that describes the problem, if not valid

errorVals: values, that can describe the error-message. Example: min/max values

## Available data within the Decoration

label: the label of the field
helptext: the helptext of the field
placeholder: the placeholder (if field supports this )


