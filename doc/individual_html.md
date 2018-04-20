# Output of individual HTML

If you need greater control over the HTML and the generic HTML generation is not suitable for you, you can choose the "View".  


## Compose the form in the template

I used the excellent template engine "Pebble" for the examples. (See: http://www.mitchellbosecke.com/pebble/home)
But of course, this will work in freemarker or the other template engines as well!

### Complete Form

If you want to build the complete form, just controlled by the FormBuilder Class, use this:

```java
...

 // in your controller
 ModelAndView mv = new ModelAndView();
 ...
 mv.addObject("fc", fc.getView());
 ...
 
...
```



```html
...

<!-- in your html template -->
{{fc.form|raw}}

...
```

### Building the form element by element


```html
...
{{fc.start|raw}}

{{fc.label("firstname", {"onclick":"alert('hello')"})|raw}}
{{fc.input("firstname")|raw}}
{{fc.help(name)|raw}}

...

{{fc.submit|raw}}
{{fc.end|raw}}
...
```


### Example of a macro

```html
...

{% macro input(name, fc) %}
<div class="form-group {{ fc.isError(name)?"has-error": "has-success" }} ">
{% if fc.isError(name) %}
<span style="color: red">{{fc.error(name) }}</span>
{% endif %}
{{fc.label(name) | raw }}
<div class="col-sm-10">
{{fc.input(name)|raw}}
{% if fc.help(name) is not empty %}
<span id="helpBlock_{{name}}" class="help-block">{{ fc.help(name) }}</span>
{% endif %}
</div>
</div>
{% endmacro %}

{{ fc.start | raw}}

{{ input("firstname") }}

...

{{ fc.submit | raw }}
{{ fc.end | raw }}
...
```

## Full API for templates:

### Full form

Get full template, as build by the FormBuilder:
(This is the preferred way)

```html
...
{{ fc.form | raw }} <!-- pebble syntax -->
...
```

### Form - Tags

Get the start- and end- HTML of the form. (Means <form...>  and </form>. The "start" tag will output as well additional tags, to get XSRF protection and submit-detection.

```html
...
{{fc.start | raw }}
...
{{ fc.end| raw }}
...
```

### Label - Tag

Get the label tag of a form element

```html
...
{{fc.label("firstname") | raw }}
...
```

Additionally, you can add individual attributes, that will be added to the label-tag:

```html
...
{{fc.label("firstname", {"onclick":"alert('hello')"}) | raw }}
...
```

### Input - Tag

Get the Input tag of a form element

```html
...
{{fc.input("firstname") | raw }}
...
```

Additionally, you can add individual attributes, that will be added to the input-tag:

```html
...
{{fc.input("firstname", {"onclick":"alert('hello')"}) | raw }}
...
```

### Help - Tag

Get the Help tag of a form element

```html
...
{{fc.help("firstname") | raw }}
...
```

### Errors

Get the (potential) errors of a form element

```html
...
{{fc.error("firstname") }}
...
```

You can check if you have errors with:

```html
...
{% if fc.isError("firstname") %}
<span class="error">{{fc.error("firstname") }}</span>
{% endif %}
...
```

### Input type

Get the type of an input - tag (For example: If you ask a checkbox you will get "checkbox" string.)

```html
...
{% if fc.type("firstname") == "text" %}
<span class="textbox">{{ fc.input("firstname") | raw }}</span> 
...
```

### All input elements

Get all names of input-elements, that were defined in the form:

```html
...
{% for elementName in fc.elementNames %}
{{ fc.input(elementName) | raw }}
...
{% endfor %} 
...
```

### Submit button

Get the submit button

```html
...
{{ fc.sbumit() | raw }} 
...
```


### Non capable template engines (since 0.1.8)

If you work with simpler template engines like mustache, you can't pass arguments to objects like in pebble.
For this case, you can use the this syntax:
Remember to pass formchecker differently to the model: 

```java
...

 // in your controller
 ModelAndView mv = new ModelAndView();
 ...
 mv.addObject("fc", fc.getView().generateAccessObjects());
 ...
 
...
```

```html
...
{{{ fc.input.firstname }}}
{{{ fc.label.firstname }}}
<!-- ... --> 
...
```
