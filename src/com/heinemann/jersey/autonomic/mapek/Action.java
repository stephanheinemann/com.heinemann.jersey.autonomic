package com.heinemann.jersey.autonomic.mapek;

public class Action {
	
	// TODO: retrieve supported commands (for a scenario) from managed resource
	// TODO: an action hierarchy could be used
	public static final String COMMAND = "command";
	public static final String COMMAND_GO = "go";
	public static final String COMMAND_HOME = "home";
	public static final String COMMAND_LAND = "land";
	public static final String COMMAND_LAUNCH = "launch";
	public static final String COMMAND_REBOOT = "reboot";
	public static final String COMMAND_SHUTDOWN = "shutdown";
	
	private String command;
	
	public Action(String command) {
		this.command = command;
	}
	
	public String getCommand() {
		return this.command;
	}
	
}
