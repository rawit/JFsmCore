package org.jfsm;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * The JFsm Model interface. Contains methods that a object implementing JFsm
 * needs to extract the machine definition and run it.
 */
public interface JFsmModelI {

	/**
	 * Removes a state from the states list, and also removes all transitions
	 * involving that state.
	 * 
	 * @param state The state to remove
	 */
	void removeState(StateI state);

	/**
	 * Adds a state.
	 * 
	 * @param state The state to be added
	 */
	void addState(StateI state);

	/**
	 * Sets the initial state attribute.
	 * 
	 * @param initial The new initial state value
	 */
	void setInitial(StateI initial);

	/**
	 * Gets the initial attribute.
	 * 
	 * @return The top state value
	 */
	StateI getInitial();

	/**
	 * Read a definition from a file.
	 * 
	 * @param fileName The file name
	 * @throws FileNotFoundException In case of error
	 */
	void open(String fileName) throws FileNotFoundException;

	/**
	 * Save the definition to a file.
	 * 
	 * @param fileName The file name
	 * @throws Exception In case of error
	 */
	void save(String fileName) throws Exception;

	/**
	 * Gets the Name attribute.
	 * 
	 * @return The Name value
	 */
	String getName();

	/**
	 * Gets the States attribute.
	 * 
	 * @return The States value
	 */
	Map<Integer, StateI> getStates();

	/**
	 * Gets the State attribute.
	 * 
	 * @param name the name
	 * @return The State value
	 */
	StateI getState(String name);

	/**
	 * Create a new State for this model.
	 * 
	 * @param id the id
	 * @param name the name of the state
	 * @return The StateI value
	 */
	StateI createState(int id, String name);

	/**
	 * Create a new Transition for this model.
	 * 
	 * @param fromState the originating state
	 * @param toState the destination state
	 * @return The TransitionI value
	 */
	TransitionI createTransition(StateI fromState, StateI toState);

	/**
	 * Validate the FSM definition.
	 * 
	 */
	void validate();

	/**
	 * Gets the Transitions attribute.
	 * 
	 * @return The Transitions value
	 */
	List<TransitionI> getTransitions();

	/**
	 * Sets the Name attribute of the JFsmModel object.
	 * 
	 * @param name The new Name value
	 */
	void setName(String name);

    StateI getState(int i);

}
