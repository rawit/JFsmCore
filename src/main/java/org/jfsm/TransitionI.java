package org.jfsm;

import java.util.List;

import org.jfsm.core.events.Event;

/**
 * Interface for a transition between two states in the state machine. A
 * transitions has attributes for to the two states it is a transition between,
 * the event type, the guard condition and the action that will be executed when
 * the transition fires
 */
public interface TransitionI {

	/**
	 * Gets the GuardConditionI attribute of the TransitionI object.
	 * 
	 * @return The GuardConditionI value
	 */
	GuardConditionI getGuardCondition();

	/**
	 * Resolve all objects according to submitted Context.
	 * 
	 * @param fsmContext The FsmContext value
	 */
	void setContext(Context fsmContext);

	/**
	 * Gets the Event type attribute of the TransitionI object.
	 * 
	 * @return The Event value
	 */
	Event getEvent();

	/**
	 * Gets the ActionI attribute of the TransitionI object.
	 * 
	 * @return The ActionI value
	 */
	List<ActionI> getActions();

    /**
     * Evaluate the event.
     * 
     * @param event The event received
     * @return true if firing, otherwise false
     */
    boolean evaluate(Object event);

    /**
	 * Gets the "from state" attribute of the TransitionI object.
	 * 
	 * @return The "from state" value
	 */
	StateI getFromState();

	/**
	 * Gets the "to state" attribute of the TransitionI object.
	 * 
	 * @return The "to State" value
	 */
	int getToStateId();

}
