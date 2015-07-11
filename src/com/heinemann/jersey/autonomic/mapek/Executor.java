package com.heinemann.jersey.autonomic.mapek;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class Executor {

	private KnowledgeBase knowledgeBase;
	private URI managedResource;

	public Executor(KnowledgeBase knowledgeBase, URI managedResource) {
		this.knowledgeBase = knowledgeBase;
		this.managedResource = managedResource;
	}

	public void execute() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget service = client.target(managedResource);
		
		List<Action> plan = knowledgeBase.getPlan();
		for (Action action : plan) {
			Form commandForm = new Form();
			commandForm.param(Action.COMMAND, action.getCommand());
			service.request().async().post(
					Entity.entity(commandForm, MediaType.APPLICATION_FORM_URLENCODED),
					Response.class);
		}
	}
}
