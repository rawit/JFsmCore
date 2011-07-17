package org.jfsm.events;

import org.jfsm.basic.JFsmUtilities;

/**
 * Models an event that the FSM may receive.
 */
public class Event implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private final String type;

    private final Object[] arguments;

    /**
     * Constructor for the Event object.
     * 
     *@param type The event type
     *@param arguments The arguments (may be null)
     */
    public Event(final Class<?> type, final Object[] arguments) {

        if (type == null) {
            throw new IllegalArgumentException("Argument \"type\" is null");
        }

        this.type = type.getName();
        this.arguments = arguments;

    }

    /**
     * Constructor for the Event object.
     * 
     *@param type The event type
     */
    public Event(final Class<?> type) {
        this(type, null);
    }

    /**
     * Constructor for the Event object.
     * 
     *@param type The event type
     */
    public Event(final String type) {
        this(type, null);

    }

    /**
     * Constructor for the Event object.
     * 
     *@param type The event type
     *@param arguments The arguments (may be null)
     */
    public Event(final String type, final Object[] arguments) {
        if (type == null) {
            throw new IllegalArgumentException("Argument \"type\" is null");
        }

        this.type = type;
        this.arguments = arguments;

    }

    /**
     * Gets the Type attribute of the Event object.
     * 
     *@return The Type value
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the Arguments attribute of the Event object.
     * 
     *@return The Arguments value
     */
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * Check if this is a Entry event.
     * 
     *@return True if internal, otherwise false
     */
    public boolean isEntryEvent() {

        if (getType().equals(org.jfsm.basic.Entry.class.getName())) {
            return true;
        }

        return false;
    }

    /**
     * Check if this is a Exit event.
     * 
     *@return True if internal, otherwise false
     */
    public boolean isExitEvent() {

        if (getType().equals(org.jfsm.basic.Exit.class.getName())) {
            return true;
        }

        return false;

    }

    /**
     * Convert this object to a string.
     * 
     *@return The converted string
     */
    public String toString() {

        final StringBuffer strB = new StringBuffer();

        strB.append("" + JFsmUtilities.removePackagePrefix(type));

        if (arguments != null) {
            strB.append("( " + arguments + " )");
        }

        return strB.toString();
    }
}
