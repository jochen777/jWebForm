# Concept

First you need to build the form itself. The form contains elements and form-validators.
The elements ("ElementContainer") conists of a type ("FormType"), validators and a decoration.

## Form

The form can be filled with ElementContainers and Validators.

#### ElmentContainer

The ElementContainer holds: a type, some validation-rules and a decoration.

##### Type

There a lots of predefined types in jWebform:
* TextType (input type="text")
* CheckBoxType (input type="checkbox") 
* HiddenType (input type="hidden")
* ...

The build in types define all basic input-elements and some additional ones.

You can define your own types. You can even define groups of types.

##### Validations

Each type can be validated with some validation-criteria. You can define 0..n validation-criteria.
There a lots of validation - rules, for example:

* Length
* MinLenght
* MaxLenght
* Required
* Email...

You can define easily own validation critera.

##### Decoration

You can decorate each element with a "Decoration". This holds a Label, a Helptext and a Placeholder text.
You can extend this decoration to add more elements.

#### Validators

Each form can be validated by FormValidators. You can have 0..n FormValidators.
For example a registration form contains two Password - Elements. Now you can a apply
a FormValidator that compares these two elements if they are equal.



The FormCheckerForm is passed to the Formchecker. The formchecker prefills the form, triggers the validators and decides, if it is valid.

The Formbuilder renders the form to html. 

The template just makes a call to the formbuilder which returns the html for the form.


![Overview](overview.png "Overview jFormchecker")


## The Form

The Form is a class of type FormCheckerForm. Fill this class within the "init" method with the form-elements. This class holds every "moving" data.

Typical usage:

```Java

public class MyForm extends FormCheckerForm {

  public void init() {
  	 // Add a Textinputfield where you can enter your firstname
     add(TextInput.build("firstname").
     	setDescription("Your Firstname"));

  	 // Add a Textinputfield where you can enter your lastname
    add(TextInput.build("lastname").
    	setDescription("Your lastname:"));
	}
}
```


## The Formelements

The Formelements represent the input-fields for your form.

There is a large number of prebuild Formelements:

* Text

* Textfield

* Radio

* Checkbox

* FileUpload

* Password

* Select

* Date-Input (complex input that makes sure, that you get a valid date)

You can build your own Formelements, by extending from  AbstractInput and implementing FormCheckerElement.

Most important methods to overload:

```Java

public ExampleInput extends AbstractInput<ExampleInput> implements FormCheckerElement {

	public static ExampleInput build(String name, String moreParams) {
		ExampleInput e = new ExampleInput();
		e.setParam(moreParams);
		return e;
	}

	@Override
	public String getInputTag(Map<String, String> attributes) {
		return  "<example Input>";

	}
	
	@Override
	public void init(Request request, boolean firstRun, Validator validator) {
		...
	}
	// ...

} 


```

## The Formbuilder

The FormBuilder (GenericFormBuilder or your own subclasses) renders the form-elements and their structure to html. It keeps your forms consistence and avoids boilerplate in templates.

For example the FormBuilder renders the <form ...> </form> tags, the structure of an input element (help-text above or under a form-element, a special css-class ...).

It's a good idea to write a tailored FormBuilder for your site. You can have even multiple FormBuilder, for example one for the desktop-version and one for the mobile version.

There are some prebuild Formbuilders which you can use without coding them yourself:

"BasicBoostrapFormBuilder" -> for usage in Boostrap layouts

"TwoColumnBoostrapFormBuilder" -> for Bootstrap layouts too, but in a two column layout

"BasicMaterialLightFormBuilder" -> for MaterialLight (https://getmdl.io/components/index.html#textfields-section)

If you don't specify a FormBuilder, the BasicFormBuilder is used.

You can configure it like that

 ```Java

	// in your controller or configuration code
	FormChecker fc = new FormChecker("id4", (k) -> params.get(k));
	fc.setFormBuilder(new BasicBootstrapFormBuilder());
	

```
