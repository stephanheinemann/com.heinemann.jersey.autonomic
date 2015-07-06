package com.heinemann.jersey.autonomic.mapek;

import com.heinemann.grpc.apmplanner.events.ApmEvents.UasEvent;

public class EventHandler {

	private KnowledgeBase knowledgeBase;

	public EventHandler(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}

	public void handle(UasEvent uasEvent) {

	}
}
