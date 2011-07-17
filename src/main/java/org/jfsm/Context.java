package org.jfsm;

/**
 * Interface for defining the context (environment) of a state machine. An object implementing this interface may be
 * submitted to a state machine at start up. Any GuardConditionI or ActionI objects added to the state machine will be
 * handed the the context object at startup of the state machine.
 */
public interface Context {

}
