package com.pwc.au.addressBook.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.web.context.WebApplicationContext;

import com.pwc.au.book.controller.ContactController;
import com.pwc.au.book.repository.AddressBookRepository;
import com.pwc.au.book.repository.ContactRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(classes = {ContactController.class})
public class ContactControllerTest {
	
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
	public void testShowViewContactPage() throws Exception {
		MvcResult result=this.mockMvc.perform(MockMvcRequestBuilders.get("/addressBook/{id}/contacts",1)).andReturn();
		assertEquals("view_contacts", result.getModelAndView().getViewName());
	}
	
	@Test
	public void testShowAddContactPage() throws Exception {
		MvcResult result=this.mockMvc.perform(MockMvcRequestBuilders.get("/addressBook/{id}/contact",1)).andReturn();
		assertEquals("add_contact", result.getModelAndView().getViewName());
	}

}
