package java.validator;

import java.model.CustomerInfo;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomerInfoValidator implements Validator{

	@Autowired
	private EmailValidator emailValidator=EmailValidator.getInstance();
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz==CustomerInfo.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		CustomerInfo customerInfo=(CustomerInfo) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.customerForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.customerForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.customerForm.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.customerForm.phone");
        
        if (!emailValidator.isValid(customerInfo.getEmail())) {
            errors.rejectValue("email", "Pattern.customerForm.email");
        }
	}

}
