package com.heinemann.jersey.autonomic.mapek;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.heinemann.grpc.apmplanner.ApmPlanner.Uas;
import com.heinemann.grpc.apmplanner.events.XmlUasEvent;

// TODO: knowledge base could be a hibernate interface
public class KnowledgeBase {

	// TODO: store Uas history data in ringbuffer
	private Uas uas;
	private boolean isCurrentSafe = true;
	private List<Action> plan;
	
	// TODO: support event priorities and priority queue
	private ConcurrentLinkedQueue<XmlUasEvent> eventQueue;
	
	public KnowledgeBase() {
		eventQueue = new ConcurrentLinkedQueue<XmlUasEvent>();
	}
	
	public void enqueueEvent(XmlUasEvent xmlUasEvent) {
		eventQueue.add(xmlUasEvent);
	}
	
	public XmlUasEvent dequeueEvent() {
		return eventQueue.remove();
	}
	
	public boolean hasEvents() {
		return !eventQueue.isEmpty();
	}
	
	public void storeUas(Uas uas) {
		this.uas = uas;
	}
	
	public Uas retrieveUas() {
		return this.uas;
	}
	
	public boolean isCurrentSafe() {
		return isCurrentSafe;
	}
	
	public void setCurrentSafe(boolean isCurrentSafe) {
		this.isCurrentSafe = isCurrentSafe;
	}
	
	public void setPlan(List<Action> plan) {
		this.plan = plan;
	}
	
	public List<Action> getPlan() {
		return plan;
	}
	
}
