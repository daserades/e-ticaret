package java.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Orders")
public class Order implements Serializable{

	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="id",nullable=false)
	private String id;
	@Column(name="orderDate",nullable=false)
	private Date orderDate;
	@Column(name="orderNum")
	private int orderNum;
	@Column(name="amount")
	private double amount;
	@Column(name="CustomerName",nullable=false)
	private String customerName;
	@Column(name="CustomerEmail",nullable=false)
	private String customerEmail;
	@Column(name="CustomerAddres")
	private String customerAddress;
	@Column(name="CustomerPhone")
	private String customerPhone;
	
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Order(String id, Date orderDate, int orderNum, double amount, String customerName, String customerEmail,
			String customerAddress, String customerPhone) {
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.orderNum = orderNum;
		this.amount = amount;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerAddress = customerAddress;
		this.customerPhone = customerPhone;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	
	
	
}
