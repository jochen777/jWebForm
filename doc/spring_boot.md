# Spring Boot integration

JWebForm works perfectly with spring-boot. (currently tested with 1.5.x and 2.0.x)

## Add dependency

Add this dependency to your project:

```xml
...
        <dependency>
            <groupId>de.cyclon-softworx</groupId>
            <artifactId>jwebform-spring-boot-starter</artifactId>
            <version>0.0.11</version>
        </dependency>
...
```

# Simple Case

## Define a Form

Define a POJO:
(See: [Working with beans](beans.md))

```Java
    public class MyForm {
  
      @UseDecoration(label="Firstname")
      public String firstname;
      
      @UseDecoration(label="Email")
      @NotEmpty
      @Email(message = "Email should be valid")
      public String email;
            
    }
    
```



## Controller

Write a controller, like this with ContainerFormRunner<Myform> form as argument: 

```Java

  @RequestMapping("/form")
  public String index(ContainerFormRunner<MyForm> form) {   // argument resolver will fill request-vars
  
    if (form.isValid()) {   // check if the form was submitted and is valid
      
      log.debug("Valid firstname from form:"  + form.getBean().firstname);   
      
    }
  }

```

## Template

In your template, just refer to the generated HTML. 
The default implementatin will deliver HTML for Bootstrap3.
You can adapt it to your own needs or render the form via 
your known template-mechanisms.  

```HTML

  {{{ form.formModel.html }}}

```


# Complex case

In case, you need greater control over your form, beans are sometimes to limited. 
(For example, one user needs more fields, than another)

### Define the form

Write a class, that builds the form. You have to implement the "FormGenerator" interface.
```Java
public class UserForm implements FormGenerator{ 

    private final String userFirstname;
    
    public MyForm(String userFirstname){
      this.userFirstname = userFirstname;
    }

    @Override public Form generateForm() {
      return FormBuilder.simple().typeBuilder(
        text("firstname", userFirstname).
          label("Firstname"), 
        text("email").
          label("Email").
          criteria(Criteria.required(), Criteria.email())
        ).build();
    }
}    
```

### Controller 

If you need to pass parameters to your formBuilder, use JWebForm and pass the FormGenerator instance to the run method.

Example

```Java

...
public String demoJWebForm(FormRunner form) {   // arguemnt resulover will fill request-vars
    UserForm userForm = new UserForm(userService.provideUsersFirstname());
    FormResult formResult = form.run(userForm);   
    
    if (formResult.isValid()) {   // check if the form was submitted and is valid
      log.info(userForm.getStringValue("firstname");  
...
```


Note: Output inside the template is exact like in the other frameworks. 
You will find the view in the model with the key __form.formModel__. See: [Rendering the form in template](template.md)

Additionally you will find in __form.formModel.html__ the html for the form coming from the theme (see jwebform-themes).
