# Build in Criteria

Here is a list of all criteria that can be used to fill Validators.


| Criteria        | Meaning           | Usage  | Valid Input | Translation-key|
| --------------- |-------------------| -------|-------------|----------------|
| Accept          | Match exact strings | `Criteria.Accept("New York", "Las Vegas")` | `New York` | *.allowed_values |
| And          | Connect several criteria | `Criteria.And(Criteria.Accept("New York", "Las Vegas"), Criteria.Required)` | - | - |
| Email          | Input must be an email | `Criteria.email()` | `max.mustermann@dmn.com` | *.valid_email |
| ExactLenght          | Input must have this exact lenght | `Criteria.ExactLenght(6)` | `123456` | *.exact_lenght |
| Lenght          | Input must have lenght between min and max | `Criteria.length(2,4)` | `123` | *.length |


