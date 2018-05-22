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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(OrderSource.class)
@EnableAutoConfiguration
@SpringBootApplication
public class OrderServiceApplication {

	
	//private OrderSource source;
	private MessageChannel source;
	
	
	public OrderServiceApplication(OrderSource channels) {
		this.source=channels.ordersOut();
	}

	@PostMapping("/msg/{name}")
	public void publish(@PathVariable String name) {
		
		String greet="Hello "+name;
		
		Message<String> greeting=MessageBuilder.withPayload(greet).build();
		this.source.send(greeting);
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
		
		
	
	}
/*	
	@SendTo(OrderSource.OUTPUT)
	public static String sendval() {
		return "Apache Kafka Fucking Rules";
	}
	
	
	public static class TestPojoWithAnnotatedArguments {

		private final Log log=LogFactory.getLog(getClass());
		
	    @StreamListener(target = OrderSource.INPUT)
	    public void receiveStock(@Payload String value ) {
	      
	    	log.info("This is the recieved value after the processing"+value);
	    	
	    }

	   
	}
	*/
	
}




interface OrderSource {
	
//String INPUT="ordersIn";	
//@Input(OrderSource.INPUT)
//SubscribableChannel ordersIn();


String OUTPUT="ordersOut";
@Output(OrderSource.OUTPUT)
MessageChannel ordersOut();

	
}
