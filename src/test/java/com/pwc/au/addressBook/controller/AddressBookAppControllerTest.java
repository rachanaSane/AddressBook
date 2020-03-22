package com.pwc.au.addressBook.controller;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import com.pwc.au.book.controller.AddressBookAppController;
import com.pwc.au.book.repository.AddressBookRepository;
import com.pwc.au.book.repository.ContactRepository;


//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest(controllers = AddressBookAppController.class)
//@ContextConfiguration(classes = WebConfig.class)
@ContextConfiguration(classes = {AddressBookAppController.class})
//@AutoConfigureMockMvc
public class AddressBookAppControllerTest {

	//@Autowired
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
	public void testShowCompareAddressBook() throws Exception {
		
		
		MvcResult result=this.mockMvc.perform(MockMvcRequestBuilders.get("/viewCompare")).andReturn();
		assertEquals("compare_books", result.getModelAndView().getViewName());
		
	}
	
	@Test
	public void testCompareAddressBooks() throws Exception {
		
		String addrBookName1="personalContacts";
		String addrBookName2="businessContacts";
		List<String> addressBookNames = new ArrayList<String>();
		addressBookNames.add(addrBookName1);
		addressBookNames.add(addrBookName2);
		
		List<String> contactNames = Arrays.asList("rac","roy");
		
		when(contactRepository.findUnCommonContacts(addressBookNames)).thenReturn(contactNames);
		
		MvcResult result=this.mockMvc.perform(MockMvcRequestBuilders.post("/compare", addrBookName1,addrBookName2)).andReturn();
		
		assertEquals("compare_books", result.getModelAndView().getViewName());
		
	}

}
