package java.utils;

import java.model.CartInfo;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	//Products in cart, stored in session
	public static CartInfo getCartInSession(HttpServletRequest request){
		//Get Cart from session
		CartInfo cartInfo=(CartInfo) request.getSession().getAttribute("myCart");
		//if null create it
		if(cartInfo==null){
			cartInfo=new CartInfo();
			//and store session
			request.getSession().setAttribute("myCart", cartInfo);
		}
		return cartInfo;
	}
	
	public static void removeCartInSession(HttpServletRequest request){
		request.getSession().removeAttribute("myCart");
	}
	
	public static void storeLastOrderedCartInSession(HttpServletRequest request,CartInfo cartInfo){
		request.getSession().setAttribute("lastOrderedCart", cartInfo);
	}
	
	public static CartInfo getLastOrderedCartInSession(HttpServletRequest request){
		return (CartInfo)request.getSession().getAttribute("lastOrderedCart");
	}
	
	
}
