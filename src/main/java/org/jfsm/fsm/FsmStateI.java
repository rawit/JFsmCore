package org.jfsm.fsm;

import org.jfsm.JFsmException;
import org.jfsm.StateI;

/**
 * An interface to a state in the finite state machine.
 */
public interface FsmStateI {

    /**
     * Input an event to the state machine.
     * 
     *@param event The input event
     *@return The result of performing the action, if the event evaluated to true. Else, return null
     *@throws JFsmException in case an error occurred
     */
    public int input(Object event) throws JFsmException;

    /**
     * Should be called when the state is entered.
     * 
     * @param event the event
     * @return the id for the new state
     * 
     * @throws JFsmException If an error occurs
     */
    public int entering(Object event) throws JFsmException;

    /**
     * Should be called when the state is exited.
     * 
     *@throws JFsmException If an error occurs
     */
    public void exiting() throws JFsmException;

    /**
     * Stop this state.
     */
    public void stop();

    public StateI getState();

}
