package jwebform.spring;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.validation.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.metadata.BeanDescriptor;
import jakarta.validation.metadata.ConstraintDescriptor;
import jakarta.validation.metadata.PropertyDescriptor;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.Test;
import jwebform.integration.FormRenderer;
import jwebform.integration.FormResultAndBean;
import jwebform.integration.FormRunner;
import jwebform.integration.FormRunnerConfig;
import jwebform.integration.bean2form.Bean2Form;
import jwebform.integration.bean2form.DefaultBean2Form;
import jwebform.integration.beanvalidation.BeanValidationRuleDeliverer;
import jwebform.integration.beanvalidation.BeanValidationValidator;
import jwebform.integration.beanvalidation.ExternalValidation;
import jwebform.integration.beanvalidation.ExternalValidationDescription;
import jwebform.resultprocessor.ModelResultProcessor;
import jwebform.themes.sourcecode.BootstrapTheme;
import jwebform.themes.sourcecode.ThemeJavaRenderer;
import jwebform.themes.sourcecode.mapper.StandardMapper;

public class FormRunnerTest {

    // test if bean-validation works in JWebForm.
    @Test
    public void testRun_beanValidation() {

        Configuration<?> i = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator());
        Bean2Form bean2FromContract;
        try (ValidatorFactory vf = i.buildValidatorFactory()) {
            bean2FromContract = getBean2Form(vf.getValidator());
        }

        FormRenderer formRenderer =
                new ThemeJavaRenderer(new StandardMapper(new BootstrapTheme(msg -> msg)));

        FormRunnerConfig formRunnerConfig =
                new FormRunnerConfig(formRenderer, bean2FromContract, new ModelResultProcessor(), "form");

        FormRunner jwebform = new FormRunner(ExampleRequests.exampleSubmittedRequest("name", "test"),
                ExampleRequests.emptySessionGet(), ExampleRequests.emptySessionPut(),
                ExampleRequests.stupidModel(), formRunnerConfig);

        FormResultAndBean fr = jwebform.runWithBean(new MyForm10());
        assertTrue(
                "The form should be not okay, beause the validation should fail ('test' is smaller than 10 chars)",
                !fr.isValid());

        FormResultAndBean fr2 = jwebform.runWithBean(new MyForm2());
        assertTrue("The form should be okay, 'test' is bigger than 2 chars",
                fr2.getFormResult().isValid());

    }


    public class MyForm10 {
        @Size(min = 10, max = 200, message = "The name must be between 10 and 200 chars")
        public String name = "";
    }

    public class MyForm2 {
        @Size(min = 2, max = 200, message = "The name must be between 10 and 200 chars")
        public String name = "";
    }

    private Bean2Form getBean2Form(Validator validator) {
        return new DefaultBean2Form(getBeanValidator(validator), getRuleDeliverer(validator));
    }

    private BeanValidationRuleDeliverer getRuleDeliverer(Validator validator) {
        return (bean, name) -> {
            Set<ExternalValidationDescription> criteraSet = new HashSet<>();
            BeanDescriptor i = validator.getConstraintsForClass(bean.getClass());
            PropertyDescriptor b = i.getConstraintsForProperty(name);
            if (b != null) {
                Set<ConstraintDescriptor<?>> z = b.getConstraintDescriptors();
                z.forEach(constraintDesc -> {
                    criteraSet.add(new ExternalValidationDescription(
                            constraintDesc.getAnnotation().annotationType().getSimpleName(),
                            constraintDesc.getAttributes()));

                });
            }
            return criteraSet;
        };
    }

    private BeanValidationValidator getBeanValidator(Validator validator) {

        return (b) -> {
            Set<ConstraintViolation<Object>> vals = validator.validate(b);
            List<ExternalValidation> externalVals = new ArrayList<>();
            vals.forEach(constr -> {
                ExternalValidation e =
                        new ExternalValidation(constr.getPropertyPath().toString(), constr.getMessage());
                externalVals.add(e);
            });

            return externalVals;
        };
    }
}
