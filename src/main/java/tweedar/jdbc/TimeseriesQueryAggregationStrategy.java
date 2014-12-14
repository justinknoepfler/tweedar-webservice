package tweedar.jdbc;


import java.util.ArrayList;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class TimeseriesQueryAggregationStrategy implements AggregationStrategy {
	
	private static Logger LOGGER = Logger.getLogger(TimeseriesQueryAggregationStrategy.class.getName());

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		if(oldExchange == null){
			newExchange = createNewAggregationExchange(newExchange);
		}
		else {
			newExchange = addNewExchange(oldExchange, newExchange);
		}
		return newExchange;
	}

	private Exchange createNewAggregationExchange(Exchange exchange){
		HashMap<String, Object> newBody = new HashMap<String, Object>();
		ArrayList<Object> exchangeBody = (ArrayList<Object>) exchange.getIn().getBody();
		ArrayList<HashMap<String, Object>> wrappedBody = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> toAdd = new HashMap<String,Object>();
		toAdd.put("scores", exchangeBody);
		toAdd.put("id", exchange.getProperty("gridpoint_id"));
		toAdd.put("x_index", exchange.getProperty("gridpoint_x_index"));
		toAdd.put("y_index", exchange.getProperty("gridpoint_y_index"));
		wrappedBody.add(toAdd);
		LOGGER.log(Level.ERROR, exchange.toString());
		newBody.put("gridpoints", wrappedBody);
		newBody.put("time_start", exchange.getProperty("time_start"));
		newBody.put("time_end", exchange.getProperty("time_end"));
		exchange.getIn().setBody(newBody);
		return exchange;
	}
	
	private Exchange addNewExchange(Exchange oldExchange, Exchange newExchange){
		HashMap<String, Object> oldBody = (HashMap<String, Object>) oldExchange.getIn().getBody();
		ArrayList<HashMap<String,Object>> oldArray = (ArrayList<HashMap<String,Object>>)oldBody.get("gridpoints");
		ArrayList<Object> exchangeBody = (ArrayList<Object>) newExchange.getIn().getBody();
		HashMap<String,Object> toAdd = new HashMap<String,Object>();
		toAdd.put("scores", exchangeBody);
		toAdd.put("id", newExchange.getProperty("gridpoint_id"));
		toAdd.put("x_index", newExchange.getProperty("gridpoint_x_index"));
		toAdd.put("y_index", newExchange.getProperty("gridpoint_y_index"));
		oldArray.add(toAdd);
		oldBody.put("gridpoints", oldArray);
		oldExchange.getIn().setBody(oldBody);
		return oldExchange;
	}
}
