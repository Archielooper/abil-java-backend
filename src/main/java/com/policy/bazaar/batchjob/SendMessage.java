package com.policy.bazaar.batchjob;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.policy.bazaar.communication.SendEmail;


public class SendMessage {

	@Autowired
	SendEmail sendEmail;
	

	// @Scheduled(initialDelay = 1000, fixedRate = 10000)
	@Scheduled(cron = "0 */2 * ? * * ")
	public void sendEmail() throws Exception {

		sendEmail.sendEmail("abhadauria2612@gmail.com", UUID.randomUUID());
	}

}
