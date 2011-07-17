package org.jfsm;

import java.util.List;

import org.jfsm.events.Event;

/**
 * Interface for a transition between two states in the state machine. A transitions has attributes for to the two
 * states it is a transition between, the event type, the guard condition and the action that will be executed when the
 * transition fires
 */
public interface TransitionI {

    /**
     * Gets the GuardConditionI attribute of the TransitionI object.
     * 
     *@return The GuardConditionI value
     */
    public GuardConditionI getGuardCondition();

    /**
     * Resolve all objects according to submitted Context.
     * 
     *@param fsmContext The FsmContext value
     *@throws JFsmException if some object could not be resolved
     */
    public void setContext(Context fsmContext) throws JFsmException;

    /**
     * Gets the Event type attribute of the TransitionI object.
     * 
     *@return The Event value
     */
    public Event getEvent();

    /**
     * Gets the ActionI attribute of the TransitionI object.
     * 
     *@return The ActionI value
     */
    public List<ActionI> getActions();

    /**
     * Gets the "from state" attribute of the TransitionI object.
     * 
     *@return The "from state" value
     */
    public StateI getFromState();

    /**
     * Gets the "to state" attribute of the TransitionI object.
     * 
     *@return The "to State" value
     */
    public int getToStateId();

}
