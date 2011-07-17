package org.jfsm.core.events;

import java.util.Date;

import org.jfsm.core.JFsmUtilities;

/**
 * Models a Timer event for execution at a specified time.
 */
public class When extends TimerEvent {

    private static final long serialVersionUID = 1L;

    private final Date time;

    /**
     * Constructor for the When object.
     * 
     *@param time The time at which to fire the event
     */
    public When(final Date time) {
        super(When.class.getName(), new Object[] { time });

        this.time = time;

    }

    /**
     * Gets the Time attribute of the When object.
     * 
     *@return The Time value
     */
    public Date getTime() {
        return time;
    }

    /**
     * Return a string version of this object.
     * 
     *@return The string
     */
    public String toString() {

        return JFsmUtilities.removePackagePrefix(this.getType()) + "( " + time + " )";
    }

}
