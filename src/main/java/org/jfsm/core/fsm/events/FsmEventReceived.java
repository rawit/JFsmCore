package org.jfsm.core.fsm.events;

/**
 * JFsmEngine has received an Event. The event may be retrieved using the
 * getEvent() method
 */
public class FsmEventReceived extends FsmEvent {

	private final Object event;

	/**
	 * Constructor for the EventReceived object.
	 * 
	 * @param event The event that was received
	 */
	public FsmEventReceived(final Object event) {
		super("Event received:" + event);

		this.event = event;

	}

	/**
	 * The Event that was received.
	 * 
	 * @return The event object
	 */
	public Object getEvent() {
		return event;
	}

}
