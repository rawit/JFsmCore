package org.jfsm.basic;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.Statement;

import org.jfsm.ActionI;

/**
 * PersistenceDelegate for the State class.
 */
public class StatePersistenceDelegate extends DefaultPersistenceDelegate {

    /**
     * Initializer for the State class.
     * 
     *@param type The class
     *@param oldInstance The old instance of the State
     *@param newInstance The new instance of the State
     *@param out The encoder to use
     */
    protected void initialize(final Class<?> type, final Object oldInstance, 
                    final Object newInstance, final Encoder out) {
        super.initialize(type, oldInstance, newInstance, out);

        final State oldState = (State) oldInstance;
        final ActionI entryAction = oldState.getEntryActions().get(0);

        out.writeStatement(new Statement(oldInstance, "setEntryAction", new Object[] { entryAction }));
    }

    /**
     * Build an instantiation expression for the State class.
     * 
     *@param oldInstance The old instance of the State
     *@param out The encoder
     *@return The expression for the instantiation
     */
    protected Expression instantiate(final Object oldInstance, final Encoder out) {
        super.instantiate(oldInstance, out);

        return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[] { ((State) oldInstance)
                        .getName() });
    }
}
