package org.jfsm.core.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfsm.Context;
import org.jfsm.JFsmException;
import org.jfsm.JFsmModelI;
import org.jfsm.StateI;
import org.jfsm.core.fsm.events.FsmError;
import org.jfsm.core.fsm.events.FsmEvent;
import org.jfsm.core.fsm.events.FsmEventReceived;
import org.jfsm.core.fsm.events.FsmEventStateChanged;
import org.jfsm.core.fsm.events.FsmFinished;
import org.jfsm.core.fsm.events.FsmStarted;

/**
 * A State Machine implementation. Takes a state machine model (JFsmModelI) and
 * extracts the state and transitions from it for execution
 */
public class JFsm {

	private final Context fsmContext;

	private final JFsmModelI fsmModel;

	private List<FsmEventListener> eventListeners;

	private FsmStateI currentState;

	private final FsmStateI initial;

	private final Map<Integer, FsmStateI> states = new HashMap<Integer, FsmStateI>();

	private boolean running = false;

	/**
	 * Constructor for the JFsm object.
	 * 
	 * @param fsmModel The JFsm model
	 * @throws JFsmException If the arguments are invalid
	 */
	public JFsm(final JFsmModelI fsmModel) throws JFsmException {
		this(null, fsmModel);
	}

	/**
	 * Constructor for the JFsm object.
	 * 
	 * @param fsmCon The FSM context
	 * @param fsmModel The JFsm model
	 * @throws JFsmException If the arguments are invalid
	 */
	public JFsm(final Context fsmCon, final JFsmModelI fsmModel) throws JFsmException {

		if (fsmModel == null) {
			throw new IllegalArgumentException("JFsm: No JFsm model");
		}

		this.fsmContext = fsmCon;
		this.fsmModel = fsmModel;

		if ((fsmModel.getStates() == null) || fsmModel.getStates().isEmpty()) {
			throw new IllegalArgumentException("JFsm: No states in Fsm model");
		}

		addStates(fsmModel);

		// fsmModel.pack();

		initial = states.get(fsmModel.getInitial().getIdentifier());

		if (initial == null) {
			throw new IllegalArgumentException("JFsm: No initial state in Fsm model");
		}

		this.setContext(fsmContext);

	}

	/**
	 * Gets the Current State attribute of the JFsm object.
	 * 
	 * @return The Current State value
	 */
	public StateI getCurrentState() {
		return currentState.getState();
	}

	/**
	 * True if the state machine is running.
	 * 
	 * @return The Running value
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Gets the Model.
	 * 
	 * @return The current model value
	 */
	public JFsmModelI getModel() {
		return this.fsmModel;
	}

	/**
	 * Adds a change event listener.
	 * 
	 * @param listener The event listener
	 */
	public void addChangeListener(final FsmEventListener listener) {

		if (this.eventListeners == null) {
			this.eventListeners = new ArrayList<FsmEventListener>();
		}

		if (!eventListeners.contains(listener)) {
			eventListeners.add(listener);
		}
	}

	/**
	 * Remove an event listener.
	 * 
	 * @param listener The listener to remove
	 */
	public void removeChangeListener(final FsmEventListener listener) {

		if (this.eventListeners == null) {
			return;
		}

		eventListeners.remove(listener);

	}

	/**
	 * Input an event to the state machine.
	 * 
	 * @param event The event
	 * @throws JFsmException If the input event was rejected
	 */
	public synchronized void input(final Object event) throws JFsmException {

		if (!running) {
			final JFsmException fsme = new JFsmException("Not running. Call start() first!");
			fireFsmEvent(new FsmError(fsme));
			throw fsme;
		}

		fireFsmEvent(new FsmEventReceived(event));

		if (currentState == null) {
			final JFsmException fsme = new JFsmException("No current state!");
			fireFsmEvent(new FsmError(fsme));
			throw fsme;
		}

		final int nextStateId = this.currentState.input(event);
		if (nextStateId == this.currentState.getState().getIdentifier()) {
			return;
		}

		final FsmStateI nextState = this.states.get(nextStateId);
		this.currentState.exiting();
		nextState.entering(event);

		final FsmStateI oldState = this.currentState;
		this.currentState = nextState;

		if (nextState.getState().isFinal()) {
			fireFsmEvent(new FsmFinished(nextState.getState(), FsmFinished.FSM_REACHED_FINAL_STATE, null));
			cleanup();
			return;
		}

		fireFsmEvent(new FsmEventStateChanged(oldState.getState(), this.currentState.getState(), event, null));

	}

	/**
	 * Start the JFsm.
	 * 
	 * @throws JFsmException If the FSM could not be started
	 */
	public synchronized void start() throws JFsmException {

		if (running) {
			return;
		}

		running = true;

		this.currentState = initial;

		fireFsmEvent(new FsmStarted(fsmModel.getName(), currentState.getState()));

		final int nextStateId = currentState.entering(null);
		if ((nextStateId == -1) || (nextStateId == this.currentState.getState().getIdentifier())) {
			return;
		}

		final FsmStateI nextState = this.states.get(nextStateId);
		this.currentState.exiting();
		nextState.entering(null);

		final FsmStateI oldState = this.currentState;
		this.currentState = nextState;

		if (nextState.getState().isFinal()) {
			fireFsmEvent(new FsmFinished(nextState.getState(), FsmFinished.FSM_REACHED_FINAL_STATE, null));
			cleanup();
			return;
		}

		fireFsmEvent(new FsmEventStateChanged(oldState.getState(), this.currentState.getState(), null, null));

	}

	/**
	 * Stop the JFsm.
	 */
	public synchronized void stop() {

		if (running) {
			fireFsmEvent(new FsmFinished(currentState.getState(), FsmFinished.FSM_CANCELLED, null));
		}

		cleanup();

	}

	/**
	 * Return a string version of this object.
	 * 
	 * @return The formatted string
	 */
	@Override
	public String toString() {

		final StringBuffer strb = new StringBuffer();
		strb.append("JFsm = {");
		strb.append("\tModel Name = ");
		strb.append(fsmModel.getName());
		strb.append("\tCurrent state = ");
		strb.append(currentState);
		strb.append("}");
		return strb.toString();
	}

	private void addStates(final JFsmModelI fsmModel) {

		final Map<Integer, StateI> states = fsmModel.getStates();

		for (final Integer stateId : states.keySet()) {
			this.states.put(stateId, new FsmState(states.get(stateId), this));
		}
	}

	/**
	 * Set the context in all states.
	 * 
	 * @param fsmContext The new Context value
	 * @throws JFsmException If resolving the objects failed
	 */
	private void setContext(final Context fsmContext) throws JFsmException {

		if (states == null) {
			return;
		}

		for (final FsmStateI state : states.values()) {
			state.getState().setContext(fsmContext);
		}
	}

	/**
	 * Clean up the state machine, stop all states.
	 */
	private void cleanup() {

		running = false;

		if (states != null) {

			for (final FsmStateI state : states.values()) {
				state.stop();
			}

		}
	}

	/**
	 * Notify all registered event listeners that an event has occurred.
	 * 
	 * @param event The event
	 */
	protected void fireFsmEvent(final FsmEvent event) {

		if (eventListeners == null) {
			return;
		}

		for (final FsmEventListener listener : eventListeners) {
			listener.onEvent(event);

		}

	}

}
