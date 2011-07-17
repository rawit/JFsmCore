package org.jfsm.basic.test;

import org.jfsm.JFsmException;
import org.jfsm.StateI;
import org.jfsm.basic.JFsmModel;
import org.jfsm.basic.State;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test code for the Definition class.
 */
public class DefinitionTest {

    /**
     * Test that the validate method discovers circular empty (epsilon) transition.
     * 
     *@throws JFsmException Description of Exception
     */
    @Test
    @Ignore
    public void testCircularNonEventTransitions() throws JFsmException {

        final JFsmModel fsmDef = new JFsmModel();

        final State state1 = new State(1, "State 1");
        final State state2 = new State(2, "State 2");

        state1.addTransition(null, null, null, state2);
        state2.addTransition(null, null, null, state1);

        fsmDef.addState(state1);
        fsmDef.addState(state2);
        fsmDef.setInitial(state1);
        try {
            fsmDef.validate();
            Assert.fail("fsmDef.validate() should have thrown JFsmException");
        } catch (final JFsmException fsme) {
            Assert.assertTrue(true);
        }
        fsmDef.validate();
    }

    /**
     * A unit test for JUnit.
     * 
     *@throws JFsmException Description of Exception
     */
    @Test
    public void testRemoveStateEmptyDef() throws JFsmException {
        final JFsmModel fsmDef = new JFsmModel();
        fsmDef.removeState(null);
        final State state1 = new State(1, "State 1");
        fsmDef.removeState(state1);
    }

    /**
     * A unit test for JUnit.
     * 
     *@throws JFsmException Description of Exception
     */
    @Test
    public void testVerifyEmptyDef() throws JFsmException {
        final JFsmModel fsmDef = new JFsmModel();
        try {
            fsmDef.validate();
            Assert.fail("fsmDef.validate() should have thrown JFsmException");
        } catch (final JFsmException fsme) {
            Assert.assertTrue(true);
        }
    }

    /**
     * A unit test for JUnit.
     */
    @Test
    public void testGettersEmptyDef() {
        final JFsmModel fsmDef = new JFsmModel();
        Assert.assertNull(fsmDef.getName());
        Assert.assertTrue(fsmDef.getInitial() == null);
        Assert.assertTrue(fsmDef.getStates() == null || fsmDef.getStates().isEmpty());
        Assert.assertTrue(fsmDef.getTransitions() == null || fsmDef.getTransitions().isEmpty());
    }

    /**
     * A unit test for JUnit.
     */
    @Test
    public void testInitialState() {
        final JFsmModel fsmDef = new JFsmModel();
        final StateI state = new State(1);
        fsmDef.setInitial(state);
        try {
            fsmDef.validate();
            Assert.fail("fsmDef.validate() should have thrown JFsmException");
        } catch (final JFsmException fsme) {
            Assert.assertTrue(true);
        }
        fsmDef.addState(state);
        fsmDef.validate();
        fsmDef.setInitial(null);
        try {
            fsmDef.validate();
            Assert.fail("fsmDef.validate() should have thrown JFsmException");
        } catch (final JFsmException fsme) {
            Assert.assertTrue(true);
        }
    }

}
