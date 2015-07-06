package com.heinemann.jersey.autonomic.mapek;

import java.net.URI;

public class Monitor {
	private KnowledgeBase knowledgeBase;
	private URI managedResource;

	public Monitor(KnowledgeBase knowledgeBase, URI managedResource) {
		this.knowledgeBase = knowledgeBase;
		this.managedResource = managedResource;
	}

	public void monitor() {
	}
}
