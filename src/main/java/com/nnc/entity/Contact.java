package com.nnc.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.multipart.MultipartFile;

@Entity(name="contact")
public class Contact implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private int age;
	
	@Column(name="img_url")
	private String imgUrl;
	
	@Transient
	private MultipartFile multipartFile;
	
	@OneToMany( cascade=CascadeType.ALL, mappedBy="contact",fetch = FetchType.LAZY,orphanRemoval=false)
//	@OneToMany( cascade=CascadeType.ALL)
	private Set<Phone> phones = new HashSet<Phone>();
	
	public Set<Phone> getPhones() {
		return phones;
	}
	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setContact(this);
    }
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	
	
//    public void removeComment(PostComment comment) {
//        comments.remove(comment);
//        comment.setPost(null);
//    }
//	public void addPhone(Phone phone) {
//        phones.add(phone);
//        phone.setContact(this);
//    }
//    public void removeComment(PostComment comment) {
//        comments.remove(comment);
//        comment.setPost(null);
//    }
}
