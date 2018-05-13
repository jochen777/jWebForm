# Build in Criteria

Here is a list of all criteria that can be used to fill Validators.


| Criteria        | Meaning           | Usage  | Valid Input | Translation-key|
| --------------- |-------------------| -------|-------------|----------------|
| Accept          | Match exact strings | `Criteria.accept("New York", "Las Vegas")` | `New York` | *.allowed_values |
| And (optional)        | Connect several criteria | `Criteria.And( Criteria.accept("New York", "Las Vegas"), Criteria.required)` | - | - |
| Email          | Input must be an email | `Criteria.email()` | `pete@dmn.com` | *.valid_email |
| ExactLenght          | Input must have this exact lenght | `Criteria.exactLenght(6)` | `123456` | *.exact_lenght |
| Lenght          | Input must have lenght between min and max | `Criteria.length(2,4)` | `123` | *.length |
| Max          | Input must be a number and smaller than max | `Criteria.max(50)` | `49` | *.max |
| Min          | Input must be a number and bigger than min | `Criteria.min(100)` | `101` | *.min |
| MaxLenght          | Input must be shorter than max | `Criteria.maxLength(3)` | `ab` | *.max_len |
| MinLenght          | Input must be longer than min | `Criteria.minLength(5)` | `abcdcd3` | *.min_len |
| Number          | Input must be a number (integer) | `Criteria.number()` | `234234` | *.not_a_number |
| Or          | Connect several criteria with or | `Criteria.or( Criteria.min(2), Criteria.accept("b")` | - | - |
| PostcodeCA          | Input must be a canadian postalcode | `Criteria.postcodeCA()` | `A0A 0A0` | *.postalcode |
| Range          | Input must be number and within the given range | `Criteria.range(10,50)` | `30` | *.range |
| Regex          | Input must match a regxep | `Criteria.regex("\\d+")` | `30` | *.regexp |
| Required          | Input is required | `Criteria.required()` | `asfwe` | *.required |
| StartsWith          | Input must start with | `Criteria.startsWith("bla")` | `blabbla` | *.starts_with |
| StrongPassword          | Input must be a strong password | `Criteria.strongPassword(5)` | `xT6!_sdfkwle.ZT` | *.strong_password|
| ZipCode          | Input must be a zipcode | `Criteria.zipcode()` | `12345-1234` | *.zip |