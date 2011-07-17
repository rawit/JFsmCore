package org.jfsm.core.events;

import org.jfsm.core.JFsmTimerTask;

/**
 * The timer event.
 */
public class TimerEvent extends Event {

	private static final long serialVersionUID = 1L;

	private JFsmTimerTask timerTask;

	/**
	 * Constructor for the TimerEvent object.
	 * 
	 * @param type The event type
	 * @param arguments The event arguments
	 */
	public TimerEvent(final String type, final Object[] arguments) {
		super(type, arguments);
	}

	/**
	 * Sets the TimerTask attribute of the TimerEvent object.
	 * 
	 * @param timerTask The new TimerTask value
	 */
	public void setTimerTask(final JFsmTimerTask timerTask) {
		this.timerTask = timerTask;
		timerTask.start();
	}

	/**
	 * Gets the TimerTask attribute of the TimerEvent object.
	 * 
	 * @return The TimerTask value
	 */
	public JFsmTimerTask getTimerTask() {
		return timerTask;
	}

}
