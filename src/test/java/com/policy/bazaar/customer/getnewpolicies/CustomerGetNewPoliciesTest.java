package com.policy.bazaar.customer.getnewpolicies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.policy.bazaar.globalresponse.GlobalResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerGetNewPoliciesTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void getprofileFound() {
		try {

			String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAxNiwiZXhwIjoxNjU0NDQ0Mjk0fQ.hkcLo-9N2X_gMbhLRzA5vcmHtrHkYYofoyc50DlLf0w";
			String values = String.format("Bearer %s", validToken);

			mvc.perform(MockMvcRequestBuilders.get("/customers/getnewpolicies/2017")
					.header("Authorization", values).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.data[0].policyname").value("LIC New Children’s Money Back Plan"))
					.andExpect(jsonPath("$.data[0].description").value("Traditional Money Back Child Plan"))
					.andExpect(jsonPath("$.data[0].amount").value("1500000"))
					.andExpect(jsonPath("$.data[1].policyname").value("LIC JeevanAnand"))
					.andExpect(jsonPath("$.data[1].description").value("Participating Traditional Endowment Plan"))
					.andExpect(jsonPath("$.data[1].amount").value("5500000"))
					.andExpect(jsonPath("$.data[2].policyname").value("LIC Jeevan Saral"))
					.andExpect(jsonPath("$.data[2].description").value("Endowment Plan"))
					.andExpect(jsonPath("$.data[2].amount").value("1100000"))
					.andExpect(jsonPath("$.status").value(true))
					.andExpect(jsonPath("$.message").value("New Policies to purchase!!!!"));

		} catch (Exception e) {
			fail("fail " + e.getMessage());
		}
	}
		@Test
		public void getprofileNotFound() {
			try {

				String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAxNiwiZXhwIjoxNjU0NDQ0Mjk0fQ.hkcLo-9N2X_gMbhLRzA5vcmHtrHkYYofoyc50DlLf0w";
				String values = String.format("Bearer %s", validToken);

				ResultActions andExpect = mvc.perform(MockMvcRequestBuilders.get("/customers/getnewpolicies/2090").header("Authorization", values)
						.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
				
				MvcResult andReturn = andExpect.andReturn();
				String contentAsString = andReturn.getResponse().getContentAsString();

				ObjectMapper mapper = new ObjectMapper();

				GlobalResponse globalResponse = mapper.readValue(contentAsString,
						TypeFactory.defaultInstance().constructType(GlobalResponse.class));
	            
				assertTrue(globalResponse.getData() == null);
				assertTrue(globalResponse.getStatus() == false);
				assertEquals(globalResponse.getMessage(), "Customer with the id-2090 not found!!!!");

						
			} catch (Exception e) {
				fail("fail " + e.getMessage());
			}
		}

}
	
