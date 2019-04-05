package com.policy.bazaar.customer;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerGetProfileTests {
	
	@Autowired
	private MockMvc mvc;
    
	
	@Test
	public void signup()
	{
		try {
			
			String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAxNiwiZXhwIjoxNjU0NDQ0Mjk0fQ.hkcLo-9N2X_gMbhLRzA5vcmHtrHkYYofoyc50DlLf0w";
			String values = String.format("Bearer %s", validToken);
			
			mvc.perform(MockMvcRequestBuilders.get("/customers/getProfile/2016")
				.header("Authorization", values)	
		        .accept(MediaType.APPLICATION_JSON))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.data.firstname").value("Lucky"))
			    .andExpect(jsonPath("$.data.lastname").value("Kumar"))
			    .andExpect(jsonPath("$.data.email").value("l2@k.com"))
			    .andExpect(jsonPath("$.data.mobile").value("9876543210"));
			    
			

		} catch (Exception e) {
			fail("fail ");
		}
	}
	
	

}
