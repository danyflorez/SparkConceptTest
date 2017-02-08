package org.gradle;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import spark.Request;

public class SubDomainServices{
   
	public static List<Subdomain> lstSubdomain = new ArrayList<>();
	
	public static void main(String args[]){

		lstSubdomain.add(new Subdomain(1331,"Credivalores"));
		lstSubdomain.add(new Subdomain(23132,"Borrar"));
		
		port(9091);
		
		options("/*", (request, response) -> {

	        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
	        if (accessControlRequestHeaders != null) {
	            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
	        }

	        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
	        if (accessControlRequestMethod != null) {
	            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
	        }

	        return "OK";
	    });
		
		after((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS");
			response.header("Access-Control-Allow-Headers", "Origin, Accept, Authorization, Content-Type, usage-ip, token, ERR_MSG, usage-os, usage-device, locale");
			response.header("Access-Control-Allow-Credentials", "true");
			response.header("Access-Control-Expose-Headers", "ERR_MSG, Content-Type");
			response.header("Access-Control-Max-Age", "1209600");      
		});
		
		Gson gson = new Gson();
		get("/subdomain", (request, response) -> {
												  response.type("application/json"); 
												  return lstSubdomain;
												  }, 
							gson::toJson);
		
		put("/subdomain", (request, response) ->  {
													Subdomain subdomain = gson.fromJson(request.body(), Subdomain.class);
        											lstSubdomain.add(subdomain);
        											return lstSubdomain;
        										}, gson::toJson);
	}
}
