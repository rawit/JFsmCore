package org.jfsm;

import org.jfsm.basic.GuardAdapter;
import org.jfsm.basic.JFsmModel;
import org.jfsm.basic.State;
import org.jfsm.events.Event;
import org.jfsm.fsm.JFsm;
import org.jfsm.guards.IsChar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test code for the StateMachine class.
 */
public class StateMachineTest {

    private State state1;

    private State state2;

    private JFsmModel fsmModel;

    /**
     * A unit test for JUnit.
     * 
     *@throws JFsmException Description of Exception
     */
    @Test
    public void testSimpleGuardTransition() throws JFsmException {

        final State state1 = new State(1, "State 1");
        final State state2 = new State(2, "State 2");
        
        final JFsmModel fsmDef = new JFsmModel();
        fsmDef.addState(state1);
        fsmDef.addState(state2);
        fsmDef.setInitial(state1);

        final JFsm fsm = new JFsm(null, fsmDef);
        state1.addTransition(new Event(Object.class, null), new GuardAdapter() {
            public boolean evaluate(final Object o) {
                return true;
            }

        }, null, state2);

        state2.addTransition(new Event(Object.class, null), new GuardAdapter() {
            public boolean evaluate(final Object o) {
                return true;
            }
        }, null, state1);

        fsm.start();
        Assert.assertTrue(fsm.getCurrentState() == state1);
        fsm.input(new Object());
        Assert.assertTrue(fsm.getCurrentState() == state2);
        fsm.input(new Object());
        Assert.assertTrue(fsm.getCurrentState() == state1);

    }

    /**
     * A unit test for JUnit.
     * 
     *@throws JFsmException Description of Exception
     */
    @Test
    public void testSimpleEventTransition() throws JFsmException {

        final State state1 = new State(1, "State 1");
        final State state2 = new State(2, "State 2");

        state1.addTransition(new Event(Object.class, null), null, null, state2);

        final JFsmModel fsmDef = new JFsmModel();
        fsmDef.addState(state1);
        fsmDef.addState(state2);
        fsmDef.setInitial(state1);

        final JFsm sm = new JFsm(null, fsmDef);
        sm.start();
        Assert.assertTrue(sm.getCurrentState() == state1);
        sm.input(new Object());
        Assert.assertTrue(sm.getCurrentState() == state2);

    }

    /**
     * A unit test for JUnit.
     * 
     *@throws JFsmException Description of Exception
     */
    @Test
    public void testNoEventTransition() throws JFsmException {

        final State state1 = new State(1, "State 1");
        final State state2 = new State(2, "State 2");

        state1.addTransition(null, null, null, state2);

        fsmModel = new JFsmModel();
        fsmModel.addState(state1);
        fsmModel.addState(state2);
        fsmModel.setInitial(state1);

        final JFsm sm = new JFsm(null, fsmModel);
        sm.start();
        Assert.assertTrue(sm.getCurrentState() == state2);

    }

    /**
     * A unit test for JUnit.
     * 
     *@throws JFsmException Description of Exception
     */
    @Test
    public void testEmptyDefinition() throws JFsmException {
        try {
            new JFsm(null, new JFsmModel());
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (final IllegalArgumentException iae) {
            Assert.assertTrue(true);
        }
    }

    /**
     * A unit test for JUnit.
     * 
     *@throws JFsmException Description of Exception
     */
    @Test
    public void testContextAndDefinitionNullArg() throws JFsmException {

        try {
            new JFsm(null, null);
            Assert.fail("StateMachine(null, null) should have thrown FsmException");
        } catch (final IllegalArgumentException iae) {
            Assert.assertTrue(true);
        }

    }

    @Test
    public void testCharInput() {
        
        final State state1= new State(1);
        final State state2= new State(2);
        final State state3= new State(3);

        state3.setFinal(true);

        final IsChar isA = new IsChar('A');
        final IsChar isB = new IsChar('B');
        final IsChar isC = new IsChar('C');
        final Event event= new Event(Character.class);
        final CountChars countChars = new CountChars();

        state1.addTransition(event, isA , countChars, state2);
        state1.addTransition(event, isC , null, state3);

        state2.addTransition(event, isB , countChars, state1);
        state2.addTransition(event, isC , null, state3);

        final JFsmModel fsmModel= new JFsmModel();
        fsmModel.addState(state1);
        fsmModel.addState(state2);
        fsmModel.addState(state3);
        fsmModel.setInitial(state1);

        final JFsm fsmEngine= new JFsm(fsmModel);
        fsmEngine.start();

        Assert.assertTrue(fsmEngine.getCurrentState() == state1);
        fsmEngine.input('z');
        Assert.assertEquals(fsmEngine.getCurrentState(), state1);
        fsmEngine.input("A"); // This is a string, to be ignored!
        Assert.assertEquals(fsmEngine.getCurrentState(), state1);
        fsmEngine.input('A');
        Assert.assertEquals(fsmEngine.getCurrentState(), state2);
        fsmEngine.input('D');
        Assert.assertEquals(fsmEngine.getCurrentState(), state2);
        fsmEngine.input('B');
        Assert.assertEquals(fsmEngine.getCurrentState(), state1);
        Assert.assertEquals(2, countChars.getCharCount());

        Assert.assertTrue(fsmEngine.isRunning());
        fsmEngine.input('C');
        Assert.assertEquals(fsmEngine.getCurrentState(), state3);
        Assert.assertEquals(2, countChars.getCharCount());
        Assert.assertFalse(fsmEngine.isRunning());

        try {
            fsmEngine.input('D');
            Assert.fail("Sould have thrown FsmException because the FSM has "
                  + "finished execution.");
        } catch (final JFsmException fsme) {
            // Fine!
        }



    }
    
    /**
     * The JUnit setup method.
     */
    @Before
    public void setUp() {

        state1 = new State(1, "State 1");
        state2 = new State(2, "State 2");

        state1.addTransition(new Event(Object.class.getName(), null), null, null, state2);

        fsmModel = new JFsmModel();
        fsmModel.addState(state1);
        fsmModel.addState(state2);
        fsmModel.setInitial(state1);

    }

}
