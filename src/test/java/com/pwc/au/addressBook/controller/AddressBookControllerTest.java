package com.pwc.au.addressBook.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import com.pwc.au.book.controller.AddressBookController;
import com.pwc.au.book.model.AddressBook;
import com.pwc.au.book.repository.AddressBookRepository;
import com.pwc.au.book.repository.ContactRepository;
import static org.mockito.Mockito.mock;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(classes = {AddressBookController.class})
public class AddressBookControllerTest {
	
	private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    
    @MockBean
	private AddressBookRepository addressBookRepository;
	
    @MockBean
    private ContactRepository contactRepository;
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    
    @Test
	public void testShowAddAddressBook() throws Exception {
		MvcResult result=this.mockMvc.perform(MockMvcRequestBuilders.get("/newAddressBook")).andReturn();
		assertEquals("new_addressBook", result.getModelAndView().getViewName());
	}
    
    @Test
	public void testSaveAddressBook() throws Exception {
		
    	AddressBook book = new AddressBook();
    	book.setName("personalContacts");
		
		 BindingResult bindingResult = mock(BindingResult.class);
		 when(bindingResult.hasErrors()).thenReturn(true);
		
		when(addressBookRepository.findByName(book.getName())).thenReturn(null);
		
		when(addressBookRepository.save(book)).thenReturn(book);
		
		MvcResult result=this.mockMvc.perform(MockMvcRequestBuilders.post("/saveAddressBook", book,bindingResult,mock(Model.class))).andReturn();
		
		assertEquals("redirect:/", result.getModelAndView().getViewName());
		
	}
	

}
