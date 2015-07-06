package com.heinemann.jersey.autonomic.mapek;

import java.net.URI;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.UriBuilder;

import com.heinemann.jersey.autonomic.model.Policy;

public class MapekLoop implements Runnable, ServletContextListener {

	// TODO: web put managed resource and policy
	public static URI managedResource;
	public static Policy policy = Policy.SAFETY;
	
	private Thread mapek;
	private boolean isRunning = false;
	private KnowledgeBase knowledgeBase;
	private Monitor monitor;
	private Analyzer analzyer;
	private Planner planner;
	private Effector effector;
	
	public MapekLoop() {
		managedResource = UriBuilder.fromUri("http://rigi-lab-03.cs.uvic.ca:8080/com.heinemann.jersey.apmplanner/rest/uas").build();
		monitor = new Monitor(knowledgeBase, managedResource);
	}
	
	@Override
	public void run() {
		while (isRunning) {
			System.out.println("***** MAPE-K is running *****");
			monitor.monitor();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void start() {
		if (!isRunning) {
			mapek = new Thread(this);
			mapek.start();
			isRunning = true;
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
