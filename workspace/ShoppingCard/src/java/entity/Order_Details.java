package java.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Order_Details")
public class Order_Details implements Serializable{

	private static final long serialVersionUID=1L;
	@Id
	@Column(name="Id")
	private String id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ORDER_ID",nullable=false)
	private Order order;
	@ManyToOne(fetch=FetchType.LAZY)
	private Product product;
	private int quantity;
	@Column(name="price")
	private double price;
	@Column(name="amoun",nullable=false)
	private double amount;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
