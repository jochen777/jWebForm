# Build in validators

Here is a list of all Validators that are build in.

#### Accept

Check if the value matches exact one of the given values.

Usage:

```Java
        new TextType("cityUsa").of(new Validator(Criteria.Accept("New York", "Las Vegas")),
          new Decoration("EnterBig City in USA"));
          

```

Or if you want to ignore the case:

```Java
        new TextType("cityUsa").of(new Validator(Criteria.Accept("New York", "Las Vegas").ignoreCose()),
          new Decoration("EnterBig City in USA"));
          

```


Valid input:

```HTML
"New York"
```

Validation-Fail:

If the validation fails, the following translation-key will be used:

```HTML
jformchecker.allowed_values
```


Example Output for english:
 
```HTML
Please enter one of the allowed values: 'New York', 'Las Vegas'
```



