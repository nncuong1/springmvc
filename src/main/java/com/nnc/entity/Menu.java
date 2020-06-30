package com.nnc.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity(name = "menu")
public class Menu implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String url;
	private String name;
	@Column(name = "order_index")
	private int orderIndex;

	@Column(name = "parent_id")
	private int parentId;
	
	@OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<Authority> auths = new HashSet<Authority>();

	@Column(name = "active_flag")
	private int activeFlag;

	@Column(name = "create_date")
	private Timestamp createDate;

	@Column(name = "update_date")
	private Timestamp updateDate;
	
//	@ManyToOne(optional = true)
//    @JoinColumn(name="parent_id",columnDefinition="integer")
//	@NotFound(action = NotFoundAction.IGNORE)
//    private Menu parent;
	
	@Transient
	//@OneToMany(mappedBy="parent",fetch = FetchType.LAZY)
	//@NotFound(action = NotFoundAction.IGNORE)
	private List<Menu> child;
	
	@Transient
	private Map<Integer, Integer> mapAuth;
	
	@Transient
	private String idMenu;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Set<Authority> getAuths() {
		return auths;
	}

	public void setAuths(Set<Authority> auths) {
		this.auths = auths;
	}

	public List<Menu> getChild() {
		return child;
	}

	public void setChild(List<Menu> child) {
		this.child = child;
	}

	public Menu() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(String idMenu) {
		this.idMenu = idMenu;
	}

	public int getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Map<Integer, Integer> getMapAuth() {
		return mapAuth;
	}

	public void setMapAuth(Map<Integer, Integer> mapAuth) {
		this.mapAuth = mapAuth;
	}

}