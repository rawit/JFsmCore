package org.jfsm.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jfsm.ActionI;
import org.jfsm.GuardConditionI;
import org.jfsm.StateI;
import org.jfsm.TransitionI;
import org.jfsm.core.events.Event;
import org.jfsm.core.pojo.PojoGuardAdapter;

/**
 * Represents a transition between two states in the state machine.
 */
public class Transition implements TransitionI, Serializable {

    private static final long serialVersionUID = 1L;

    /** The from state. */
    private final StateI fromState;

    /** The to state id. */
    private final int toStateId;

    /** The event. */
    private final Event event;

    /** The guard condition. */
    private GuardConditionI guardCondition = null;

    /** The actions. */
    private final List<ActionI> actions = new ArrayList<ActionI>();

    /**
     * Creates a Transition.
     * 
     * @param fromState The state to connect from
     * @param toState The state to connect to
     */
    public Transition(final StateI fromState, final StateI toState) {
        this(fromState, null, null, null, toState);

    }

    /**
     * Creates a connection entry.
     * 
     * @param from the state the connection starts from
     * @param event The event
     * @param guardCond The guard condition
     * @param act the action
     * @param to the state the connection goes to
     */
    public Transition(final StateI from, final Event event, final Object guardCond, final ActionI act, final StateI to) {

        if (from == null) {
            throw new IllegalArgumentException("Argument \"from\" == null");
        }

        if (to == null) {
            throw new IllegalArgumentException("Argument \"to\" == null");
        }

        this.fromState = from;
        this.event = event;
        if (guardCond != null) {
            if (guardCond instanceof GuardConditionI) {
                this.guardCondition = (GuardConditionI) guardCond;
            } else {
                this.guardCondition = new PojoGuardAdapter(guardCond);
            }
        }
        this.actions.add(act);
        this.toStateId = to.getIdentifier();

    }

    /**
     * Creates a connection entry.
     * 
     * @param from the state the connection starts from
     * @param event The event
     * @param guardCond The guard condition
     * @param actions the actions
     * @param to the state the connection goes to
     */
    public Transition(final StateI from, final Event event, final Object guardCond, final List<ActionI> actions,
            final int to) {

        if (from == null) {
            throw new IllegalArgumentException("Argument \"from\" == null");
        }

        this.fromState = from;
        this.event = event;
        if (guardCond != null) {
            if (guardCond instanceof GuardConditionI) {
                this.guardCondition = (GuardConditionI) guardCond;
            } else {
                this.guardCondition = new PojoGuardAdapter(guardCond);
            }
            for (final ActionI action : actions) {
                this.actions.add(action);
            }
        }
        this.toStateId = to;

    }

    /**
     * Gets the CurrentState attribute of the Transition object.
     * 
     * @return The CurrentState value
     */
    public StateI getFromState() {
        return this.fromState;
    }

    /**
     * Gets the Guard condition attribute of the Transition object.
     * 
     * @return The guard condition value
     */
    public GuardConditionI getGuardCondition() {
        return this.guardCondition;
    }

    /**
     * Gets the Action attribute of the Transition object.
     * 
     * @return The Action value
     */
    public List<ActionI> getActions() {
        return this.actions;
    }

    /**
     * Gets the Event type attribute of the Transition object.
     * 
     * @return The Event value
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Gets the NextState attribute of the Transition object.
     * 
     * @return The NextState value
     */
    public int getToStateId() {
        return this.toStateId;
    }

    /**
     * Evaluate the event and guard against the received event.
     * 
     * @param pEvent The event received
     * @return true if match, otherwise false
     */
    public boolean evaluate(final Object pEvent) {

        // Check for event type equality, including null values
        if (equals(pEvent, getEvent())) {
            return false;
        }

        if (getGuardCondition() == null) {
            return true;
        } else {
            return getGuardCondition().evaluate(pEvent);
        }

    }

    private boolean equals(final Object event1, final Event event2) {

        if (event1 == null && event2 != null) {
            return true;
        }

        if (event1 != null && event2 == null) {
            return true;
        }

        final String name = event1.getClass().getName();
        final String type = event2.getType();
        return !name.equals(type);

    }

    /**
     * Return a string version of this object.
     * 
     * @return The string
     */
    @Override
    public String toString() {

        final StringBuffer strb = new StringBuffer();

        final String eventText = getEventText(getEvent());
        final String guardText = getGuardText();
        final String actionText = getActionText();

        strb.append(eventText + guardText + actionText);

        return strb.toString();
    }

    /**
     * Get the action text.
     * 
     * @return the text
     */
    private String getActionText() {
        final StringBuffer strBuf = new StringBuffer();
        strBuf.append(" / [");
        for (final ActionI action : this.actions) {
            if (action != null) {
                strBuf.append(", ");
                strBuf.append(action.getExpression());
            }
        }
        strBuf.append(" ]");

        return strBuf.toString();

    }

    /**
     * Get the guard text.
     * 
     * @return the text
     */
    private String getGuardText() {
        if (getGuardCondition() == null) {
            return "";
        }
        return " [ " + getGuardCondition().getExpression() + " ] ";
    }

    /**
     * Get the event text.
     * 
     * @param pEvent the event
     * @return the text
     */
    private String getEventText(final Event pEvent) {
        if (pEvent == null) {
            return "";
        }
        final String eventType = pEvent.getType();
        final String type = JFsmUtilities.removePackagePrefix(eventType);
        return "" + type;
    }

}
