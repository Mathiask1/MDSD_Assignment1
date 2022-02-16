package main.metamodel;

import java.util.ArrayList;
import java.util.List;

public class State {
	private final String name;
	public final List<Transition> transitions = new ArrayList<>();

	public State(String name) {
		this.name = name;
	}

	public Object getName() {

		return name;
	}

	public List<Transition> getTransitions() {

		return transitions;
	}

	public Transition getTransitionByEvent(String string) {
		String event = string;
		for (Transition t : transitions) {
			if (t.getEvent() == event) {
				return t;
			};
		};

		return null;
	}

}
