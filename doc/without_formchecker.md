## Remember: Standard form handling WITHOUT jFormChecker

Without jFormChecker, html forms must be constructed manually in the template.

Example-Definition of a form in spring-mvc:
```java
public class Adress {

    @Size(min=2, max=30)
    private String name;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

	...
}

```

Example-Definition of a form in html (spring-forms)
```html

        <form:form class="form-horizontal" method="post" 
                modelAttribute="userForm" action="${userActionUrl}">
                <form:hidden path="id" />

		<spring:bind path="name">
		  <div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label" label-for="name">Name</label>
			<div class="col-sm-10">
				<form:input path="name" type="text" class="form-control" 
                                id="name" placeholder="Name" />
				<form:errors path="name" class="control-label" />
			</div>
		  </div>
		</spring:bind>
		
		<spring:bind path="email">
		  <div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label" label-for="email">Email</label>
			<div class="col-sm-10">
				<form:input path="email" class="form-control" 
                                id="email" placeholder="Email" />
				<form:errors path="email" class="control-label" />
			</div>
		  </div>
		</spring:bind>
		
		<spring:bind path="country">
		  <div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label" label-for="country">Country</label>
			<div class="col-sm-5">
				<form:select path="country" class="form-control">
					<form:option value="NONE" label="--- Select ---" />
					<form:options items="${countryList}" />
				</form:select>
				<form:errors path="country" class="control-label" />
			</div>
			<div class="col-sm-5"></div>
		  </div>
		</spring:bind>
		
		...
		
			<div class="form-group">
		  <div class="col-sm-offset-2 col-sm-10">
			     <button type="submit" class="btn-lg btn-primary pull-right">Add
                             </button>
		  </div>
		</div>
	</form:form>

```

As you can see, especially on the template side, you have to enter a lot of stuff for basic highlighting errors...