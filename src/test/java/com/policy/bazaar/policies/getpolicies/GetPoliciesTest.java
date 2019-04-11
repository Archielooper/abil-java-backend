package com.policy.bazaar.policies.getpolicies;

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
public class GetPoliciesTest {
	
	@Autowired
	private MockMvc mvc;
	
	
	@Test
	public void getprofileFound() {
		try {

			mvc.perform(MockMvcRequestBuilders.get("/policy/getPolicies")
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.data[0].policyname").value("LIC JeevanAkshay"))
					.andExpect(jsonPath("$.data[0].description").value("Immediate Annuity Pension Plan"))
					.andExpect(jsonPath("$.data[0].amount").value("1000000"))
					.andExpect(jsonPath("$.data[0].pid").value(1))
					.andExpect(jsonPath("$.data[1].policyname").value("LIC e-term Insurance"))
					.andExpect(jsonPath("$.data[1].description").value("Pure Term Plan"))
					.andExpect(jsonPath("$.data[1].amount").value("2500000"))
					.andExpect(jsonPath("$.data[1].pid").value(2))
					.andExpect(jsonPath("$.data[2].policyname").value("LIC New Children’s Money Back Plan"))
					.andExpect(jsonPath("$.data[2].description").value("Traditional Money Back Child Plan"))
					.andExpect(jsonPath("$.data[2].amount").value("1500000"))
					.andExpect(jsonPath("$.data[2].pid").value(3))
					.andExpect(jsonPath("$.data[3].policyname").value("LIC JeevanAnand"))
					.andExpect(jsonPath("$.data[3].description").value("Participating Traditional Endowment Plan"))
					.andExpect(jsonPath("$.data[3].amount").value("5500000"))
					.andExpect(jsonPath("$.data[3].pid").value(4))
					.andExpect(jsonPath("$.data[4].policyname").value("LIC Jeevan Saral"))
					.andExpect(jsonPath("$.data[4].description").value("Endowment Plan"))
					.andExpect(jsonPath("$.data[4].amount").value("1100000"))
					.andExpect(jsonPath("$.data[4].pid").value(5001))
					.andExpect(jsonPath("$.data[5].policyname").value("My new updated Policy"))
					.andExpect(jsonPath("$.data[5].description").value("My new updated Description"))
					.andExpect(jsonPath("$.data[5].amount").value("50000"))
					.andExpect(jsonPath("$.data[5].pid").value(5002))
					.andExpect(jsonPath("$.status").value(true))
					.andExpect(jsonPath("$.message").value("Authorized!!!!"));

		} catch (Exception e) {
			fail("fail " + e.getMessage());
		}

}
	}
