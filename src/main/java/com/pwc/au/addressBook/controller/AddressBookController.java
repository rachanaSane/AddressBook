package com.pwc.au.addressBook.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pwc.au.addressBook.model.AddressBook;
import com.pwc.au.addressBook.repository.AddressBookRepository;

@Controller
public class AddressBookController {
	
	@Autowired
	private AddressBookRepository addressBookRepository;
	
	@RequestMapping("/newAddressBook")
	public String createNewAddressBook(Model model) {
		AddressBook book = new AddressBook();
		model.addAttribute("addressBook", book);
		
		return "new_addressBook";
	}
	
	@RequestMapping(value = "/saveAddressBook", method = RequestMethod.POST)
	public String saveProduct(@Valid AddressBook addressBook,BindingResult bindingResult,Model model) {
		
		if (bindingResult.hasErrors()) {
			return "new_addressBook";
		}
		model.addAttribute("errorMessage",null);
		if(addressBookRepository.findByName(addressBook.getName()).isPresent()) {
			model.addAttribute("errorMessage", "Address book with name "+addressBook.getName() +" already exists.");
			return "new_addressBook";
		}
		
		addressBookRepository.save(addressBook);
		
		return "redirect:/";
	}
	
/*	@GetMapping("/addressBooks")
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
*/
}
