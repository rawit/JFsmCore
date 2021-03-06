package org.jfsm.core.fsm;

import org.jfsm.core.fsm.events.FsmEvent;

/**
 * The JFsmEngine event listener interface. Must be implemented by user classes
 * to receive FSM events.
 */
public interface FsmEventListener {

	/**
	 * The method that delivers FSM events.
	 * 
	 * @param event
	 *            The FSM event
	 */
	public abstract void onEvent(FsmEvent event);

}
