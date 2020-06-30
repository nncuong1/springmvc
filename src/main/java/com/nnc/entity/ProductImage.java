package com.nnc.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Entity(name="product_image")
//public class ProductImage {
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Integer id;
//	
//	@Column(name = "img_url")
//	private String imageUrl;
//	private int position;
//	
////	@ManyToOne(fetch = FetchType.LAZY)
////	@JoinColumn(name = "product_id")
////	private Product product;
//	
//	@Column(name="active_flag")
//	private int activeFlag;	
//
//	@Column(name="create_date")
//	private Timestamp createDate;
//
//	@Column(name="update_date")
//	private Timestamp updateDate;
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getImageUrl() {
//		return imageUrl;
//	}
//
//	public void setImageUrl(String imageUrl) {
//		this.imageUrl = imageUrl;
//	}
//
//	public int getPosition() {
//		return position;
//	}
//
//	public void setPosition(int position) {
//		this.position = position;
//	}

//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}
//}

