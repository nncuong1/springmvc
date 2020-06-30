package com.nnc.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name="phone")
@Table(name="phone")
public class Phone implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="context")
	private String context;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="contact_id",nullable = false)
	private Contact contact;
	
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
	public Phone(String context) {
		this.context = context;
	}
	public Phone( ) {

	}
}
