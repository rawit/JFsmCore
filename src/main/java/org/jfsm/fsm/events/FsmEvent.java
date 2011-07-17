package org.jfsm.fsm.events;

/**
 * Base class for events that occur in the JFsmEngine.
 */
public class FsmEvent {

    private String message = null;

    /**
     * Constructor for the FsmEvent object.
     * 
     *@param message The message
     */
    protected FsmEvent(final String message) {

        this.message = message;
    }

    /**
     * Return the message.
     * 
     *@return The Message value
     */
    public String getMessage() {

        return message;
    }

}
