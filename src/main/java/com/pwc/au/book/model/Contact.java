package com.pwc.au.book.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@Entity
@Table(name="contact")
public class Contact {
	
	 @Id
	 @GeneratedValue
	 private Long id;
	 
	 @Column(name="name")
	 @NotNull
	 @NotBlank
	 private String name;
	 
	 @Column(name="phoneNumber")
	 @NotNull
	 @NotBlank
	 @Pattern(regexp = "(\\+61|0)[0-9]{9}")
	 private String phoneNumber;
	 
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 @JoinColumn(name = "addressBook_id", nullable = false)	
	 private AddressBook addressBook;	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public AddressBook getAddressBook() {
		return addressBook;
	}

	public void setAddressBook(AddressBook addressBook) {
		this.addressBook = addressBook;
	}
	 
	 
	 
	 

}
