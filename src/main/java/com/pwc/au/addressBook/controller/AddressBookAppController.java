package com.pwc.au.addressBook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pwc.au.addressBook.model.AddressBook;
import com.pwc.au.addressBook.model.Contact;
import com.pwc.au.addressBook.repository.AddressBookRepository;
import com.pwc.au.addressBook.repository.ContactRepository;







@Controller
public class AddressBookAppController {

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
	
	@RequestMapping("/newAddressBook")
	public String createNewAddressBook(Model model) {
		AddressBook book = new AddressBook();
		model.addAttribute("addressBook", book);
		
		return "new_addressBook";
	}
	
	@RequestMapping(value = "/saveAddressBook", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("addressBook") AddressBook addressBook) {
		addressBookRepository.save(addressBook);
		
		return "redirect:/";
	}
	
	@RequestMapping("/addressBook/{id}/contact")
	public ModelAndView showAddContactPage(@PathVariable(name = "id") Long addressBookId) {
		ModelAndView mav = new ModelAndView("add_contact");	
		Contact contact = new Contact();
		mav.addObject("contact", contact);		
		mav.addObject("addressBookId", addressBookId);		
		return mav;
	}
	
	
	@RequestMapping("/addContact/{id}")
	public String addContact(@PathVariable(name = "id") Long addressBookId, @ModelAttribute("contact") Contact updatedContact) {
		
		Contact contact =addressBookRepository.findById(addressBookId).map(addressBook -> {
			updatedContact.setAddressBook(addressBook);
            return contactRepository.save(updatedContact);
        }).orElseThrow(() -> new RuntimeException("addressBookId " + addressBookId + " not found"));
		
		
		return "redirect:/";
	}
	
	
	@RequestMapping("/addressBook/{id}/contacts")
	public ModelAndView showViewContactPage(@PathVariable(name = "id") Long addressBookId) {
		ModelAndView mav = new ModelAndView("view_contacts");
		List<Contact> contacts=(List<Contact>) contactRepository.findByAddressBookId(addressBookId);
		mav.addObject("contacts", contacts);
		
		return mav;
	}
	
	
	@RequestMapping("/viewCompare")
	public ModelAndView showCompareAddressBook() {
		ModelAndView mav = new ModelAndView("compare_books");	
		String addrBookName1 = "";
		String addrBookName2 = "";
		mav.addObject("addrBookName1", addrBookName1);		
		mav.addObject("addrBookName2", addrBookName2);		
		mav.addObject("contactNames",new ArrayList<>());
		return mav;
	}
	
	
	@RequestMapping(value = "/compare", method = RequestMethod.POST)
	public ModelAndView compareAddressBooks(@RequestParam(value = "addrBookName1", required = false) String addrBookName1,@RequestParam(value = "addrBookName2", required = false) String addrBookName2) {
		List<String> addressBookNames = new ArrayList();
		addressBookNames.add(addrBookName1);
		addressBookNames.add(addrBookName2);
		List<String> contactNames=(List<String>) contactRepository.findUnCommonContacts(addressBookNames);
		
		ModelAndView mav = new ModelAndView("compare_books");	
	
		mav.addObject("addrBookName1", addrBookName1);		
		mav.addObject("addrBookName2", addrBookName2);
		mav.addObject("contactNames",contactNames);
		
		return mav;
	}
	
	
}
