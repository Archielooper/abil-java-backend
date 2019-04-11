package com.policy.bazaar.customer.signin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.policy.bazaar.globalresponse.GlobalResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class CustomerSignInTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void signinIsSuccess() {

		CustomerSignInMock cusMock = new CustomerSignInMock();
		{

			cusMock.setEmail("s66@q.com");
			cusMock.setPassword("A@rchit123");

		}

		try {

			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customers/signin")
					.content(toJsonString(cusMock)).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);

			ResultActions andExpect = mvc.perform(requestBuilder.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			MvcResult andReturn = andExpect.andReturn();
			String contentAsString = andReturn.getResponse().getContentAsString();

			ObjectMapper mapper = new ObjectMapper();

			GlobalResponse globalResponse = mapper.readValue(contentAsString,
					TypeFactory.defaultInstance().constructType(GlobalResponse.class));

			assertTrue(globalResponse.getStatus() == true);
			assertEquals(globalResponse.getMessage(), "Authenticated!!!!!");

		} catch (Exception e) {
			fail("fail " + e.getMessage());
		}
	}

	@Test
	public void signInIsFail() {

		CustomerSignInMock cusMock = new CustomerSignInMock();
		{

			cusMock.setEmail("s67.com");
			cusMock.setPassword("A@rchit123");

		}

		try {

			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customers/signin")
					.content(toJsonString(cusMock)).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);


			ResultActions andExpect = mvc.perform(requestBuilder.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
			MvcResult andReturn = andExpect.andReturn();
			String contentAsString = andReturn.getResponse().getContentAsString();

			ObjectMapper mapper = new ObjectMapper();

			GlobalResponse globalResponse = mapper.readValue(contentAsString,
					TypeFactory.defaultInstance().constructType(GlobalResponse.class));
           
			assertTrue(globalResponse.getData() == null);
			assertTrue(globalResponse.getStatus() == false);
			assertEquals(globalResponse.getMessage(), "Email or Password not valid");

		} catch (Exception e) {
			fail("fail " + e.getMessage());
		}

	}

	public static String toJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
