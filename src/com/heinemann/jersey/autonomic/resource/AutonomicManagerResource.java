package com.heinemann.jersey.autonomic.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import com.heinemann.grpc.apmplanner.events.ApmEvents.UasEvent;
import com.heinemann.jersey.autonomic.mapek.MapekLoop;
import com.heinemann.jersey.autonomic.model.AutonomicManager;

@Path("/am")
public class AutonomicManagerResource {
	
	@GET
	@Produces({
		MediaType.TEXT_PLAIN,
		MediaType.TEXT_HTML,
		MediaType.TEXT_XML,
		MediaType.APPLICATION_XML,
		MediaType.APPLICATION_XHTML_XML,
		MediaType.APPLICATION_JSON })
	public AutonomicManager getAutonomicManager() {
		AutonomicManager am = new AutonomicManager();
		am.setPolicy(MapekLoop.policy);
		am.setManagedResource(MapekLoop.managedResource);
		return am;
	}
	
	@PUT
	public void setAutonomicManager() {
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public void receiveEvent(JAXBElement<UasEvent> event) {
		UasEvent uasEvent = event.getValue();
		System.out.println("***** receivedEvent *****");
		System.out.println(uasEvent.getIdentifier());
		System.out.println(uasEvent.getSource());
		System.out.println(uasEvent.getParameters());
	}
	
}
