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

#### And

Can connect several Criteria

Usage

```Java
        new TextType("cityUsa").of(new Validator(Criteria.And(Criteria.Accept("New York", "Las Vegas"), Criteria.Required)),
          new Decoration("EnterBig City in USA"));
          

```

#### Email

Check if the value has the format of an email.

Usage:

```Java
        new TextType("email").of(new Validator(Criteria.email()),
          new Decoration("Your Email"));
          

```


Valid input:

```HTML
"max.mustermann@somedomain.com"
```

Validation-Fail:

If the validation fails, the following translation-key will be used:

```HTML
jformchecker.valid_email
```


Example Output for english:
 
```HTML
Please enter a valid email address
```


#### ExactLenght

Check if the value the exact lenght that you require

Usage:

```Java
        new TextType("lotterie").of(new Validator(Criteria.ExactLenght(6)),
          new Decoration("Guess Lotterie"));
          

```


Valid input:

```HTML
"123456"
```

Validation-Fail:

If the validation fails, the following translation-key will be used:

```HTML
jformchecker.exact_lenght
```


Example Output for english:
 
```HTML
The value must be 6 characters long
```
