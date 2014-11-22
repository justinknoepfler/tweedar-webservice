package tweedar.jdbc;

import org.apache.camel.Exchange;

public class QueryTranslator {
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

}
