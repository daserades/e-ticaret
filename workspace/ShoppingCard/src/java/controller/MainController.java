package java.controller;

import java.dao.OrderDAO;
import java.dao.ProductDAO;
import java.entity.Product;
import java.io.IOException;
import java.model.CartInfo;
import java.model.CustomerInfo;
import java.model.PaginationResult;
import java.model.ProductInfo;
import java.validator.CustomerInfoValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.utils.Utils;

@Controller
@Transactional
@EnableWebMvc
public class MainController {

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CustomerInfoValidator customerInfoValidator;
	
	@InitBinder
	private void myInitBinder(WebDataBinder dataBinder){
		Object target=dataBinder.getTarget();
		
		if(target==null){
			return;
		}
		System.out.println("Target :"+target);
		
		// For Cart Form.
        // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
		if(target.getClass()==CartInfo.class){
			//
			//for customer form
		}else if(target.getClass()==CustomerInfo.class){
			dataBinder.setValidator(customerInfoValidator);
		}
		
	}
	@RequestMapping("/403")
	public String accessDenied(){
		return "/403";
	}
	
	@RequestMapping("/")
	public String home(){
		return "index";
	}
	
	//product list page
	@RequestMapping("/productList")
	public String listProductHandler(Model model,@RequestParam(value="name",defaultValue="") String likeName,
			@RequestParam(value="page",defaultValue="1") int page){
		final int maxResult=5;
		final int maxNavigationPage=10;
	PaginationResult<ProductInfo> result=productDAO.queryProducts(page, maxResult, maxNavigationPage, likeName);
	model.addAttribute("paginationProducts",result);
	return "productList";
	}
	
	@RequestMapping("/buyProduct")
	public String listProductHandler(HttpServletRequest request,Model model,@RequestParam(value="code",defaultValue="") String code){
		Product product=null;
		if(code!=null&&code.length()>0)
			product=productDAO.findProduct(code);
		if(product!=null){
			CartInfo cartInfo=Utils.getCartInSession(request);
			ProductInfo productInfo=new ProductInfo(product);
			cartInfo.addProduct(productInfo, 1);
		}
		return "redirect:/shoppingCart";
	}
	@RequestMapping("/shoppingCartRemoveProduct")
	public String removeProductHandler(HttpServletRequest request,Model model,
			@RequestParam(value="code",defaultValue="")String code){
		Product product=null;
		if(code!=null && code.length()>0)
			product=productDAO.findProduct(code);
		if(product!=null){
			CartInfo cartInfo=Utils.getCartInSession(request);
			ProductInfo productInfo=new ProductInfo(product);
			cartInfo.removeProduct(productInfo);
			
		}
		return "redirect:/shoppingCart";
	}
	
	@RequestMapping(value={"/shoppingCart"},method=RequestMethod.POST)
	public String shoppingCartUpdateQTY(HttpServletRequest request,
			Model model,@ModelAttribute("cartForm") CartInfo cartForm){
		CartInfo cartInfo=Utils.getCartInSession(request);
		cartInfo.updateQuantity(cartForm);
		
		return "redirect:/shoppingCart";
	}
	
	//show cart
	@RequestMapping(value="/shoppingCart",method=RequestMethod.GET)
	public String shoppingCartHandler(HttpServletRequest request,Model model){
		
		CartInfo myCart=Utils.getCartInSession(request);
		model.addAttribute("cartForm",myCart);
		
		return "shoppingCart";
	}
	@RequestMapping(value="/shoppingCartCustomer",method=RequestMethod.GET)
	public String shoppingCartCustomerForm(HttpServletRequest request,Model model){
		CartInfo cartInfo=Utils.getCartInSession(request);
		if(cartInfo.isEmpty())
			return "redirect:/shoppingCart";
		CustomerInfo customerInfo=cartInfo.getCustomerInfo();
		if(customerInfo==null)
			customerInfo=new CustomerInfo();
		model.addAttribute("customerForm",customerInfo);
		return "shoppingCartCustomer";
	}
	@RequestMapping(value="/shoppingCartCustomer",method=RequestMethod.POST)
	public String shoppingCartCustomerSave(HttpServletRequest request,
			Model model,@ModelAttribute("customerForm") @Validated CustomerInfo customerForm,
			BindingResult result,final RedirectAttributes redirectAttributes){
		
		//eger hata varsa
		if(result.hasErrors()){
			customerForm.setValid(false);
			return "shoppingCartCustomer";
		}
		customerForm.setValid(true);
		CartInfo cartInfo=Utils.getCartInSession(request);
		cartInfo.setCustomerInfo(customerForm);
		
		return "redirect:/shoppingCartConfirmation";
	}
	
	//post cart save
	@RequestMapping(value="/shoppingCartConfirmation",method=RequestMethod.POST)
	@Transactional(value=TxType.NEVER)
	public String shoppingCartConfirmationSave(HttpServletRequest request,Model model){
		
		CartInfo cartInfo=Utils.getCartInSession(request);
		
		if(cartInfo.isEmpty())
			return "redirect:/shoppingCart";
		else if(!cartInfo.isValidCustomer()){
		return "redirect:/shoppingCartCustomer";
		}
		try{
			orderDAO.saveOrder(cartInfo);
			
		}catch(Exception e){
			//need propagation never
			return "shoppingCartConfirmation";
		}
		//remove cart in session
		Utils.removeCartInSession(request);
		
		//store last ordered cart to session
		Utils.storeLastOrderedCartInSession(request, cartInfo);
		
		//return successful page
		return "redirect:/shoppingCartFinalize";
		
	}
	
	@RequestMapping(value="/shoppingCartFinalize",method=RequestMethod.POST)
	public String shoppingCartFinalize(HttpServletRequest request,Model model){
		CartInfo lastOrderedCart=Utils.getLastOrderedCartInSession(request);
		
		if(lastOrderedCart==null)
			return "redirect:/shoppingCart";
		else
			return "/shoppingCartFinalize";
	}
	@RequestMapping(value="/productImage",method=RequestMethod.GET)
	public void productImage(HttpServletRequest request,HttpServletResponse response,Model model,@RequestParam("code") String code)throws IOException{
		
		Product product=null;
		if(code!=null)
		product=this.productDAO.findProduct(code);
		
		if(product!=null && product.getImage()!=null){
			response.setContentType("image/jpeg,image/jpg,image/png,image/gif");
			response.getOutputStream().write(product.getImage());
		}
		response.getOutputStream().close();
	}
}
