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

drawableElements: List of DrawableElement Objects. They are used for low-capable template engines like mustache

elementNames: List of the names of all elements from the form

unrenderedElements: List of Producer-Infos for each Element.

allUnrenderedElements: Map of <elementName> and ProducerInfos

elements: Map of <elementName> and RenderedElement



## Available data within the DrawableElement


elementNameInfo: Map of Type-Name and Boolean True (so you can ask: {% if element.elementTypeInfo.TextType %})... and a type-definition ("text", "number", "password"...)

producerInfos: ProducerInfo - Object

childs: all childs, if we have a group element


## Available data within the RenderedElement

html: the rendered html.
producerInfos: ProducerInfo Object
elementResult: ElementResult Object

## Available data within the ProducerInfo



formId: the formId

tabIndex: The tabIndex

elementContainer: all static Informations about the element

childs: all childs if the element is a group.

elementResult: the result including validationResult


## Available data within the ElementResult

