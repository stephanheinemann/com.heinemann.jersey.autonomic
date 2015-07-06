package com.heinemann.jersey.autonomic.model;

public enum Policy {
	EFFECTIVENESS,
	SAFETY;
	
	public String toString() {
		String policy;
		switch(this) {
		case EFFECTIVENESS:
			policy = "effectiveness";
			break;
		case SAFETY:
			policy = "safety";
			break;
		default:
			policy = "safety";
		}
		return policy;
	}
}
