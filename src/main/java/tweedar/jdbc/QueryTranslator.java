package tweedar.jdbc;

import java.util.HashMap;




import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class QueryTranslator {
	
	private static Logger LOGGER = Logger.getLogger(QueryTranslator.class.getName());
	
	
	public void getTestJdbcQuery(Exchange exchange){
		String query = "SELECT * FROM gridmap_test, bin_test WHERE gridmap_test.id = bin_test.gridmap_id && gridmap_test.id = 1415815712384;";
		exchange.getOut().setBody(query); 
	}
	
	public void getGridmaps(Exchange exchange){
		String query = "SELECT * FROM gridmap";
		exchange.getOut().setBody(query); 
	}
	
	public void getGridmapId(Exchange exchange){
		String gridmapId = (String)exchange.getIn().getHeader("gridmapId");
		String query = "SELECT * FROM bin WHERE bin.gridmap_id = " + gridmapId;
		exchange.getOut().setBody(query); 
	}

	public void moveTimestampInfoToHeaders(Exchange exchange){
		HashMap<String, Object> postBody = (HashMap<String, Object>)exchange.getIn().getBody();
		exchange.setProperty("time_end", postBody.get("time_end"));
		exchange.setProperty("time_start", postBody.get("time_start"));
	}
	public void getTimeseriesInfo(Exchange exchange){

		HashMap<String, Object> postBody = (HashMap<String, Object>)exchange.getIn().getBody();
		String startTime = (String) exchange.getProperty("time_start");
		String endTime = (String) exchange.getProperty("time_end");
		
		String x_coord = (String)postBody.get("x_index");
		String y_coord = (String)postBody.get("y_index");
		String query = "SELECT time_start, time_end, affect_score FROM gridmap, bin WHERE "
				+ "gridmap.time_start >= " + startTime + " "
						+ "&& gridmap.time_end <= " + endTime + 
						" && bin.x_index = " + x_coord + " && bin.y_index = " + y_coord 
						+ " && gridmap.id = bin.gridmap_id"
						+ " ORDER BY time_start ASC;";
		exchange.getOut().setBody(query);
		
		LOGGER.log(Level.ERROR, "query");
	}
}
