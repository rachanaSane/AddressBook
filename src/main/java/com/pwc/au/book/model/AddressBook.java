package com.pwc.au.book.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="addressBook", uniqueConstraints=@UniqueConstraint(columnNames="name"))
public class AddressBook {
	
	 @Id
	 @GeneratedValue
	 private Long id;
	 
	 @Column(name="name")	
	 @NotNull
	 @NotBlank
	 private String name;

	


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
	 
	 

}
