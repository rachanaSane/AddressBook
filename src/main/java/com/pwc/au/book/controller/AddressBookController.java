package com.pwc.au.book.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pwc.au.book.model.AddressBook;
import com.pwc.au.book.repository.AddressBookRepository;

@Controller
public class AddressBookController {
	
	private static final String NEW_ADDRESS_BOOK = "new_addressBook";
	
	@Autowired
	private AddressBookRepository addressBookRepository;

	@RequestMapping("/newAddressBook")
	public String showAddAddressBook(Model model) {
		
		model.addAttribute("addressBook", new AddressBook());
		return NEW_ADDRESS_BOOK;
	}

	@RequestMapping(value = "/saveAddressBook", method = RequestMethod.POST)
	public String saveAddressBook(@Valid AddressBook addressBook, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return NEW_ADDRESS_BOOK;
		}
		model.addAttribute("errorMessage", null);
		if (addressBookRepository.findByName(addressBook.getName()).isPresent()) {
			model.addAttribute("errorMessage", "Address book with name " + addressBook.getName() + " already exists.");
			return NEW_ADDRESS_BOOK;
		}

		addressBookRepository.save(addressBook);

		return "redirect:/";
	}

}
