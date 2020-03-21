package com.pwc.au.addressBook.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pwc.au.addressBook.model.AddressBook;
import com.pwc.au.addressBook.repository.AddressBookRepository;

@RestController
public class AddressBookController {
	
	@Autowired
	private AddressBookRepository addressBookRepository;
	
	@GetMapping("/addressBooks")
    public List<AddressBook> getAllAddressBooks() {
        return addressBookRepository.findAll();
    }
	
	@PostMapping("/addressBook")
    public AddressBook createPost(@Valid @RequestBody AddressBook addressBook) {
        return addressBookRepository.save(addressBook);
    }
	
	@PutMapping("/addressBook/{addressBookId}")
    public AddressBook updatePost(@PathVariable Long addressBookId, @Valid @RequestBody AddressBook addressBookUpdated) {
        return addressBookRepository.findById(addressBookId).map(addressBook -> {
        	addressBook.setName(addressBookUpdated.getName());
            return addressBookRepository.save(addressBook);
        }).orElseThrow(() -> new RuntimeException("addressBookId " + addressBookId + " not found"));
    }


    @DeleteMapping("/addressBook/{addressBookId}")
    public ResponseEntity<?> deletePost(@PathVariable Long addressBookId) {
        return addressBookRepository.findById(addressBookId).map(addressBook -> {
        	addressBookRepository.delete(addressBook);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RuntimeException("addressBookId " + addressBookId + " not found"));
    }

}
