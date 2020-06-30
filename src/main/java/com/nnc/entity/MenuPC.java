package com.nnc.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

public class MenuPC {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String url;
	private String name;
	@Column(name = "order_index")
	private int orderIndex;

	
	@OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
	private Set<Authority> auths = new HashSet<Authority>();

	@Column(name = "active_flag")
	private int activeFlag;

	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "update_date")
	private Timestamp updateDate;
	
	@ManyToOne
    @JoinColumn(name="parent_id")
	//columnDefinition="integer"
	@NotFound(action = NotFoundAction.IGNORE)
	private Menu parent;
	
	@OneToMany(mappedBy="parent",fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<MenuPC> child;
	
	@Transient
	private String idMenu;
	
	
}
