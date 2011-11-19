package org.jfsm.core;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfsm.JFsmException;
import org.jfsm.JFsmModelI;
import org.jfsm.StateI;
import org.jfsm.TransitionI;
import org.jfsm.core.events.Event;

/**
 * Contains the elements that make up a state machine: - one or more states - a
 * specified start state.
 * 
 */
public class JFsmModel implements JFsmModelI {

	private static final Logger LOGGER = Logger.getLogger(JFsmModel.class);

	private String modelName;

	/** The states map. */
	private Map<Integer, StateI> states = new HashMap<Integer, StateI>();

	private State startState;

	/**
	 * Sets the start state attribute.
	 * 
	 * @param ts The new start state
	 */
	public void setInitial(final StateI ts) {
		this.startState = (State) ts;
	}

	/**
	 * Get the initial state attribute.
	 * 
	 * @return The initial state value
	 */
	public StateI getInitial() {
		return this.startState;
	}

	/**
	 * Create a new State for this model.
	 * 
	 * @param id the id of the state
	 * @param name the name of the state
	 * @return The StateI value
	 */
	public StateI createState(final int id, final String name) {
		return new State(id, name);
	}

	/**
	 * Create a new Transition for this model.
	 * 
	 * @param fromState the originating state
	 * @param toState the destination state
	 * @return The TransitionI value
	 */
	public TransitionI createTransition(final StateI fromState, final StateI toState) {
		return new Transition(fromState, toState);
	}

	/**
	 * Sets the Name attribute.
	 * 
	 * @param name The new Name value
	 */
	public void setName(final String name) {
		this.modelName = name;
	}

	/**
	 * Returns a copy of the states list.
	 * 
	 * @return the state which is labeled by n
	 */
	public Map<Integer, StateI> getStates() {
		return this.states;
	}

	/**
	 * Returns the state with the indicated id.
	 * 
	 * @param name the state name
	 * @return the state which is labeled by n
	 */
	public StateI getState(final String name) {

		LOGGER.debug("name : " + name);

		for (final StateI state : this.states.values()) {

			if (state.getName().equals(name)) {
				return state;
			}
		}

		return null;
	}

	   /**
     * Returns the state with the indicated id.
     * 
     * @param name the state name
     * @return the state which is labeled by n
     */
    public StateI getState(final int stateId) {
        return states.get(stateId);
    }

	/**
	 * Return all transitions in the FSM.
	 * 
	 * @return The list of transitions
	 */
	public List<TransitionI> getTransitions() {

		if (this.states == null) {
			return null;
		}

		final List<TransitionI> transitions = new LinkedList<TransitionI>();

		for (final StateI state : this.states.values()) {

			if (state.getTransitions() == null) {
				continue;
			}

			for (final TransitionI transition : state.getTransitions()) {
				transitions.add(transition);
			}

		}

		return transitions;
	}

	/**
	 * Gets the Name attribute.
	 * 
	 * @return The Name value
	 */
	public String getName() {
		return modelName;
	}

	/**
	 * Read a definition from a file.
	 * 
	 * @param fileName The file name
	 * @throws FileNotFoundException If reading failed
	 */
	public void open(final String fileName) throws FileNotFoundException {

		final XMLDecoder encoder = new XMLDecoder(new FileInputStream(fileName));

		final JFsmModel model = (JFsmModel) encoder.readObject();

		this.modelName = model.getName();
		this.states = model.getStates();
		this.startState = (State) model.getInitial();

	}

