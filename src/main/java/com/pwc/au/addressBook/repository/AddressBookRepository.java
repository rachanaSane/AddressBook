package com.pwc.au.addressBook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pwc.au.addressBook.model.AddressBook;
import com.pwc.au.addressBook.model.Contact;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook,Long> {

	Optional<AddressBook> findByName(String name);
}
