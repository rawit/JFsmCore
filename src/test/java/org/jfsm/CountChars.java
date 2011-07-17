package org.jfsm;

import org.jfsm.core.ActionAdapter;

public class CountChars extends ActionAdapter {

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