	/**
	 * Save the definition to a file.
	 * 
	 * @param fileName the file name
	 * @throws Exception If writing failed
	 */
	public void save(final String fileName) throws Exception {

		LOGGER.debug("saving fileName = " + fileName);

		final XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));

		encoder.setExceptionListener(new ExceptionListener() {
			public void exceptionThrown(final Exception exception) {
				LOGGER.error("Exception thrown when saving = ", exception);
			}
		});

		encoder.setPersistenceDelegate(Event.class, new EventPersistenceDelegate());
		encoder.setPersistenceDelegate(State.class, new StatePersistenceDelegate());
		encoder.setPersistenceDelegate(Transition.class, new TransitionPersistenceDelegate());

		encoder.writeObject(this);
		encoder.close();

	}

	/**
	 * Adds a state.
	 * 
	 * @param state The state to be added
	 */
	public void addState(final StateI state) {

		LOGGER.debug("state = " + state);

		if (state == null) {
			return;
		}

		if (this.states == null) {
			this.states = new HashMap<Integer, StateI>();
			this.states.put(state.getIdentifier(), state);
			return;
		}

		boolean stateFound = false;

		for (final StateI presentState : this.states.values()) {

			if (state.getName().equals(presentState.getName())) {
				stateFound = true;
				break;
			}
		}

		if (!stateFound) {
			this.states.put(state.getIdentifier(), state);
		}
	}

	/**
	 * Removes a state from the states list, and also removes all transitions
	 * involving that state.
	 * 
	 * @param state The state to remove
	 */
	public void removeState(final StateI state) {

		LOGGER.debug("state = " + state);

		if (states == null) {
			return;
		}

		states.remove(state);
	}

	/**
	 * Validates a given machine, by checking for - presence of at least one
	 * state - presence of at least one transition - states in transitions are
	 * present in states list. If no exception is thrown, the JFsm Model can be
	 * run by the FsmEngine (Should check for cycles of transitions with no
	 * events)
	 * 
	 * @throws JFsmException If not a valid FSM. The message will say what was
	 */
	public void validate() throws JFsmException {

		LOGGER.debug("validate: ");

		if ((states == null) || (states.size() == 0)) {
			throw new JFsmException("No states.");
		}

		// Checking "to" states
		if (this.startState == null) {
			throw new JFsmException("No initial state.");
		}

		if ((this.getTransitions() == null) || (this.getTransitions().size() == 0)) {
			if (!anyTransitions()) {
				throw new JFsmException("No transitions and no entry actions in any states.");
			}
		}

		// Checking "to" states
		if (!hasTransitions()) {
			throw new JFsmException("No transitions and no entry actions in any states.");
		}

	}

	/**
	 * Check if there is any transitions or actions in any of the states.
	 * 
	 * @return true if at least one of the states has a action, otherwise false
	 */
	private boolean hasTransitions() {

		if (this.states == null) {
			return false;
		}

		for (final StateI state : this.states.values()) {

			if (state.getEntryActions() != null) {
				return true;
			}

			if (state.getExitActions() != null) {
				return true;
			}

			if ((state.getTransitions() != null) && (state.getTransitions().size() > 0)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Return a string version of this object.
	 * 
	 * @return The formatted string
	 */
	@Override
	public String toString() {

		final StringBuffer strb = new StringBuffer();

		strb.append("" + super.toString());
		strb.append("\nJFsmModel = { ");

		if (states == null) {
			strb.append("\nStates = none ");
		} else {

			strb.append("\nStates = { ");

			for (final StateI state : states.values()) {
				strb.append("\n State = " + state);
			}
			strb.append("} ");
		}

		strb.append("}");
		return strb.toString();
	}

	/**
	 * Check if there is any entry transitions or actions in any of the states.
	 * 
	 * @return true if at least one of the states has a entry action, otherwise
	 *         false
	 */
	private boolean anyTransitions() {

		if (states == null) {
			return false;
		}

		for (final StateI state : this.states.values()) {

			if (state.getInternalTransitions() != null) {
				return true;
			}

			if (state.getEntryActions() != null) {
				return true;
			}

			if (state.getExitActions() != null) {
				return true;
			}

			if (state.getInternalTransitions() != null) {
				return true;
			}
		}

		return false;
	}

    /**
     * Sets the States attribute of the JFsmModel object.
     * 
     * @param pStates The new States value
     */
    public void setStates(final List<StateI> pStates) {
        states.clear();
        for (StateI state: pStates) {
            states.put(state.getIdentifier(), state);
        }
    }

    /**
     * Sets the start state attribute of the JFsmModel object.
     * 
     * @param ts The new start state
     */
    public void setStartState(final StateI ts) {

        this.startState = (State) ts;

    }

}
