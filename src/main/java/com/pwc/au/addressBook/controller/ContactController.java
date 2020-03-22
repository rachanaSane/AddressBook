package com.pwc.au.addressBook.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pwc.au.addressBook.model.Contact;

import com.pwc.au.addressBook.repository.AddressBookRepository;
import com.pwc.au.addressBook.repository.ContactRepository;

@Controller
public class ContactController {
	
	@Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressBookRepository addressBookRepository;
    
	@RequestMapping("/addressBook/{id}/contact")
	public ModelAndView showAddContactPage(@PathVariable(name = "id") Long addressBookId) {
		ModelAndView mav = new ModelAndView("add_contact");	
		Contact contact = new Contact();
		mav.addObject("contact", contact);		
		mav.addObject("addressBookId", addressBookId);		
		return mav;
	}
	
	
	@RequestMapping("/addContact/{id}")
	public ModelAndView addContact(@PathVariable(name = "id") Long addressBookId, @Valid @ModelAttribute("contact") Contact contact,BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			ModelAndView mav = new ModelAndView("add_contact");	
			mav.addObject("addressBookId", addressBookId);		
			return mav;
			//return "add_contact";
			//return "add_contact/"+addressBookId;
		}
		
		
		Contact savedcontact =addressBookRepository.findById(addressBookId).map(addressBook -> {
			contact.setAddressBook(addressBook);
            return contactRepository.save(contact);
        }).orElseThrow(() -> new RuntimeException("addressBookId " + addressBookId + " not found"));
		
		ModelAndView mav = new ModelAndView("redirect:/");	
	//	return mav;
		return mav;
		//return mav;
	}
	
	
	@RequestMapping("/addressBook/{id}/contacts")
	public ModelAndView showViewContactPage(@PathVariable(name = "id") Long addressBookId) {
		ModelAndView mav = new ModelAndView("view_contacts");
		List<Contact> contacts=(List<Contact>) contactRepository.findByAddressBookId(addressBookId);
		mav.addObject("contacts", contacts);
		
		return mav;
	}

   /* @GetMapping("/addressBook/{addressBookId}/contacts")
    public List<Contact> getAllContactsByAddressBookId(@PathVariable (value = "addressBookId") Long addressBookId) {
        return (List<Contact>) contactRepository.findByAddressBookId(addressBookId);
    }
    
    @GetMapping("/contacts")
    public List<String> getAllContactsByAddressBookId(@RequestParam List<Long> addressBookIds) {
        return (List<String>) contactRepository.findUnCommonRecords(addressBookIds);
    }

    @PostMapping("/addressBook/{addressBookId}/contact")
    public Contact createContact(@PathVariable (value = "addressBookId") Long addressBookId,
                                 @Valid @RequestBody Contact contact) {
        return addressBookRepository.findById(addressBookId).map(addressBook -> {
        	contact.setAddressBook(addressBook);
            return contactRepository.save(contact);
        }).orElseThrow(() -> new RuntimeException("addressBookId " + addressBookId + " not found"));
    }

    @PutMapping("/addressBook/{addressBookId}/contact/{contactId}")
    public Contact updateContact(@PathVariable (value = "addressBookId") Long addressBookId,
                                 @PathVariable (value = "contactId") Long contactId,
                                 @Valid @RequestBody Contact updatedContact) {
        if(!addressBookRepository.existsById(addressBookId)) {
            throw new RuntimeException("addressBookId " + addressBookId + " not found");
        }

        return contactRepository.findById(contactId).map(contact -> {
        	contact.setName(updatedContact.getName());
        	contact.setPhoneNumber(updatedContact.getPhoneNumber());
            return contactRepository.save(contact);
        }).orElseThrow(() -> new RuntimeException("contactId " + contactId + "not found"));
    }

    @DeleteMapping("/addressBook/{addressBookId}/contact/{contactId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "addressBookId") Long addressBookId,
                              @PathVariable (value = "contactId") Long contactId) {
        return contactRepository.findByIdAndAddressBookId(contactId, addressBookId).map(contact -> {
        	contactRepository.delete(contact);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RuntimeException("Contact not found with id " + contactId + " and addressBookId " + addressBookId));
    }*/

}
