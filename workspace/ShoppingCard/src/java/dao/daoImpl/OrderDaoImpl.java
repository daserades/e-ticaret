package java.dao.daoImpl;

import java.dao.OrderDAO;
import java.dao.ProductDAO;
import java.entity.Order;
import java.entity.Order_Details;
import java.entity.Product;
import java.model.CartInfo;
import java.model.CartLineInfo;
import java.model.CustomerInfo;
import java.model.OrderDetailInfo;
import java.model.OrderInfo;
import java.model.PaginationResult;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Transactional
public class OrderDaoImpl implements OrderDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProductDAO productDAO;
	
	private int getMaxOrderNum(){
		String sql="select max(o.orderNum) from" + Order.class.getName() + "o";
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery(sql);
		Integer value=(Integer)query.uniqueResult();
		
		if(value==null)
			return 0;
		return value;
	}
	
	@Override
	public void saveOrder(CartInfo cartInfo) {
		Session session=sessionFactory.getCurrentSession();
		
		int orderNum=this.getMaxOrderNum()+1;
		Order order=new Order();
		
		order.setId(UUID.randomUUID().toString());
		order.setOrderNum(orderNum);
		order.setOrderDate(new Date());
		order.setAmount(cartInfo.getAmountTotal());
		
		CustomerInfo customerInfo=cartInfo.getCustomerInfo();
		order.setCustomerName(customerInfo.getName());
		order.setCustomerEmail(customerInfo.getEmail());
		order.setCustomerPhone(customerInfo.getPhone());
		order.setCustomerAddress(customerInfo.getAddress());
		
		session.persist(order);
		
		List<CartLineInfo> lines=cartInfo.getCartLines();
		
		for(CartLineInfo line:lines){
			Order_Details detail=new Order_Details();
			detail.setId(UUID.randomUUID().toString());
			detail.setOrder(order);
			detail.setAmount(line.getAmount());
			detail.setPrice(line.getProductInfo().getPrice());
			detail.setQuantity(line.getQuantity());
			
			String code=line.getProductInfo().getCode();
			Product product=this.productDAO.findProduct(code);
			detail.setProduct(product);
			
			session.persist(detail);
		}
		//set numara for report
		cartInfo.setOrderNum(orderNum);
		
	}
	
	

	@Override
	public PaginationResult<OrderInfo> listOrderInfo(int page, int maxResult, int maxNavigationPage) {
		String sql="select new" + OrderInfo.class.getName() +
				"(ord.id,ord.orderDate,ord.orderNum,ord.amount," +
				"ord.customerName,ord.customerAddress,ord.customerEmail,ord.customerPhone)" + "from"+
				Order.class.getName()+"ord"+
				"order by ord.orderNum desc";
		Session session=this.sessionFactory.getCurrentSession();
		Query query=session.createQuery(sql);
		return new PaginationResult<OrderInfo>(query,page,maxResult,maxNavigationPage);
	}

	public Order findOrder(String orderId){
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(Order.class);
		criteria.add(Restrictions.eq("id",orderId));
		return (Order)criteria.uniqueResult();
	}
	
	@Override
	public OrderInfo getOrderInfo(String orderId) {
		//
		
		Order order=this.findOrder(orderId);
		if(order==null)
			return null;
		return new OrderInfo(order.getId(),order.getOrderDate(),
				order.getOrderNum(),order.getAmount(),order.getCustomerName(),
				order.getCustomerAddress(),order.getCustomerEmail(),order.getCustomerPhone());
	}

	@Override
	public List<OrderDetailInfo> listOrderDetailInfos(String orderId) {
		 String sql = "Select new " + OrderDetailInfo.class.getName() //
	                + "(d.id, d.product.code, d.product.name , d.quanity,d.price,d.amount) "//
	                + " from " + Order_Details.class.getName() + " d "//
	                + " where d.order.id = :orderId ";
	 
	        Session session = this.sessionFactory.getCurrentSession();
	 
	        Query query = session.createQuery(sql);
	        query.setParameter("orderId", orderId);
	 
	        return query.list();
	    }

}
