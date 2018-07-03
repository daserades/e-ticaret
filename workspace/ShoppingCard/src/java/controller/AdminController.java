package java.controller;

import java.dao.OrderDAO;
import java.dao.ProductDAO;
import java.entity.Product;
import java.model.OrderDetailInfo;
import java.model.OrderInfo;
import java.model.PaginationResult;
import java.model.ProductInfo;
import java.util.List;
import java.validator.ProductInfoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ProductInfoValidator productInfoValidator;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder){
		Object target=dataBinder.getTarget();
		if(target==null)
			return ;
		System.out.println("Target:"+target);
		
		if(target.getClass()==Product.class){
			dataBinder.setValidator(productInfoValidator);
			
			//resim yukleme
			dataBinder.registerCustomEditor(Byte[].class, new ByteArrayMultipartFileEditor());
			
		}
	}
	//show login page
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(Model model){
		return "login";
	}
	@RequestMapping(value="/accountInfo",method=RequestMethod.GET)
	public String accountInfo(Model model){
		UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());
        
        model.addAttribute("userDetails",userDetails);
        return "accountInfo";
	}
	
	@RequestMapping(value="/orderList",method=RequestMethod.GET)
	public String orderList(Model model,@RequestParam(value="page",defaultValue="1")String pageStr){
		int page=1;
		try{
			page=Integer.parseInt(pageStr);
		}catch(Exception e){
			
		}
		final int MAX_RESULT=5;
		final int MAX_NAVIGATION_PAGE=10;
		
		PaginationResult<OrderInfo> paginationResult=orderDAO.listOrderInfo(page,MAX_RESULT,MAX_NAVIGATION_PAGE);
		
		model.addAttribute("paginationResult",paginationResult);
		return "orderList";
	}
	//show product
	@RequestMapping(value="/product",method=RequestMethod.GET)
	public String product(Model model,@RequestParam(value="code",defaultValue="1")String code){
		ProductInfo productInfo=null;
		if(code!=null && code.length()>0)
			productInfo=productDAO.findProductInfo(code);
		if(productInfo==null)
			productInfo=new ProductInfo();
		productInfo.setNewProduct(true);
		
		model.addAttribute("productform",productInfo);
		return "productInfo";
	}
	
	//save product
	@RequestMapping(value="/product",method=RequestMethod.POST)
	@Transactional(propagation=Propagation.NEVER)
	public String saveProduct(Model model,@ModelAttribute("productForm") @Validated ProductInfo productInfo,BindingResult result,final RedirectAttributes redirectAttributes){
		if(result.hasErrors())
			return "product";
		
		try{
			productDAO.save(productInfo);
		}catch(Exception e){
			
			//propagation.never
			String message=e.getMessage();
			model.addAttribute("message",message);
			return "product";
		}
		return "redirect:/productList";
	}
	
	public String orderView(Model model,@RequestParam("orderId") String orderId){
		OrderInfo orderInfo=null;
		if(orderId==null)
			orderInfo=this.orderDAO.getOrderInfo(orderId);
		if(orderInfo==null)
			return "redirect:/orderList";
		
		List<OrderDetailInfo> details=this.orderDAO.listOrderDetailInfos(orderId);
		orderInfo.setDetails(details);
		model.addAttribute("orderInfo",orderInfo);
		return "order";
	}
}
