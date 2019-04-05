package com.policy.bazaar.customer;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerSignUpTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void signup() {
		List<CustomerSignUpMock> customers = new ArrayList<CustomerSignUpMock>();

		{
			CustomerSignUpMock cusMock = new CustomerSignUpMock();
			cusMock.setFirstname("Archit");
			cusMock.setLastname("Bhadauria");
			cusMock.setEmail("a@b.com");
			cusMock.setMobile("9863876375");
			cusMock.setPassword("Archit@123");
			customers.add(cusMock);

		}

		try {

			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("customers/signup")
					.content(toJsonString(customers)).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
            
			
			mvc.perform(requestBuilder);

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
