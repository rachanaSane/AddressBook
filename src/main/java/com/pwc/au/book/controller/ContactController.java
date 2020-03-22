package com.pwc.au.book.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import com.pwc.au.book.model.Contact;
import com.pwc.au.book.repository.AddressBookRepository;
import com.pwc.au.book.repository.ContactRepository;


@Controller
public class ContactController {
	
	private static final String ADD_CONTACT ="add_contact";
	
	@Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressBookRepository addressBookRepository;
    
	@RequestMapping("/addressBook/{id}/contact")
	public ModelAndView showAddContactPage(@PathVariable(name = "id") Long addressBookId) {
		ModelAndView mav = new ModelAndView(ADD_CONTACT);			
		mav.addObject("contact", new Contact());		
		mav.addObject("addressBookId", addressBookId);		
		return mav;
	}
	
	
	@RequestMapping("/addContact/{id}")
	public ModelAndView addContact(@PathVariable(name = "id") Long addressBookId, @Valid @ModelAttribute("contact") Contact contact,BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView(ADD_CONTACT);	
			mav.addObject("addressBookId", addressBookId);		
			return mav;			
		}
		
		
		Contact updatedContact =addressBookRepository.findById(addressBookId).map(addressBook -> {
			contact.setAddressBook(addressBook);          
			return contact;
        }).orElseThrow(() -> new RuntimeException("addressBookId " + addressBookId + " not found"));
	
	   contactRepository.save(updatedContact);	
	   
       return new ModelAndView("redirect:/");	
	
	}
	
	
	@RequestMapping("/addressBook/{id}/contacts")
	public ModelAndView showViewContactPage(@PathVariable(name = "id") Long addressBookId) {
		ModelAndView mav = new ModelAndView("view_contacts");
		List<Contact> contacts=(List<Contact>) contactRepository.findByAddressBookId(addressBookId);
		mav.addObject("contacts", contacts);
		
		return mav;
	}

 

}
