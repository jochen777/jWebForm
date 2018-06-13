# How to render the form within the template

After you called the "run" method on the Form, you get a FormResult. 
Ask the FormResult for a "View" and handle this view to the template.



```Java
// This creates a view object with a POPST-Method and HTML5 Validation 
model.addAttribute("form", formResult.getView());    

// This creates a view object with a POPST-Method and NO HTML5 Validation 
model.addAttribute("form", formResult.getView(false));    

// This creates a view object with a GET-Method and NO HTML5 Validation 
model.addAttribute("form", formResult.getView(false, "GET"));    

```



## Available data within the view

The view object provides these methods:


