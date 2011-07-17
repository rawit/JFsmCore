package org.jfsm.core.fsm.events;

import org.jfsm.StateI;

/**
 * The JFsmEngine has changed its state. If the event was external, the eventType attribute will be set to
 * EVENT_TYPE_EXTERNAL and the event received can be obtained from the getEvent() method. If the event was internal,
 * the getEvent() method will return null and the the event_type will be set to the proper type of internal event
 */
public class FsmEventStateChanged extends FsmEvent {

    /**
     * Constant for external event type.
     */
    public static final int EVENT_TYPE_EXTERNAL = 0;

    /**
     * Constant for "on entry" event type.
     */
    public static final int EVENT_TYPE_ENTRY = 1;

    /**
     * Constant for "on exit" event type.
     */
    public static final int EVENT_TYPE_EXIT = 2;

    private final StateI fromState, toState;

    private final Object action;

    private final Object event;


    /**
     * Constructor for the FsmEventStateChanged object.
     * 
     *@param fromState The from state
     *@param toState The to state
     *@param event The event that triggered the state change
     *@param action Any action that was executed
     */
    public FsmEventStateChanged(final StateI fromState, final StateI toState, final Object event, final Object action) {
        super("State changed from state '" + fromState.getName() + "' to state '" + toState.getName() + "'.");

        this.fromState = fromState;
        this.toState = toState;
        this.event = event;
        this.action = action;

    }

    /**
     * Gets the FromState attribute of the FsmEventStateChanged object.
     * 
     *@return The FromState value
     */
    public StateI getFromState() {
        return fromState;
    }

    /**
     * Gets the ToState attribute of the FsmEventStateChanged object.
     * 
     *@return The ToState value
     */
    public StateI getToState() {
        return toState;
    }

    /**
     * Gets the Action that triggered the state transition.
     * 
     *@return The Action value
     */
    public Object getAction() {
        return action;
    }

    /**
     * Gets the Event that triggered the state transition.
     * 
     *@return The Event value
     */
    public Object getEvent() {
        return event;
    }

}
