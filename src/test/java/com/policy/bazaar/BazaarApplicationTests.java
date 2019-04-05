package com.policy.bazaar;

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
public class BazaarApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void checkUnAuthorized() {

		try {
			String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAxNiwiZXhwIjoxMTU1NDI4ODM30.XgFvdCPyKvQdT6Fm3QJEkrGX32O6_P-w3m0F6TSw2ug";
			String values = String.format("Bearer %s", invalidToken);
			mvc.perform(MockMvcRequestBuilders.get("/customers/getProfile/2014").header("Authorization", values)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());

		} catch (Exception e) {
			fail("fail ");
		}
	}

	@Test
	public void checkAuthorized() {

		try {
			String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAxNiwiZXhwIjoxMTU1NDI4ODQ1M30.XgFvdCPyKvQdT6Fm3QJEkrGX32O6_P-w3m0F6TSw2ug";
			String values = String.format("Bearer %s", validToken);
			mvc.perform(MockMvcRequestBuilders.get("/customers/getProfile/2014").header("Authorization", values)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.data.firstname").value("Archit"))
					.andExpect(jsonPath("$.data.lastname").value("Bhadauria"))
					.andExpect(jsonPath("$.data.email").value("abhadauria@gmail.com"))
					.andExpect(jsonPath("$.data.mobile").value("9987655446"))
					.andExpect(jsonPath("$.status").value(true))
					.andExpect(jsonPath("$.message").value("User Found!!!!"));
		} catch (Exception e) {
			fail("fail ");
		}
	}

	@Test
	public void checkpurchasedpolicies() {

		try {
			String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MjAxNiwiZXhwIjoxMTU1NDM2NzY4NX0.QRlosEluAmlIFXIDWwN8CvWxsiEdVQqnOkrbdM6NN-E";
			String values = String.format("Bearer %s", validToken);
			mvc.perform(MockMvcRequestBuilders.get("/customers/getpurchasedpolicies/2014")
					.header("Authorization", values).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.data[0].policyname").value("LIC JeevanAkshay"))
					.andExpect(jsonPath("$.data[0].startdate").value("2018-12-22T18:30:00.000+0000"));
		} catch (Exception e) {
			fail("fail ");
		}
	}

}
