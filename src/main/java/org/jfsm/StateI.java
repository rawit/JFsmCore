package org.jfsm;

import java.util.List;

import org.jfsm.events.Event;
import org.jfsm.events.FsmPropertyChangeEvent;
import org.jfsm.events.TimerEvent;

/**
 * An interface to a state in the finite state machine.
 */
public interface StateI {

    /**
     * Get the identifier for the state.
     * 
     * @return the id
     */
    public int getIdentifier();

    /**
     * Gets the EntryAction attribute of the State object.
     * 
     *@return The OnEntryActions value
     */
    public List<ActionI> getEntryActions();

    /**
     * Gets the EntryAction attribute of the State object.
     * 
     *@return The OnEntryActions value
     */
    public List<ActionI> getExitActions();

    /**
     * Returns the acceptance status of the state.
     * 
     *@return true for accepting, false otherwise
     */
    public boolean isFinal();

    /**
     * Gets the name of the state.
     * 
     *@return Return the state name.
     */
    public String getName();

    /**
     * Sets the name of the state.
     * 
     * @param name the name
     */
    public void setName(String name);

    /**
     * Set the accepting attribute.
     * 
     *@param flag The new Final value
     */
    public void setFinal(boolean flag);

    /**
     * Gets the Internal actions attribute of the State object.
     * 
     *@return The list of internal transitions
     */
    public List<TransitionI> getInternalTransitions();

    /**
     * Gets the outgoing Transitions of the State object.
     * 
     *@return The outgoing transitions value
     */
    public List<TransitionI> getExternalTransitions();

    /**
     * Set the entry action for the state.
     * 
     *@param act action
     * @param runAtRestart true if the action should be run if the state machine is restartet in this state
     */
    public void addEntryAction(final ActionI act, final boolean runAtRestart);

    /**
     * Set the exit action for the state.
     * 
     *@param act action
     */
    public void addExitAction(final ActionI act);

    /**
     * Add a new internal transition.
     * 
     *@param event The event
     *@param guardCond The guard
     *@param act The action
     */
    public void addInternalTransition(Event event, GuardConditionI guardCond, ActionI act);

    /**
     * Add a new internal transition.
     * 
     *@param event The event
     *@param guardCond The guard
     *@param actions The actions
     *@param toState the 'to' state id
     */
    void addTransition(Event event, GuardConditionI guardCond, List<ActionI> actions, int toState);

    /**
     * Add a new outgoing transition.
     * 
     *@param event The event
     *@param guardCond The guard
     *@param act The action
     *@param to The state to transit to
     */
    public void addTransition(Event event, GuardConditionI guardCond, ActionI act, StateI to);

    /**
     * Get the transitions.
     * 
     * @return the list of transitions
     */
    public List<TransitionI> getTransitions();

    public List<FsmPropertyChangeEvent> getPropertyEvents();

    public List<TimerEvent> getTimerEvents();

    public void addTimerEvent(TimerEvent event);

    public void addPropertyEvent(FsmPropertyChangeEvent event2);

    /**
     * Set the context.
     * 
     *@param fsmContext The FsmContext value
     *@throws JFsmException if some object could not be resolved
     */
    public void setContext(Context fsmContext) throws JFsmException;

    /**
     * Set the xPos.
     * 
     * @param xPos the value
     */
    public void setXPos(int xPos);

    /**
     * Get the xPos.
     * 
     * @return the value
     */
    public int getXPos();

    /**
     * Set the yPos.
     * 
     * @param yPos the value
     */
    public void setYPos(int yPos);

    /**
     * Get the yPos.
     * 
     * @return the value
     */
    public int getYPos();

    /**
     * Get the width.
     * 
     * @return the value
     */
    public int getWidth();

    /**
     * Set the width.
     * 
     * @param width the value
     */
    public void setWidth(int width);

    /**
     * Get the height.
     * 
     * @return the value
     */
    public int getHeight();

    /**
     * Set the height.
     * 
     * @param height the value
     */
    public void setHeight(int height);


}
