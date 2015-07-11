package com.heinemann.jersey.autonomic.mapek;

import java.util.ArrayList;
import java.util.List;

public class Planner {
	private KnowledgeBase knowledgeBase;

	public Planner(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}

	public void plan() {
		List<Action> plan = new ArrayList<Action>();
		if (!knowledgeBase.isCurrentSafe()) {
			plan.add(new Action(Action.COMMAND_REBOOT));
		}
		knowledgeBase.setPlan(plan);
	}
}
