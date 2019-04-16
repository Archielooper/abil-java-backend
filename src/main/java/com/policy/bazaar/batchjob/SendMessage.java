package com.policy.bazaar.batchjob;

import java.util.UUID;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.policy.bazaar.communication.SendEmail;

@Component
public class SendMessage {

	@Autowired
	SendEmail sendEmail;

	@Scheduled(initialDelay = 1000, fixedRate = 10000)
	public void sendEmail() throws Exception {

		sendEmail.sendEmail("abhadauria2612@gmail.com", UUID.randomUUID());
	}

}
