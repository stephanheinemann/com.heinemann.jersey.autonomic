package com.heinemann.jersey.autonomic.mapek;

import java.net.URI;

public class Effector {

	private KnowledgeBase knowledgeBase;
	private URI managedResource;

	public Effector(KnowledgeBase knowledgeBase, URI managedResource) {
		this.knowledgeBase = knowledgeBase;
		this.managedResource = managedResource;
	}

	public void effect() {
	}
}
