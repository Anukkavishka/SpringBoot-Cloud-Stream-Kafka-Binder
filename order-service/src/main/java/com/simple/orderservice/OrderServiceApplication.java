package com.simple.orderservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.support.SendToMethodReturnValueHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.simple.orderservice.model.OrderEvent;


@RestController
@EnableBinding(OrderSource.class)
@EnableAutoConfiguration
@SpringBootApplication
public class OrderServiceApplication {

	
	
	//private OrderSource source;
	private MessageChannel source;
	private SubscribableChannel incomingsource;
	
	
	public OrderServiceApplication(OrderSource channels) {
		this.source=channels.ordersOut();
		this.incomingsource=channels.ordersIn();
	}

	@PostMapping("/msg/")
	public void publish(@RequestBody OrderEvent orderEvent) {
		
		
		Message<OrderEvent> orderin=MessageBuilder.withPayload(orderEvent).build();
		this.source.send(orderin);
		
	}
	
	//creating the listener
	
	@Component
	@EnableBinding(OrderSource.class)
	@EnableAutoConfiguration
	public static class SimpleTest {
		private final Log log=LogFactory.getLog(getClass());
		
		
	    @StreamListener(target = OrderSource.INPUT)
	    public void inputPayload(OrderEvent orderEvent) {
	    	System.out.println("Processed OrderEvent Recieved");
	    	log.info(orderEvent.getOrder().getOrderId()+"\n"+orderEvent.getOrder().getProName()+"\n"+orderEvent.getOrder().getQty()+"\nOrder Status \t"+orderEvent.getStatus().toString());
	    	System.out.println(orderEvent.getOrder().getOrderId()+"\n"+orderEvent.getOrder().getProName()+"\n"+orderEvent.getOrder().getQty()+"\nOrder Status \t"+orderEvent.getStatus().toString());	
	    	System.out.println("Processed OrderEvent Recieved and Logged Successfully");
	    }

	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
		
		System.out.println("OrderServiceApplication Running.......................");
	
	}

	
}

//////////////////////////////////////End of the Application Class/////////////////////////////////////////////////////


interface OrderSource {
	
String INPUT="ordersIn";	
@Input(OrderSource.INPUT)
SubscribableChannel ordersIn();


String OUTPUT="ordersOut";
@Output(OrderSource.OUTPUT)
MessageChannel ordersOut();

	
}
