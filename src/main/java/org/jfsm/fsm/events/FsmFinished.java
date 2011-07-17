package org.jfsm.fsm.events;

import org.jfsm.JFsmException;
import org.jfsm.StateI;

/**
 * The JFsmEngine has reached a final (accepting) state and is terminating the execution of the state machine.
 */
public class FsmFinished extends FsmEvent {

    /**
     * Execution was stopped because the "stop" method was called.
     */
    public static final int FSM_CANCELLED = 0;

    /**
     * Execution was stopped because a final (accepting) state was reached.
     */
    public static final int FSM_REACHED_FINAL_STATE = 1;

    /**
     * Execution was stopped because a fatal error occurred.
     */
    public static final int FSM_IN_ERROR = 2;

    private final StateI eState;

    private final int reason;


    /**
     * Constructor for the FsmEventStateChanged object.
     * 
     *@param endState The state that the FSM ended in
     *@param reason The reason for stopping execution. One of FSM_CANCELLED, FSM_REACHED_FINAL_STATE or FSM_IN_ERROR
     *@param fsmExc The exception if reason was FSM_IN_ERROR. Otherwise ignored
     */
    public FsmFinished(final StateI endState, final int reason, final JFsmException fsmExc) {
        super("Execution stopped in state = " + endState.getName() + ", reason = " + encodeReason(reason, fsmExc));

        this.eState = endState;
        this.reason = reason;

        if (reason == FSM_IN_ERROR) {
            // this.fsmExc = fsmExc;
        }
    }

    /**
     * Gets the FromState attribute of the FsmEventStateChanged object.
     * 
     *@return The FromState value
     */
    public StateI getEndState() {

        return eState;
    }

    /**
     * Gets the Reason attribute of the FsmFinished object.
     * 
     *@return The Reason value
     */
    public int getReason() {
        return reason;
    }

    /**
     * Convert the reason attribute to a human readable string.
     * 
     *@param reason The reason
     *@param fsmExc exception, in case of FSM_IN_ERROR
     *@return The encoded string
     */
    private static String encodeReason(final int reason, final JFsmException fsmExc) {

        switch (reason) {

        case FSM_CANCELLED:
            return "Cancelled";
        case FSM_REACHED_FINAL_STATE:
            return "Reached final state";
        case FSM_IN_ERROR:
            if (fsmExc == null) {
                return "Error = <unknown>";
            } else {
                return "Error = " + fsmExc.getMessage();
            }
        default:
            return "Unknown reason = " + reason;
        }

    }

}
