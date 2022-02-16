package main;

import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachine {
    private final List<State> states = new ArrayList<>();
	public final Map<String, Integer> integers = new HashMap<>();
	public String currentTransition = null;
	public State initial;
	public State currentState;
	public Machine machine;

	public Machine build() {
		return new Machine(states, initial, integers);
	}

	public StateMachine state(String string) {
		boolean contains = false;
		State state = null;
		for (State s : states) {
			if (s.getName() == string) {
				contains = true;
				state = s;
			};
		};
		
		if (contains) {
			this.currentState = state;
		} else {
			final State newState = new State(string);
			states.add(this.currentState = newState);
		};
		
		return this;
	}

	public StateMachine initial() {
		initial = states.get(states.size() - 1);		
		return this;
	}

	public StateMachine when(String string) {
        currentTransition = string;
		return this;
	}

	public StateMachine to(String string) {
		boolean contains = false;
		State state = null;
		for (State s : states) {
			if (s.getName() == string) {
				contains = true;
				state = s;
			};
		};
		
		if (contains) {
			Transition T = new Transition(currentTransition, state);
			currentState.getTransitions().add(T);
		} else {
			final State newState = new State(string);
			states.add(newState);
			currentState.getTransitions().add(new Transition(currentTransition, newState));
		};

		return this;
	}

	public StateMachine integer(String string) {
		integers.put(string, 0);
		return this;
	}

	public StateMachine set(String string, int i) {
		Transition ct = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
		ct.setSetOperation(true);
		ct.setSetValue(i);
		ct.setOperationVariableName(string);
		return this;
	}

	public StateMachine increment(String string) {
		Transition ct = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
		ct.setIncrementOperation(true);
		ct.setOperationVariableName(string);
		return this;
	}

	public StateMachine decrement(String string) {
		Transition ct = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
		ct.setDecrementOperation(true);
		ct.setOperationVariableName(string);
		return this;
	}

	public StateMachine ifEquals(String string, int i) {
		Transition ct = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
		ct.setConditionVariableName(string);
		ct.setConditionEqual(true);
		ct.setConditionComparedValue(i);
		return this;
	}

	public StateMachine ifGreaterThan(String string, int i) {
		Transition ct = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
		ct.setConditionVariableName(string);		
		ct.setConditionGreaterThan(true);
		ct.setConditionComparedValue(i);
		return this;
	}

	public StateMachine ifLessThan(String string, int i) {
		Transition ct = currentState.getTransitions().get(currentState.getTransitions().size() - 1);
		ct.setConditionVariableName(string);		
		ct.setConditionLessThan(true);
		ct.setConditionComparedValue(i);
		return this;
	}

}
