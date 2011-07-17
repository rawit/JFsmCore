package org.jfsm.core.fsm.events;

import org.jfsm.JFsmException;

/**
 * This event is emitted when an error occurred in the FSM.
 */
public class FsmError extends FsmEvent {

	private final JFsmException fsme;

	/**
	 * Constructor for the EventReceived object.
	 * 
	 * @param fsme The exception that was thrown
	 */
	public FsmError(final JFsmException fsme) {
		super("Fsme Error :" + fsme);

		this.fsme = fsme;

	}

	/**
	 * The Action that was executed.
	 * 
	 * @return The action object
	 */
	public JFsmException getFsmException() {
		return fsme;
	}

}
