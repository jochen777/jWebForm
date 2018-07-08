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

The view object provides these variables:

formId : ID of the form (important, if you have more than one form on your page)

method: Either POST or GET

isUploadEnctypeRequired: Indicates, if a special upload type is required (enctype="multipart/form-data")

isHtml5Validaiton: If the form should be validated with html 5

drawableElements: List of *DrawableElement* Objects. They are used for low-capable template engines like mustache

elementNames: List of the names of all elements from the form

unrenderedElements: List of *Producer-Infos* for each Element.

allUnrenderedElements: Map of <elementName> and *ProducerInfos*

elements: Map of <elementName> and *RenderedElement*



## Available data within the DrawableElement


elementNameInfo: Map of Type-Name and Boolean True (so you can ask: {% if element.elementTypeInfo.TextType %})... and a type-definition ("text", "number", "password"...)

producerInfos: *ProducerInfo* - Object

childs: all childs, if we have a group element


## Available data within the RenderedElement

html: the rendered html.
producerInfos: *ProducerInfo* Object
elementResult: *ElementResult* Object

## Available data within the ProducerInfo

formId: the formId

tabIndex: The tabIndex

elementContainer: *ElementContainer* all static Informations about the element

childs: all childs if the element is a group.

elementResult: the result including validationResult (*ElementResult*)


## Available data within the ElementResult

validationResult: *ValidationResult* Object

value: Value as a String of that element

valueObject: Value as original Object (example: Checkbox -> Boolean)

staticElementInfo: *StaticElementInfo* Object

childs: Children as *ElementResult* Objects


## Available data within the ElementContainer

element: The type object itself (TextType, PasswordType...)

decoration: The *Decoration* object

validator: The avail decorator with all Validation-criteria

## Available data within the StaticElementInfo

name: the name of the element

htmlProducer: the HtmlProducer that can be asked for rendered HTML

tabIndexIncrement: Not important for output


## Available data within the ValidationResult

isValid: if the element is valid

message: Message that describes the problem, if not valid

errorVals: values, that can describe the error-message. Example: min/max values

## Available data within the Decoration

label: the label of the element
helptext: the helptext of the element
placeholder: the placeholder (if element supports this )


