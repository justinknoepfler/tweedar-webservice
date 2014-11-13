package tweedar.service;


import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;

public class TestService {
	
	ProducerTemplate producer;
	
	public void test(Exchange exchange){
		initializeProducer(exchange);
		Request request = (Request) exchange.getIn().getBody();
		
		assert(request != null);
		Object json = producer.requestBody("seda:test-getData", new Object());
		Response response = Response.ok(json, "application/json").build();
		exchange.getOut().setBody(response);
		
	}
	
	private void initializeProducer(Exchange exchange){
		if (producer == null){
			producer = exchange.getContext().createProducerTemplate();
		}
	}

}
