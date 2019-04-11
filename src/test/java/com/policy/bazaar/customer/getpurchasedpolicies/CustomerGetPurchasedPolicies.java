package com.policy.bazaar.customer.getpurchasedpolicies;

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
public class CustomerGetPurchasedPolicies {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getprofileFound() {
		try {

			String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAxNiwiZXhwIjoxNjU0NDQ0Mjk0fQ.hkcLo-9N2X_gMbhLRzA5vcmHtrHkYYofoyc50DlLf0w";
			String values = String.format("Bearer %s", validToken);

			mvc.perform(MockMvcRequestBuilders.get("/customers/getpurchasedpolicies/2016")
					.header("Authorization", values).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.data[0].policyname").value("LIC e-term Insurance"))
					.andExpect(jsonPath("$.data[0].pid").value("2"))
					.andExpect(jsonPath("$.data[0].startdate").value("2019-04-03T18:30:00.000+0000"))
					.andExpect(jsonPath("$.data[0].amount").value("2500000"))
					.andExpect(jsonPath("$.data[1].policyname").value("LIC JeevanAkshay"))
					.andExpect(jsonPath("$.data[1].pid").value("1"))
					.andExpect(jsonPath("$.data[1].startdate").value("2019-04-03T18:30:00.000+0000"))
					.andExpect(jsonPath("$.data[1].amount").value("1000000"))
					.andExpect(jsonPath("$.status").value(true))
					.andExpect(jsonPath("$.message").value("Purchased Policies"));

		} catch (Exception e) {
			fail("fail " + e.getMessage());
		}
	}

	@Test
	public void getprofileNotFound() {
		try {

			String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAxNiwiZXhwIjoxNjU0NDQ0Mjk0fQ.hkcLo-9N2X_gMbhLRzA5vcmHtrHkYYofoyc50DlLf0w";
			String values = String.format("Bearer %s", validToken);

			ResultActions andExpect = mvc.perform(MockMvcRequestBuilders.get("/customers/getpurchasedpolicies/2090")
					.header("Authorization", values).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

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
