package org.jfsm.core.action;

import org.jfsm.JFsmException;
import org.jfsm.core.AbstractActionAdapter;

public class CountChars extends AbstractActionAdapter {

	int charCount = 0;

	public void execute(final Object pEvent) throws JFsmException {
		if (pEvent instanceof Character) {
			this.charCount++;
		}
	}

	public int getCharCount() {
		return this.charCount;
	}

}
