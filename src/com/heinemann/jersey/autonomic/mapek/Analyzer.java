package com.heinemann.jersey.autonomic.mapek;

import java.util.StringTokenizer;

import com.heinemann.grpc.apmplanner.events.XmlUasEvent;
import com.heinemann.jersey.autonomic.model.Policy;

public class Analyzer {

	private KnowledgeBase knowledgeBase;

	private final double SAFE_CURRENT = 2.0;
	
	public Analyzer(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}

	public void analyze() {
		
		while(knowledgeBase.hasEvents()) {
			XmlUasEvent xmlUasEvent = knowledgeBase.dequeueEvent();
			if (xmlUasEvent.getIdentifier().equals(EventHandler.BATTERY_CHANGED_EVENT)) {
				if (MapekLoop.getPolicy().equals(Policy.SAFETY)) {
					StringTokenizer parameters = new StringTokenizer(xmlUasEvent.getParameters(), EventHandler.EVENT_PARAMETER_DELIM);
					while (parameters.hasMoreTokens()) {
						String parameter = parameters.nextToken();
						if (parameter.split("=")[0].equals(EventHandler.BATTERY_PARAMETER_CURRENT)) {
							if (Double.parseDouble(parameter.split("=")[1]) > SAFE_CURRENT) {
								knowledgeBase.setCurrentSafe(false);
							} else {
								knowledgeBase.setCurrentSafe(true);
							}
						}
					}
				} else {
					knowledgeBase.setCurrentSafe(true);
				}
			}
		}
	}
}
