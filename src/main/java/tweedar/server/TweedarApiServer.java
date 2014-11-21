package tweedar.server;

import static spark.Spark.*;
import spark.*;

public class TweedarApiServer {
	public static void main(String[] args){
		
		get("/api/timestamp", (req, res) -> {
			return "hello."
		});
		
		Spark.get("/api/timestamp/:timestamp", (req, res) -> {
			return timestamp;
		});
		
	}
}
