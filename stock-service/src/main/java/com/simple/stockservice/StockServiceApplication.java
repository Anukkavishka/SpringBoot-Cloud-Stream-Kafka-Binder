package com.simple.stockservice;

import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@EnableBinding(StockSource.class)
@EnableAutoConfiguration
@SpringBootApplication
public class StockServiceApplication {

	private SubscribableChannel source;
	
	private final Log log=LogFactory.getLog(getClass());
	
	public StockServiceApplication(StockSource channel) {
		super();
		source=channel.ordersOut();
	}
	
	
/*
	@Bean
	IntegrationFlow integrationflow(StockSource s) {
		return IntegrationFlows.from(s.ordersOut()).handle(String.class,(payload,headers) -> {
			System.out.println("new String"+payload);
			log.info("log new+"+payload);
			return null; 
		}).get();
		
		
	}*/
	
	@Component
	@EnableBinding(StockSource.class)
	@EnableAutoConfiguration
	public static class SimpleTest {
		private final Log log=LogFactory.getLog(getClass());
		

	    @StreamListener(target = StockSource.INPUT)
	    public void bark(String msg) {
	    	log.info("linside static class"+msg.toString());
	    	System.out.println("inside static class"+msg.toString());
			
			
	    }

	}
	
	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}
	
	
	

	
/*	public static class TestPojoWithAnnotatedArguments {

		private final Log log=LogFactory.getLog(getClass());
		
	    @StreamListener(target =StockSource.INPUT)
	    @SendTo(StockSource.OUTPUT)
	    public String receiveStock(@Payload String value ) {
	      
	    	return value+"hey this value is processed";
	    	
	   //// source.ordersIn().send(MessageBuilder.withPayload(value).build());	
	    	
	    }

	   
	}*/

}





interface StockSource {
	//this channel recieves the message to process from the order service	
	String INPUT="ordersOut";	
	@Input(StockSource.INPUT)
	SubscribableChannel ordersOut();

	//String OUTPUT="ordersIn";	
	//@Output(StockSource.OUTPUT)
	//MessageChannel ordersIn();


	}

