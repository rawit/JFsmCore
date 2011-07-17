package org.jfsm;

/**
 * This interface models an action that will be executed when a transition fires
 * after receiving an event that matched the transition's event type and guard
 * condition.
 */
public interface ActionI {

	/**
	 * Executes the action..
	 * 
	 * @param triggeringEvent The event that triggered the action
	 */
	void execute(Object triggeringEvent);

	/**
	 * Gets the Expression attribute of the ActionI object.
	 * 
	 * @return The Expression value
	 */
	String getExpression();

	/**
	 * Set the hasBeenExecuted attribute.
	 * 
	 * @param pHasBeenExecuted value
	 */
	void setHasBeenExecuted(final boolean pHasBeenExecuted);

	/**
	 * Check if this action has been executed since last reset of the state.
	 * 
	 * @return true if it has been executed, otherwise false
	 */
	boolean hasBeenExecuted();

}
