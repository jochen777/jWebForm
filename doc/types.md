# Build in Types

Here is a list of all types that can be used within a form.


| Type        | Meaning           | Usage  |
| --------------- |-------------------| -------|
| CheckboxType          | Checkbox Input | `Type.checkbox("chk", true)` |
| HiddenType        | Hidden Field | `Type.hidden("hdn", "preset")` |
| HtmlType          | Raw HTML | `Type.html("<div>html content</div>")` |
| LabelType          | Just a label | `Type.label("My label")` |
| NumberType          | Textinput that only allows numbers | `Type.number("nbr", 44)` |
| PasswordType          | Password Input | `Type.password("pwd")` |
| RadioType          | Radio Input | `Type.radio("radio", "1", new String[] {"1", "2"}, new String[] {"yes", "no"}` |
| SelectDateType          | Date Input with Select Fields (Group) | `Type.selectDate("dateInput", LocalDate.of(2017, 7, 4))` |
| SelectType          | SelectInput | `Type.select("gender", "", new String[] {"m", "f"}, new String[] {"Male", "Female"})` |
| SimpleGroup          | Group - holds fields|  |
| SimpleType          | Very Simple Type (just for demo) | `Type.simple()` |
| SubmitType          | Submit-Button | `Type.submit()` |
| TextAreaType          | TextArea Type | `Type.textArea("area", "Area-Prebuild")` |
| TextDateType          | Date Input with Text Fields (Group) | `Type.textDate("dateInput", LocalDate.of(2017, 7, 4))` |
| TextType          | Text Input | `Type.text("txt", "Text-Prebuild")` |
| UploadType          | Upload Input | `Type.upoad("upl")` |
| XSRFProtection          | XSRF Protection | `Type.xsrf()`|
