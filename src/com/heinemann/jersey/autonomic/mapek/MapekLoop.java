package com.heinemann.jersey.autonomic.mapek;

import java.net.URI;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.heinemann.grpc.apmplanner.events.XmlUasEvent;
import com.heinemann.jersey.autonomic.model.Policy;

public class MapekLoop implements Runnable, ServletContextListener {

	public static final String MANAGER_RESOURCE = "http://rigi-lab-03.cs.uvic.ca:8080/com.heinemann.jersey.autonomic/rest/am";
	public static final String MANAGED_RESOURCE = "http://rigi-lab-03.cs.uvic.ca:8080/com.heinemann.jersey.apmplanner/rest/uas";
	public static final String SUBSCRIBERS = "subscribers";
	public static final String SUBSCRIBER = "subscriber";
	
	private Thread mapek;
	private boolean isRunning = false;
	private static KnowledgeBase knowledgeBase;
	private static Monitor monitor;
	private static Analyzer analyzer;
	private static Planner planner;
	private static Executor executor;
	private static EventHandler eventHandler;

	private static URI managedResource;
	private static Policy policy = Policy.SAFETY;

	// TODO: think about singleton instead of static methods
	public MapekLoop() {
		managedResource = UriBuilder.fromUri(MANAGED_RESOURCE).build();
		knowledgeBase = new KnowledgeBase();
		monitor = new Monitor(knowledgeBase, managedResource);
		analyzer = new Analyzer(knowledgeBase);
		planner = new Planner(knowledgeBase);
		executor = new Executor(knowledgeBase, managedResource);
		eventHandler = new EventHandler(knowledgeBase);
	}

	public static URI getManagedResource() {
		return MapekLoop.managedResource;
	}

	public static void setManagedResource(URI managedResource) {
		MapekLoop.managedResource = managedResource;
		// subscribe for events from managed resource
		// TODO: obtain resource in a cleaner fashion (no implicit rule)
		URI subscribersResource = managedResource.resolve(SUBSCRIBERS);
		ClientConfig config = new ClientConfig();
		Client distributor = ClientBuilder.newClient(config);
		WebTarget service = distributor.target(subscribersResource);
		
		// TODO: obtain own resource in a cleaner fashion (glassfish registry)
		Form subscriberForm = new Form();
		subscriberForm.param(SUBSCRIBER, MANAGER_RESOURCE);
		service.request().async().post(Entity.entity(subscriberForm, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
	}

	public static Policy getPolicy() {
		return MapekLoop.policy;
	}

	public static void setPolicy(Policy policy) {
		MapekLoop.policy = policy;
	}

	public static void handleEvent(XmlUasEvent xmlUasEvent) {
		eventHandler.handle(xmlUasEvent);
		// TODO: think about concurrency issues
		// EAPE immediately - do not wait for the next loop
		analyzer.analyze();
		planner.plan();
		executor.execute();
	}

	@Override
	public void run() {
		setManagedResource(managedResource);
		while (isRunning) {
			monitor.monitor();
			analyzer.analyze();
			planner.plan();
			executor.execute();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		if (!isRunning) {
			isRunning = true;
			mapek = new Thread(this);
			mapek.start();
		}
	}

	public void stop() {
		if (isRunning) {
			isRunning = false;
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		start();
	}

}
