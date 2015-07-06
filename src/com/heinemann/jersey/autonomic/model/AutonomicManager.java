package com.heinemann.jersey.autonomic.model;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AutonomicManager {
	private URI managedResource;
	private Policy policy;

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public URI getManagedResource() {
		return managedResource;
	}

	public void setManagedResource(URI manageResource) {
		this.managedResource = manageResource;
	}
}
