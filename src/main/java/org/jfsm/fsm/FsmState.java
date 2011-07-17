package org.jfsm.fsm;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.jfsm.ActionI;
import org.jfsm.JFsmException;
import org.jfsm.StateI;
import org.jfsm.TransitionI;
import org.jfsm.basic.JFsmTimerTask;
import org.jfsm.basic.Transition;
import org.jfsm.events.FsmPropertyChangeEvent;
import org.jfsm.events.TimerEvent;

/**
 * A class to represent a state in the FSM.
 */
public class FsmState implements FsmStateI, Serializable {

    private static final long serialVersionUID = 1L;

    private final JFsm fsmRef;

    private static final Logger LOGGER = Logger.getLogger(FsmState.class);

    private StateI state;

    /**
     * Creates a new state with a given name.
     * 
     * @param id the identifier
     * @param name The name of the state
     */
    public FsmState(StateI state, final JFsm jFsm) {
        this.state = state;
        this.fsmRef = jFsm;
    }

    /**
     * Input an event to the state.
     * 
     *@param event The input event
     *@return The next state (this, if no transition took place, or this is an accepting state
     *@throws JFsmException If the event cannot be processed
     */
    public int input(final Object event) throws JFsmException {

        LOGGER.debug("'" + state.getName() + "': input: event = " + event.getClass().getName());

        // Search for match on internal transitions
        Transition transition = (Transition) findFieringTransition(event, state.getInternalTransitions());

        if (transition == null) {
            // Search for match on external transitions
            transition = (Transition) findFieringTransition(event, state.getExternalTransitions());
        }

        if (transition == null) {
            return state.getIdentifier();
        }

        final List<ActionI> actions = transition.getActions();
        if (actions != null && actions.size() > 0) {
            for (final ActionI action : actions) {
                if (action != null) {
                    action.execute(event);
                    action.setHasBeenExecuted(true);
                }
            }
        }

        return transition.getToStateId();
    }

    /**
     * {@inheritDoc}
     */
    public int entering(final Object event) throws JFsmException {

        LOGGER.debug("'" + state.getIdentifier() + "': entering: name = " + state.getName());

        startTimerTasks();
        startPropertyListeners();

        final List<ActionI> entryActions = state.getEntryActions();
        if (entryActions != null && entryActions.size() > 0) {
            for (final ActionI action : entryActions) {
                if (action != null) {
                    action.execute(event);
                    action.setHasBeenExecuted(true);
                }
            }
        }

        final List<TransitionI> transitions = state.getTransitions();
        if (transitions != null) {
            for (final TransitionI transition : transitions) {
                if (transition.getEvent() == null) {
                    final boolean evaluate = evaluate(null, transition);
                    if (evaluate) {
                        return transition.getToStateId();
                    }

                }
            }
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    public void exiting() throws JFsmException {

        LOGGER.debug("'" + state.getIdentifier() + "': exiting: name = " + state.getName());

        cancelTimerTasks();
        cancelPropertyEvents();

        final List<ActionI> exitActions = state.getExitActions();
        if (exitActions != null && exitActions.size() > 0) {
            for (final ActionI action : exitActions) {
                if (action != null) {
                    action.execute(null);
                    action.setHasBeenExecuted(true);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void stop() {
        cancelTimerTasks();
        cancelPropertyEvents();
    }

    public StateI getState() {
        return state;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "State: id = " + state.getIdentifier() + ", name = '" + state.getName() + "'";
    }

    /**
     * {@inheritDoc}
     */
    protected void startTimerTasks() {

        LOGGER.debug("'" + state.getName() + "': startTimerTasks: timerEvents = " + state.getTimerEvents());

        if (state.getTimerEvents() != null) {

            for (final TimerEvent timerEvent : state.getTimerEvents()) {
                LOGGER.debug("'" + state.getName() + "': startTimerTasks: starting timer = " + timerEvent);

                final JFsmTimerTask timerTask = new JFsmTimerTask(timerEvent);
                timerTask.setFsm(fsmRef);
                timerEvent.setTimerTask(timerTask);

            }
        }
    }

    private boolean evaluate(final Object event, TransitionI transition) {
    
        LOGGER.debug("event = " + (event == null ? "null" : event.getClass().getName()));
        // Check for event type equality, including null values
        if (transition.getEvent() == null) {
            return true;
        }
    
        if (event != null && !event.getClass().getName().equals(transition.getEvent().getType())) {
            return false;
        }
    
        LOGGER.debug("event type matched = " + event.getClass().getName());
    
        if (transition.getGuardCondition() == null) {
            LOGGER.debug("guard == null");
            return true;
        } else {
            LOGGER.debug("expression: " + transition.getGuardCondition().getExpression());
            return transition.getGuardCondition().evaluate(event);
        }
    
    }

    private void startPropertyListeners() {

        final List<FsmPropertyChangeEvent> propertyEvents = state.getPropertyEvents();

        if (propertyEvents != null) {
            LOGGER.debug("'" + state.getName() + "': startPropertyListeners: propertyEvents = " + propertyEvents);

            for (final FsmPropertyChangeEvent propertyEvent : propertyEvents) {
                LOGGER.debug("'" + state.getName() + "': startTimerTasks: starting listener = " + propertyEvent);
                propertyEvent.setJFsm(this.fsmRef);
                propertyEvent.start();

            }
        }

    }

    private void cancelPropertyEvents() {

        if (state.getPropertyEvents() != null) {

            for (final FsmPropertyChangeEvent propertyEvent : state.getPropertyEvents()) {
                LOGGER.debug("'" + state.getName() + "': cancelPropertyEvents: properety event = " + propertyEvent);
                propertyEvent.stop();
            }

        }

    }

    /**
     * Cancel timer tasks.
     */
    private void cancelTimerTasks() {

        final List<TimerEvent> timerEvents = state.getTimerEvents();
        if (timerEvents != null) {

            for (final TimerEvent timerEvent : timerEvents) {
                LOGGER.debug("'" + state.getName() + "': cancelTimerTasks: timer event = " + timerEvent);
                timerEvent.getTimerTask().cancel();
            }

        }
    }

    /**
     * Search all transitions for the current state and see if it matches (fires) for the submitted event.
     * 
     *@param receivedEvent The event that was received
     *@param transitions a list of transitions to inspect
     *@return The Transition if it fired, else null
     */
    private TransitionI findFieringTransition(final Object receivedEvent, final List<TransitionI> transitions) {

        LOGGER.debug("'" + state.getIdentifier() + "': findFieringTransition: receivedEvent = "
                        + receivedEvent.getClass().getName());

        if (transitions == null) {
            return null;
        }

        for (final TransitionI transition : transitions) {

            if (evaluate(receivedEvent, transition)) {
                LOGGER.debug("'" + state.getIdentifier() + "': findFieringTransition: transition fired= " + transition);
                return transition;
            }
            LOGGER.debug("'" + state.getName() + "': findFieringTransition: transition not fired = " + transition);
        }

        return null;
    }

}
