package com.app.aforo255.deposit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.aforo255.deposit.domain.Transaction;
import com.app.aforo255.deposit.producer.DepositEventProducer;
import com.app.aforo255.deposit.service.ITransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class DepositEventsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepositEventsController.class);
	@Autowired DepositEventProducer depositEventProducer;
	private @Autowired ITransactionService transactionService;
	
	@PostMapping("/v1/depositEvent")
	public ResponseEntity<Transaction> postLibraryEvent(@RequestBody Transaction transactionEvent)
		throws JsonProcessingException {
		LOGGER.info("antes sendDepositEvent_Approach3");
		Transaction transaction = this.transactionService.save(transactionEvent);
		depositEventProducer.sendDepositEvent_Approach3(transaction);
		LOGGER.info("despues sendDepositEvent_Approach3");
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(transactionEvent);
	}
}
