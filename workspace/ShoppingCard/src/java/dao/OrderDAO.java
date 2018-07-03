package java.dao;

import java.model.CartInfo;
import java.model.OrderDetailInfo;
import java.model.OrderInfo;
import java.model.PaginationResult;
import java.util.List;

public interface OrderDAO {

	public void saveOrder(CartInfo cartInfo);
	public PaginationResult<OrderInfo> listOrderInfo (int page,int maxResult,int maxNavigationPage);
	public OrderInfo getOrderInfo(String orderId);
	public List<OrderDetailInfo> listOrderDetailInfos(String orderId);
}
