package com.restws.ds.test;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class Test {

	public static final String BASE_URI = "http://localhost:8080/RESTfulWS";
	public static final String PATH_NAME = "UserInfoService/name";
	public static final String PATH_AGE = "UserInfoService/age";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name = "Dinesh";
		int age = 25;
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);

		WebResource resource = client.resource(BASE_URI);

		WebResource nameResource = resource.path("rest").path(
				PATH_NAME + '/' + name);

		System.out.println("Client Response \n"
				+ getClientResponse(nameResource));
		
		System.out.println("Response \n" + getResponse(nameResource) + "\n\n");
		
		WebResource ageResource = resource.path("rest").path(PATH_AGE).path(Integer.toString(age));
		System.out.println("Client Response \n"
		+ getClientResponse(ageResource));
		System.out.println("Response \n" + getResponse(ageResource));

	}

	private static String getClientResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_XML).get(ClientResponse.class)
				.toString();
	}
	
	private static String getResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_XML).get(String.class);
		}

}
