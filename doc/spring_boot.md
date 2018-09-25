# Spring Boot integration

JWebForm works perfectly with spring-boot.

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
public class AddressForm implements FormGenerator{ 
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

## Controller

Write a controller, like this with SimpleJWebform<Myform> form as argument: 
(MyForm has to implement the FormGenerator interface!)

```Java

  @RequestMapping("/form")
  public String demoJWebForm(SimpleJWebForm<MyForm> form) {   // argument resolver will fill request-vars
  
    if (form.isOk()) {   // check if the form was submitted and is valid
      log.debug("Valid firstname from form:"  + form.getStringValue("firstname"));   // if everything was okay, we can get the values from the form
    }
    
    return "index"; // the template, that renders the form
  }

```

If you need to pass parameters to your formBuilder, use JWebForm and pass the FormGenerator instance to the run method.

Example

```Java

...
public String demoJWebForm(JWebForm form) {   // arguemnt resulover will fill request-vars
    FormResult formResult = form.run(new MyForm(LocalDate.now()).generateForm());   
    
    if (formResult.isOk()) {   // check if the form was submitted and is valid
...
```


Note: Output inside the template is exact like in the other frameworks. 
You will find the view in the model with the key "form". See: [Rendering the form in template](template.md)
