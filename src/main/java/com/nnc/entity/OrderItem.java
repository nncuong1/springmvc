package com.nnc.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity(name = "OrderItem")
@Table(name = "order_item")
public class OrderItem implements Serializable {

	@EmbeddedId
	private OrderItemId id;

	@Column(name = "qty")
	private int quantity;

	private BigDecimal unitPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("orderId")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	private Product product;

	public OrderItemId getId() {
		return id;
	}

	public void setId(OrderItemId id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
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

}
