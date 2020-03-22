package com.pwc.au.book.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pwc.au.book.model.AddressBook;
import com.pwc.au.book.repository.AddressBookRepository;
import com.pwc.au.book.repository.ContactRepository;


@Controller
public class AddressBookAppController {
	
	private static final String COMPARE_BOOKS="compare_books";

	@Autowired
	private AddressBookRepository addressBookRepository;
	
	@Autowired
    private ContactRepository contactRepository;

	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<AddressBook> listAddressBooks = addressBookRepository.findAll();
		model.addAttribute("listAddressBooks", listAddressBooks);
		
		return "index";
	}
	
		
	
	//@RequestMapping("/viewCompare")
	@GetMapping("/viewCompare")
	public ModelAndView showCompareAddressBook() {
		ModelAndView compareBooks = new ModelAndView(COMPARE_BOOKS);			
		compareBooks.addObject("addrBookName1", "");		
		compareBooks.addObject("addrBookName2", "");		
		compareBooks.addObject("contactNames",new ArrayList<>());
		return compareBooks;
	}
	
	
	@RequestMapping(value = "/compare", method = RequestMethod.POST)
	public ModelAndView compareAddressBooks(@RequestParam(value = "addrBookName1", required = false) String addrBookName1,@RequestParam(value = "addrBookName2", required = false) String addrBookName2) {
		List<String> addressBookNames = new ArrayList<String>();
		addressBookNames.add(addrBookName1);
		addressBookNames.add(addrBookName2);
		List<String> contactNames=(List<String>) contactRepository.findUnCommonContacts(addressBookNames);
		
		ModelAndView mav = new ModelAndView(COMPARE_BOOKS);	
	
		mav.addObject("addrBookName1", addrBookName1);		
		mav.addObject("addrBookName2", addrBookName2);
		mav.addObject("contactNames",contactNames);
		
		return mav;
	}
	
	
}
