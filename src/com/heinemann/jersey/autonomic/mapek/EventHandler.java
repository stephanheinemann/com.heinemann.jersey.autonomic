package com.heinemann.jersey.autonomic.mapek;

import com.heinemann.grpc.apmplanner.events.XmlUasEvent;
import com.heinemann.jersey.autonomic.model.Policy;

public class EventHandler {

	public static final String EVENT_PARAMETER_DELIM = ";";
	public static final String BATTERY_CHANGED_EVENT = "batteryChanged";
	public static final String BATTERY_PARAMETER_CURRENT = "current";
	
	private KnowledgeBase knowledgeBase;

	public EventHandler(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}

	public void handle(XmlUasEvent xmlUasEvent) {
		System.out.println("***** receivedEvent *****");
		System.out.println(xmlUasEvent.getIdentifier());
		System.out.println(xmlUasEvent.getSource());
		System.out.println(xmlUasEvent.getParameters());
		
		if (MapekLoop.getPolicy().equals(Policy.SAFETY)) {
			if (xmlUasEvent.getIdentifier().equals(BATTERY_CHANGED_EVENT)) {
				knowledgeBase.enqueueEvent(xmlUasEvent);
			}
		}
	}
}
