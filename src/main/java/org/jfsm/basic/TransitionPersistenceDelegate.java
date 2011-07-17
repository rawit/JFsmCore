package org.jfsm.basic;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.Statement;

/**
 * PersistenceDelegate for the Transition class.
 */
public class TransitionPersistenceDelegate extends PersistenceDelegate {

    /**
     * Initializer for the Transition class.
     * 
     *@param type The class
     *@param oldInstance The old instance of the Transition
     *@param newInstance The new instance of the Transition
     *@param out The encoder to use
     */
    protected void initialize(final Class<?> type, final Object oldInstance, 
                    final Object newInstance, final Encoder out) {
        super.initialize(type, oldInstance, newInstance, out);

        final Transition oldTransition = (Transition) oldInstance;

        out.writeStatement(new Statement(oldInstance, "setEvent", new Object[] { oldTransition.getEvent() }));

        out.writeStatement(new Statement(oldInstance, "setGuardCondition", new Object[] { oldTransition
                        .getGuardCondition() }));

        out.writeStatement(new Statement(oldInstance, "setAction", new Object[] { oldTransition.getActions() }));

    }

    /**
     * Build an instantiation expression for the Transition class.
     * 
     *@param oldInstance The old instance of the Transition
     *@param out The encoder
     *@return The expression for the instantiation
     */
    protected Expression instantiate(final Object oldInstance, final Encoder out) {

        return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[] {
                        ((Transition) oldInstance).getFromState(), ((Transition) oldInstance).getToStateId() });
    }
}
