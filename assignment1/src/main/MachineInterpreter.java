package main;

import main.metamodel.Machine;
import main.metamodel.State;
import main.metamodel.Transition;


public class MachineInterpreter {
	private Machine machine;
	private State currentState;

	public void run(Machine m) {
		this.machine = m;
		currentState = m.getInitialState();
	}
	
	public State getCurrentState() {
		return currentState;
	}

	public void processEvent(String string) {
		for (Transition t : currentState.getTransitions()) {
			if (t.getEvent() == string) {
				
				Integer val;
				if (t.isConditional()) {
					val = this.getInteger(t.getConditionVariableName());
				} else {
					val = null;
				};
	
				if (!t.isConditional() 
						||	(t.isConditionEqual() && val.equals(t.getConditionComparedValue())) 
						||	(t.isConditionGreaterThan() && val > t.getConditionComparedValue()) 
						||	(t.isConditionLessThan() && val < t.getConditionComparedValue())
						) {
					
					if (t.hasSetOperation()) {
						machine.getIntegers().put(t.getOperationVariableName().toString(), t.getSetValue());
					}
					if (t.hasIncrementOperation()) {
						machine.getIntegers().put(t.getOperationVariableName().toString(),machine.getIntegers().get(t.getOperationVariableName()) + 1);
					} 
					if (t.hasDecrementOperation()) {
						machine.getIntegers().put(t.getOperationVariableName().toString(),machine.getIntegers().get(t.getOperationVariableName()) - 1);
					}
					
					currentState = t.getTarget();
					break;
				};
			};
		};
	}

	public int getInteger(String string) {
		return machine.getIntegers().get(string);
	}

}
