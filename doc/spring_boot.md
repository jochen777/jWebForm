# Spring Boot integration

JWebForm works perfectly with spring-boot. (currently tested with 1.5.x and 2.0.x)

## Add dependency

Add this dependency to your project:

```xml
...
        <dependency>
            <groupId>de.cyclon-softworx</groupId>
            <artifactId>jwebform-spring-boot-starter</artifactId>
            <version>0.0.10-SNAPSHOT</version>
        </dependency>
...
```

## Define a form

Write a class, that builds the form. You have to implement the "FormGenerator" interface.
```Java
public class MyForm implements FormGenerator{ 
    @Override public Form generateForm() {
      return FormBuilder.simple().typeBuilder(
        text("firstname").
          label("Firstname"), 
        text("email").
          label("Email").
          criteria(Criteria.required(), Criteria.email())
        ).build();
    }
}    
```

or define a POJO:
(See: [Working with beans](beans.md))

```Java
    public class MyForm {
  
      @UseDecoration(label="Firstname")
      String firstname;
      
      @UseDecoration(label="Email")
      @NotEmpty
      @Email(message = "Email should be valid")
      String email;
            
    }
    
```



## Controller

Write a controller, like this with SimpleJWebform<Myform> form as argument: 
(MyForm has to implement the FormGenerator interface!)

```Java

  @RequestMapping("/form")
  public String demoJWebForm(ContainerFormRunner<MyForm> form) {   // argument resolver will fill request-vars
  
    if (form.isSubmittedAndOk()) {   // check if the form was submitted and is valid
      log.debug("Valid firstname from form:"  + form.getStringValue("firstname"));   // if everything was okay, we can get the values from the form
      
      log.debug("Valid firstname from form:"  + form.getBean().firstname);   // in case you used the POJO way
      
    }
    
    return "index"; // the template, that renders the form
  }

```

If you need to pass parameters to your formBuilder, use JWebForm and pass the FormGenerator instance to the run method.

Example

```Java

...
public String demoJWebForm(FormRunner form) {   // arguemnt resulover will fill request-vars
    FormResult formResult = form.run(new MyForm(LocalDate.now()));   
    
    if (formResult.isSubmittedAndOk()) {   // check if the form was submitted and is valid
...
```


Note: Output inside the template is exact like in the other frameworks. 
You will find the view in the model with the key __form__. See: [Rendering the form in template](template.md)

Additionally you will find in __form_rendered__ an object that can output the rendered html via "form_rendered.html".
