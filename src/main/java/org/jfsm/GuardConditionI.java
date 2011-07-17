package org.jfsm;

/**
 * This is the interface to a guard condition. The guard condition may be an arbitrary complex conditional statement.
 * The only requirement is that its resulting value is boolean, returned through the evaluate() method.
 */
public interface GuardConditionI {

    /**
     * Set the context.
     * 
     *@param fsmContext The FsmContext value
     *@throws JFsmException if some object could not be resolved
     */
    public void setContext(Context fsmContext) throws JFsmException;

    /**
     * Evaluate the guard condition against the submitted event object.
     * 
     *@param event The event to evaluate
     *@return True if the condition is true, otherwise false
     */
    public boolean evaluate(Object event);

    /**
     * Return the complete guard condition expression as a string.
     * 
     *@return The Expression value
     */
    public String getExpression();

}
