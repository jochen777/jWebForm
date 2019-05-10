# Working with beans

Instead of defining your forms with the API via FormBuilder you can just define a POJO.
The Bean2Form class can convert a POJO to a jWebForm Form and fill it with the values coming from the form.

This can be very handy and you don't have to learn another api.

You have to use the jwebform-integration project for this. (This is included in the spring-project)

## Example

```Java

public class MyForm {
  
    @UseDecoration(label = "Your Firstname", helpText = "Please don't cheat here",
          placeholder = "Max Mustermann", isTranslated = true)
    public String firstname="";
    
    
    public String lastname="";
    
    @UseFieldType(TextAreaType.class)
    public String notes="";
    
    @UseFieldType(HiddenType.class)
    public String password="";
    
    boolean optIn=false;
    
    LocalDate birthday = LocalDate.now();
}
```

## Build in conversions

Theses types will be converted automatically for you.

| Java Type        | Converted to     |
| --------------- |-------------------|
| String | TextType |
| Integer | NumberType |
| Boolean | CheckboxType |
| LocalDate | TextDateType |

## Available Annotations

| Annotation       | Meaning     | Example     |
| --------------- |------------------------------------|-------------------|
| @UseFieldType | Forces a specific type | @UseFieldType(TextAreaType.class) String notes;|
| @IgnoreField | Ignores the field in the bean and will not process it | @IgnoreField String secret;|
| @UseDecoration | Fills in decoration for this field | @UseDecoration(label="Your first name") String firstname;|
| @IsRequired| Marks the field as required| @IsRequired |

## Bean Validation

Yes, you can use bean validation too. Be aware, that you have to tell this the Bean2Form class via the constructor. (automatically done for you in spring-boot-starter)

```Java

public class MyForm {

    @Size(min=3, max=20)    
    public String firstname="";
}
```

There are two Annoations that will be transferred to internal Criteria:

| Annotation        | Converted to     |Reason     |
| --------------- |-------------------|-------------------|
| @NotEmpty | Required | Will add (*) and "required" html5 attribute |
| @Size | MaxLenght | Will add a lenght attribute |

## Callbacks

If you implement the JWebFormBean interface, you have three callbacks:
(Note: You can extend AbstractJWebFormBean to have default implemntations for all of the three callbacks)

### preRun

can change the form after Bean2Form has processed it. 
Here you can finetune the form with something, the bean-api is not capable to.

### postRun

currently not implemented (TODO!)

### validate

This is the most useful: Here you can write your own validation rules. This is simmpler than the Bean Validation API.


## Translation

In a multi-language setup it is common to use .property files to store translated keys. If you use the themeRenderer, 
you can choose, whether you want to have translations in the Decoration or not. 

@Decoration(..., isTranslated=true) - will output the texts as they are
@Decoration(..., isTranslated=false) - will translate the text via .propertys (default) 

## Notes

* The bean has to be public acessible.
* All fields must be public and must be initialised. (Subject to change)
