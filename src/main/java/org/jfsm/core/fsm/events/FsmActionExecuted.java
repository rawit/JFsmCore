package org.jfsm.core.fsm.events;

/**
 * This event is emitted when an action has been executed.
 */
public class FsmActionExecuted extends FsmEvent {

	private final Object action;

	/**
	 * Constructor for the EventReceived object.
	 * 
	 * @param action The action that was executed
	 */
	public FsmActionExecuted(final Object action) {
		super("Action executed:" + action);

		this.action = action;

	}

	/**
	 * The Action that was executed.
	 * 
	 * @return The action object
	 */
	public Object getAction() {
		return action;
	}

}
