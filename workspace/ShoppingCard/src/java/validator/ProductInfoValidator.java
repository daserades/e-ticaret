package java.validator;

import java.dao.ProductDAO;
import java.entity.Product;
import java.model.ProductInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductInfoValidator implements Validator{

	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz==ProductInfo.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductInfo productInfo=(ProductInfo) target;
		
		ValidationUtils.rejectIfEmpty(errors, "code", "NotEmpty.productFrom.code");
		ValidationUtils.rejectIfEmpty(errors, "name", "NotEmpty.productFrom.name");
		ValidationUtils.rejectIfEmpty(errors, "price", "NotEmpty.productFrom.price");
		
		String code=productInfo.getCode();
		if(code!=null && code.length()>0){
			if(code.matches("\\s+")){
				errors.rejectValue("code", "Pattern.productFrom.code");
			}else if(productInfo.isNewProduct()){
				Product product=productDAO.findProduct(code);
				
				if(product!=null){
					errors.rejectValue("code", "Dublicate.productFrom.code");
				}
			}
			
		}
	}

}
