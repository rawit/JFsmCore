package org.jfsm.basic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfsm.ActionI;
import org.jfsm.Context;
import org.jfsm.GuardConditionI;
import org.jfsm.JFsmException;
import org.jfsm.StateI;
import org.jfsm.TransitionI;
import org.jfsm.events.Event;
import org.jfsm.events.FsmPropertyChangeEvent;
import org.jfsm.events.TimerEvent;
import org.jfsm.fsm.JFsm;

/**
 * A class to represent a state in the FSM.
 */
public class State implements StateI, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_WIDTH = 200;

    private static final int DEFAULT_HEIGHT = 100;

    /** Standard action type, "entry". */
    private final Map<ActionI, Boolean> entryActions = new HashMap<ActionI, Boolean>();

    /** Standard action type, "exit". */
    private final List<ActionI> exitActions = new ArrayList<ActionI>();

    /** List of external transitions for this state. */
    private List<TransitionI> externalTransitions;

    /** List of internal transitions for this state. */
    private List<TransitionI> internalTransitions;

    // List of timer events that this state should manage
    private List<TimerEvent> timerEvents;

    private List<FsmPropertyChangeEvent> propertyEvents;

    // the acceptance status of the state
    private boolean finalState = false;

    /** A name for state (optional). */
    private String name;

    // A reference to the JFsm. Used by the timer task
    // TODO This introduces an undesirable circular dependency
    // that should be avoided!!
    private JFsm fsmRef;

    private int xPos;

    private int yPos;

    private int width = DEFAULT_WIDTH;

    private int height = DEFAULT_HEIGHT;

    /** Sequential numbering if states. */
    private final int identifier;

    private final Logger logger = Logger.getLogger(State.class);

    /**
     * Creates a new state with a given name.
     * 
     * @param id the identifier
     * @param name The name of the state
     */
    public State(final int id, final String name) {
        this(id);
        this.name = name;
    }

    /**
     * Creates a new state with a given id.
     * 
     * @param identifier the state id
     */
    public State(final int identifier) {
        this.identifier = identifier;
        this.name = String.valueOf(identifier);
    }

    /** {@inheritDoc} */
    public int getIdentifier() {
        return this.identifier;
    }

    /**
     * {@inheritDoc}
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public void addEntryAction(final ActionI act, final boolean runAtRestart) {
        this.entryActions.put(act, runAtRestart);
    }

    /**
     * Set the new exit action to the state.
     * 
     *@param act action
     */
    public void addExitAction(final ActionI act) {
        this.exitActions.add(act);
    }

    /**
     * Makes the state accepting. If the state is already accepting, this will have no effect. The default is for the
     * state to be rejecting.
     * 
     *@param flag The new Final value
     */
    public void setFinal(final boolean flag) {
        finalState = flag;
    }

    /**
     * {@inheritDoc}
     */
    public void setContext(final Context fsmContext) throws JFsmException {

        if (this.internalTransitions != null) {
            for (final TransitionI trans : internalTransitions) {
                trans.setContext(fsmContext);
            }
        }

        if (this.externalTransitions != null) {
            for (final TransitionI trans : externalTransitions) {
                trans.setContext(fsmContext);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<TransitionI> getInternalTransitions() {
        return internalTransitions;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isFinal() {
        return finalState;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    public List<TransitionI> getExternalTransitions() {
        return externalTransitions;
    }

    /**
     * {@inheritDoc}
     */
    public List<ActionI> getEntryActions() {
        return new ArrayList<ActionI>(this.entryActions.keySet());
    }

    /**
     * {@inheritDoc}
     */
    public List<ActionI> getExitActions() {
        return this.exitActions;
    }

    /**
     * {@inheritDoc}
     */
    public void addInternalTransition(final Event event, final GuardConditionI guardCond, final ActionI act) {

        logger.debug("'" + this.getName() + "': addInternalTransition:  event = " + event + ", gaurd cond = "
                        + guardCond + ", action = " + act);

        final Transition transition = new Transition(this, event, guardCond, act, this);

        if (internalTransitions == null) {
            internalTransitions = new ArrayList<TransitionI>();
        }

        internalTransitions.add(transition);

        checkForTimerEvent(transition.getEvent());
        checkForPropertyChangeEvent(transition.getEvent());

    }

    /**
     * {@inheritDoc}
     */
    public void addTransition(final Event event, final GuardConditionI guardCond, final ActionI act,
                    final StateI toState) {

        addTransition(new Transition(this, event, guardCond, act, toState));

    }

    /**
     * Add a new external transition.
     * 
     *@param event The event
     *@param guardCond The guard condition
     *@param actions The actions
     *@param toState The state to transfer to
     */
    public void addTransition(final Event event, final GuardConditionI guardCond, final List<ActionI> actions,
                    final int toState) {

        addTransition(new Transition(this, event, guardCond, actions, toState));

    }

    /**
     * {@inheritDoc}
     */
    public List<TransitionI> getTransitions() {
        return this.externalTransitions;
    }

    public void addPropertyEvent(final FsmPropertyChangeEvent event) {
        this.propertyEvents.add(event);
        
    }

    public void addTimerEvent(final TimerEvent event) {
        this.timerEvents.add(event);
    }

    public List<FsmPropertyChangeEvent> getPropertyEvents() {
        return this.propertyEvents;
    }

    public List<TimerEvent> getTimerEvents() {
        return timerEvents;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "State: id = " + getIdentifier() + ", name = '" + getName() + "'";
    }

    /**
     * {@inheritDoc}
     */
    public int getXPos() {
        return this.xPos;
    }

    /**
     * {@inheritDoc}
     */
    public void setXPos(final int pXpos) {
        this.xPos = pXpos;
    }

    /**
     * {@inheritDoc}
     */
    public int getYPos() {
        return this.yPos;
    }

    /**
     * {@inheritDoc}
     */
    public void setYPos(final int pYpos) {
        this.yPos = pYpos;
    }

    /**
     * {@inheritDoc}
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * {@inheritDoc}
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * {@inheritDoc}
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * {@inheritDoc}
     */
    public void setHeight(final int height) {
        this.height = height;
    }

    /**
     * Add a new external transition.
     * 
     *@param transition The feature to be added to the Transition attribute
     */
    private void addTransition(final Transition transition) {

        logger.debug("'" + this.getName() + "': addTransition:  transition = " + transition);

        if (transition == null) {
            return;
        }

        if (externalTransitions == null) {
            externalTransitions = new ArrayList<TransitionI>();
        }

        externalTransitions.add(transition);

        checkForTimerEvent(transition.getEvent());
        checkForPropertyChangeEvent(transition.getEvent());

    }

    /**
     * If the event in the submitted transition is a timer event, add the event to the list of timer events. If this is
     * a timer event, add it to the internal list, ready to be scheduled once the state is entered
     * 
     *@param event The event
     */
    private void checkForTimerEvent(final Event event) {

        logger.debug("'" + this.getName() + "': checkForTimerEvent:  event = " + event);

        if (event instanceof TimerEvent) {

            if (timerEvents == null) {
                timerEvents = new ArrayList<TimerEvent>();
            }

            timerEvents.add((TimerEvent) event);

        }
    }

    /**
     * If the event in the submitted transition is a property changed event, add the event to the list of property
     * events.
     * 
     *@param event The event
     */
    private void checkForPropertyChangeEvent(final Event event) {

        logger.debug("'" + this.getName() + "': checkForPropertyChangeEvent:  event = " + event);

        if (event instanceof FsmPropertyChangeEvent) {

            if (propertyEvents == null) {
                propertyEvents = new ArrayList<FsmPropertyChangeEvent>();
            }

            final FsmPropertyChangeEvent event2 = (FsmPropertyChangeEvent) event;
            event2.setJFsm(this.fsmRef);
            propertyEvents.add(event2);

        }
    }

}
