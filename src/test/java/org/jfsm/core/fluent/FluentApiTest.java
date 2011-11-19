package org.jfsm.core.fluent;

import static org.jfsm.core.fluent.FromStateBuilder.when;

import org.jfsm.ActionI;
import org.jfsm.GuardConditionI;
import org.jfsm.JFsmException;
import org.jfsm.JFsmModelI;
import org.jfsm.StateI;
import org.jfsm.core.AbstractActionAdapter;
import org.jfsm.core.AbstractGuardAdapter;
import org.jfsm.core.JFsmModel;
import org.jfsm.core.action.CountChars;
import org.jfsm.core.fsm.JFsm;
import org.jfsm.core.guards.IsChar;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Prototyping av et fluent API.
 * 
 * @author ragnarwestad
 *
 */
public class FluentApiTest {

    private final class TestAction extends AbstractActionAdapter {

        public void execute(Object triggeringEvent) {
           System.out.println("Hello from action!");            
        }
    }

    private final class TestGuard extends AbstractGuardAdapter {

        public boolean evaluate(Object event) {
            if (event instanceof String) {
                String str = (String) event;
                return str.equals("TestString");
            }
            return false;
        }
    }

    /**
     * Test fluent interface.
     */
    @Test
    public void testOneExternalTransition() {

        final GuardConditionI guard1 = new TestGuard();
        
        final ActionI action1 = new TestAction();

        final StateI state = when(1).receives(String.class).andMatches(guard1).thenExecute(action1).andGoTo(2);

        Assert.assertEquals("Wrong number of transitions", 1, state.getTransitions().size());

        final JFsmModelI jFsmModel2 = when(1).receives(String.class).andMatches(guard1).goTo(2);

        System.out.println(jFsmModel2);
        Assert.assertEquals("Wrong number of states", 2, jFsmModel2.getStates().size());
        Assert.assertEquals("Wrong state!", "1", jFsmModel2.getInitial().getName());
        // Assert.assertEquals("Wrong number of transitions", 1, jFsmModel2.getTransitions().size());

    }

    /**
     * Test fluent interface.
     */
    @Test
    public void testMinimalTransition() {

        final JFsmModelI jFsmModel = when(1).receives(String.class).goTo(2);

        System.out.println(jFsmModel);
        Assert.assertEquals("Wrong number of states", 2, jFsmModel.getStates().size());
        Assert.assertEquals("Wrong state!", "1", jFsmModel.getInitial().getName());

    }


    @Test
    @Ignore("Need more work")
    public void testCharInput() {

        final IsChar isCharA = new IsChar('A');
        final IsChar isCharB = new IsChar('B');
        final IsChar isCharC = new IsChar('C');
        final CountChars countChars = new CountChars();
        
        StateI state1 = when(1).receives(Character.class).andMatches(isCharA).thenExecute(countChars).andGoTo(2);
                        when(1).receives(Character.class).andMatches(isCharC).goTo(3);

        StateI state2 = when(2).receives(Character.class).andMatches(isCharB).thenExecute(countChars).andGoTo(1);
                        when(2).receives(Character.class).andMatches(isCharC).goTo(3);
        
        final JFsmModelI fsmModel = new JFsmModel();
        fsmModel.addState(state1);
        fsmModel.addState(state2);
        fsmModel.setInitial(state1);
        System.out.println("Model: " + fsmModel);
        final JFsm jFsm = new JFsm(fsmModel);
        jFsm.start();

        Assert.assertEquals(1, jFsm.getCurrentState().getIdentifier());
        jFsm.input('z');
        Assert.assertEquals(1, jFsm.getCurrentState().getIdentifier());
        jFsm.input("A"); // This is a string, to be ignored!
        Assert.assertEquals(1, jFsm.getCurrentState().getIdentifier());
        jFsm.input('A');
        Assert.assertEquals(2, jFsm.getCurrentState().getIdentifier());
        jFsm.input('D');
        Assert.assertEquals(2, jFsm.getCurrentState().getIdentifier());
        jFsm.input('B');
        Assert.assertEquals(2, jFsm.getCurrentState().getIdentifier());
        Assert.assertEquals(2, countChars.getCharCount());

        Assert.assertTrue(jFsm.isRunning());
        jFsm.input('C');
        Assert.assertEquals(3, jFsm.getCurrentState().getIdentifier());
        Assert.assertEquals(2, countChars.getCharCount());
        Assert.assertFalse(jFsm.isRunning());

        try {
            jFsm.input('D');
            Assert.fail("Sould have thrown FsmException because the FSM has " + "finished execution.");
        } catch (final JFsmException fsme) {
            // Fine!
        }

    }
//    /**
//     * Test fluent interface.
//     */
//    @Test
//    public void testFluentFsm() {
//
//        final Guard guard1 = new Guard("myString");
//        final Action action1 = new Action();
//        final Action action2 = new Action();
//
//        final FsmBuilder fsm = new FsmBuilder("state1");
//        fsm.when("state1").isEnteredThenExecute(action1, action2);
//        fsm.when("state1").receives(String.class).andMatches(guard1).thenExecute(action1).andGoTo("state2");
//        fsm.when("state1").isExitedThenExecute(action1, action2);
//        fsm.when("state2").receives(String.class).thenGoTo("state3");
//        fsm.when("state2").receives(null).thenGoTo("state1");
//        fsm.when("state3").receives(String.class).thenExecute(action1, action2).andGoTo("state1");
//
//        fsm.print();
//        fsm.input("myString");        
//        Assert.assertEquals("Wrong state!", "state2", fsm.getCurrentState().getId());
//
//    }

}
