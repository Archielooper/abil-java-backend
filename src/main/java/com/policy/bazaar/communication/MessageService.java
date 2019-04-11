package com.policy.bazaar.communication;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.policy.bazaar.customers.model.Customers;
 
@Service
public class MessageService {

	public String sendSms(Customers cust) throws Exception {
		try {
			// Construct data
			String apiKey = "apikey=" + "	ZYgc/mK13mU-7O0MOcMbzlZVAaBnJTPYPy9K9t96ip";
			String message = "&message=" + "Hi," +cust.getFirstname()+ " Your Account has been created";
			String sender = "&sender=" + "TXTLCL";
			String numbers = "&numbers=" + cust.getMobile();

			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();

			return stringBuffer.toString();
		} catch (Exception e) {
			throw e; 
		}
	}

}
