package org.jfsm.core.fluent;

public class FromStateBuilder {

    public static EventBuilder when(final int pStateId) {
        FsmBuilder fsmBuilder = new FsmBuilder(pStateId);
        fsmBuilder.setFromState(fsmBuilder.getState(pStateId));
        return new EventBuilder(fsmBuilder);
    }

}
