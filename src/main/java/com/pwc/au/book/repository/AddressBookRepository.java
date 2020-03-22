package com.pwc.au.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pwc.au.book.model.AddressBook;




@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook,Long> {

	Optional<AddressBook> findByName(String name);
}
