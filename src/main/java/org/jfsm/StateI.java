package org.jfsm;

import java.util.List;

import org.jfsm.core.events.Event;
import org.jfsm.core.events.FsmPropertyChangeEvent;
import org.jfsm.core.events.TimerEvent;

/**
 * An interface to a state in the finite state machine.
 */
public interface StateI {

	/**
	 * Get the identifier for the state.
	 * 
	 * @return the id
	 */
	int getIdentifier();

	/**
	 * Gets the EntryAction attribute of the State object.
	 * 
	 * @return The OnEntryActions value
	 */
	List<ActionI> getEntryActions();

	/**
	 * Gets the EntryAction attribute of the State object.
	 * 
	 * @return The OnEntryActions value
	 */
	List<ActionI> getExitActions();

	/**
	 * Returns the acceptance status of the state.
	 * 
	 * @return true for accepting, false otherwise
	 */
	boolean isFinal();

	/**
	 * Gets the name of the state.
	 * 
	 * @return Return the state name.
	 */
	String getName();

	/**
	 * Sets the name of the state.
	 * 
	 * @param name the name
	 */
	void setName(String name);

	/**
	 * Set the accepting attribute.
	 * 
	 * @param flag The new Final value
	 */
	void setFinal(boolean flag);

	/**
	 * Gets the Internal actions attribute of the State object.
	 * 
	 * @return The list of internal transitions
	 */
	List<TransitionI> getInternalTransitions();

	/**
	 * Gets the outgoing Transitions of the State object.
	 * 
	 * @return The outgoing transitions value
	 */
	List<TransitionI> getExternalTransitions();

	/**
	 * Set the entry action for the state.
	 * 
	 * @param act action
	 * @param runAtRestart true if the action should be run if the state machine
	 *        is restartet in this state
	 */
	void addEntryAction(final Object act, final boolean runAtRestart);

	/**
	 * Set the exit action for the state.
	 * 
	 * @param act action
	 */
	void addExitAction(final Object act);

	/**
	 * Add a new internal transition.
	 * 
	 * @param event The event
	 * @param guardCond The guard
	 * @param act The action
	 */
	void addInternalTransition(Event event, Object guardCond, Object act);

	/**
	 * Add a new internal transition.
	 * 
	 * @param event The event
	 * @param guardCond The guard
	 * @param actions The actions
	 * @param toState the 'to' state id
	 */
	void addTransition(Event event, Object guardCond, List<Object> actions, int toState);

	/**
	 * Add a new outgoing transition.
	 * 
	 * @param event The event
	 * @param guardCond The guard
	 * @param act The action
	 * @param to The state to transit to
	 */
	void addTransition(Event event, Object guardCond, Object act, StateI to);

	/**
	 * Get the transitions.
	 * 
	 * @return the list of transitions
	 */
	List<TransitionI> getTransitions();

	List<FsmPropertyChangeEvent> getPropertyEvents();

	List<TimerEvent> getTimerEvents();

	void addTimerEvent(TimerEvent event);

	void addPropertyEvent(FsmPropertyChangeEvent event2);

	/**
	 * Set the xPos.
	 * 
	 * @param xPos the value
	 */
	void setXPos(int xPos);

	/**
	 * Get the xPos.
	 * 
	 * @return the value
	 */
	int getXPos();

	/**
	 * Set the yPos.
	 * 
	 * @param yPos the value
	 */
	void setYPos(int yPos);

	/**
	 * Get the yPos.
	 * 
	 * @return the value
	 */
	int getYPos();

	/**
	 * Get the width.
	 * 
	 * @return the value
	 */
	int getWidth();

	/**
	 * Set the width.
	 * 
	 * @param width the value
	 */
	void setWidth(int width);

	/**
	 * Get the height.
	 * 
	 * @return the value
	 */
	int getHeight();

	/**
	 * Set the height.
	 * 
	 * @param height the value
	 */
	void setHeight(int height);

}
