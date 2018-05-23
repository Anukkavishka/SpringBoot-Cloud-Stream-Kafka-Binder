package com.simple.stockservice;

import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.simple.stockservice.model.OrderEvent;
import com.simple.stockservice.model.OrderEventType;

@EnableBinding(StockSource.class)
@EnableAutoConfiguration
@SpringBootApplication
public class StockServiceApplication {

	private SubscribableChannel source;
	public static MessageChannel outgngsource;
	
	private final Log log=LogFactory.getLog(getClass());
	
	public StockServiceApplication(StockSource channel) {
		super();
		this.source=channel.ordersOut();
		this.outgngsource=channel.ordersIn();
		
	}
	
	

	
	@Component
	@EnableBinding(StockSource.class)
	@EnableAutoConfiguration
	public static class SimpleTest {
		private final Log log=LogFactory.getLog(getClass());
		

	    @StreamListener(target = StockSource.INPUT)
	    public void inputPayload(OrderEvent orderEvent) {
	    	log.info("\n"+orderEvent.getOrder().getOrderId()+"\n"+orderEvent.getOrder().getProName()+"\n"+orderEvent.getOrder().getQty()+"\nOrder Status \t"+orderEvent.getStatus().toString());
	    	
	    	orderEvent.setStatus(OrderEventType.APRROVED);	
	    	Message<OrderEvent> orderin=MessageBuilder.withPayload(orderEvent).build();
			outgngsource.send(orderin);
			
			
	    }
	    
	   

	}
	 
	
	
	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
		
	}
	
	
	



}


///////////////////////////////////////////////End Of ApplicationJava class///////////////////////////////////////////




interface StockSource {
	//this channel recieves the message to process from the order service	
	String INPUT="ordersOut";	
	@Input(StockSource.INPUT)
	SubscribableChannel ordersOut();

	String OUTPUT="ordersIn";	
	@Output(StockSource.OUTPUT)
	MessageChannel ordersIn();


	}

