package org.jfsm.core.test.events;

import org.jfsm.GuardConditionI;
import org.jfsm.JFsmModelI;
import org.jfsm.StateI;
import org.jfsm.core.JFsmModel;
import org.jfsm.core.State;
import org.jfsm.events.FsmPropertyChangeEvent;
import org.jfsm.fsm.JFsm;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test property change events. Set up at <code>TestBean</code> class who emits property change events using standard
 * bean property change mechanisms. Then use a custom JFsm property change event, <code>MyBeanPropertyChangeEvent</code>
 * to catch these and input to the FSM. The <code>IntGt</code> guard condition test the changed value and says 'true'
 * when the value greater than 5 has been set using the <code>setX()</code> method.
 * 
 * @author rwe
 */
public class PropertyChangeTest {

    /**
     * Test that JFsmPropertyChange events leads to the expected state changes.
     */
    @Test
    public void testPropertyChange() {

        final TestBean testBean = new TestBean();

        final StateI state1 = new State(1);
        final StateI state2 = new State(2);
        final JFsmModelI fsmModel = new JFsmModel();
        fsmModel.addState(state1);
        fsmModel.addState(state2);
        fsmModel.setInitial(state1);

        final FsmPropertyChangeEvent pce = new MyBeanPropertyChangeEvent(testBean);
        final GuardConditionI xGt5 = new IntGt(5);
        state1.addTransition(pce, xGt5, null, state2);
        state2.addTransition(pce, xGt5, null, state1);

        final JFsm jFsm = new JFsm(fsmModel);
        jFsm.start();
        Assert.assertEquals(state1, jFsm.getCurrentState());

        testBean.setX(4);
        Assert.assertEquals(state1, jFsm.getCurrentState());
        testBean.setX(5);
        Assert.assertEquals(state1, jFsm.getCurrentState());
        testBean.setX(6);
        Assert.assertEquals(state2, jFsm.getCurrentState());
        testBean.setX(7);
        Assert.assertEquals(state1, jFsm.getCurrentState());
        testBean.setX(8);
        Assert.assertEquals(state2, jFsm.getCurrentState());

    }

}
