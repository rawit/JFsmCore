package org.jfsm.core.fsm.events;

import org.jfsm.StateI;

/**
 * The JFsmEngine has started.
 */
public class FsmStarted extends FsmEvent {

    private final StateI startState;

    /**
     * Creates a new instance of FsmStarted object.
     * 
     *@param fsmDefName The JFsm definition
     *@param startState The start state
     */
    public FsmStarted(final String fsmDefName, final StateI startState) {
        super("Execution of Fsm definition" + fsmDefName + " started in state " + startState.getName());
        this.startState = startState;
    }

    /**
     * Gets the StartState attribute of the FsmStarted object.
     * 
     *@return The StartState value
     */
    public StateI getStartState() {
        return startState;
    }
}
