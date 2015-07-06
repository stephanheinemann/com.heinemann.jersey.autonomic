package com.heinemann.jersey.autonomic.mapek;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class Monitor {
	private KnowledgeBase knowledgeBase;
	private URI managedResource;

	public Monitor(KnowledgeBase knowledgeBase, URI managedResource) {
		this.knowledgeBase = knowledgeBase;
		this.managedResource = managedResource;
	}

	public void monitor() {
		System.out.println("***** monitoring *****");
		ClientConfig config = new ClientConfig();
		Client distributor = ClientBuilder.newClient(config);
		WebTarget service = distributor.target(managedResource);
		
		String eventString = service.request().accept(MediaType.APPLICATION_XML).get(String.class);
		System.out.println(eventString);
	}
}
