package com.policy.bazaar.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
import com.policy.bazaar.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DataJpaTest
public class CustomerSignUpTests {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Test
	
	public void signupIsSuccess() {

		CustomerSignUpMock cusMock = new CustomerSignUpMock();
		{

			Random random = new Random();
			int nextInt = random.nextInt(2000) + 1000;
			System.out.println(nextInt);

			cusMock.setFirstname("Archit");
			cusMock.setLastname("Bhadauria");
//			cusMock.setEmail(String.format("a%s@b.com", nextInt));
			cusMock.setEmail(String.format("s@q.com", ""));
			cusMock.setMobile("9863876375");
			cusMock.setPassword("Archit@123");

		}

		try {

			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customers/signup")
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
			assertTrue(globalResponse.getStatus() == true);
			assertEquals(globalResponse.getMessage(), "Successfully Registered");

		} catch (Exception e) {
			fail("fail ");
		}
	}

	@Test
	public void signupISFail() {

		CustomerSignUpMock cusMock = new CustomerSignUpMock();
		{

			cusMock.setFirstname("Archit");
			cusMock.setLastname("Bhadauria");
			cusMock.setEmail("s@q.com");
			cusMock.setMobile("9863876375");
			cusMock.setPassword("Archit@123");

		}

		try {

			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customers/signup")
					.content(toJsonString(cusMock)).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);

			mvc.perform(requestBuilder.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

		} catch (Exception e) {
			fail("fail ");
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
