package org.jfsm.core.events;

import org.jfsm.core.JFsmUtilities;

/**
 * Models the Timer event for repeated fixed-delay execution, beginning at the
 * specified time.
 */
public class After extends TimerEvent {

	private static final long serialVersionUID = 1L;

	private final long delay;

	private final boolean repeat;

	/**
	 * Constructor for the After object.
	 * 
	 * @param delay
	 *            The delay to wait in milliseconds
	 * @param repeat
	 *            true if the event should be repeating
	 */
	public After(final long delay, final boolean repeat) {

		super(After.class.getName(), new Object[] { new Long(delay) });

		this.delay = delay;
		this.repeat = repeat;

	}

	/**
	 * Gets the Delay attribute of the After object.
	 * 
	 * @return The Delay value
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * Gets the Delay attribute of the After object.
	 * 
	 * @return The Delay value
	 */
	public boolean getRepeat() {
		return repeat;
	}

	/**
	 * Return a string version of this object.
	 * 
	 * @return The string
	 */
	@Override
	public String toString() {
		return JFsmUtilities.removePackagePrefix(this.getType()) + "( " + delay + "(ms) )";
	}

}
