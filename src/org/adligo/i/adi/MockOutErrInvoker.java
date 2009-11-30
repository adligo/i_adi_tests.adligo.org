package org.adligo.i.adi;

import java.util.ArrayList;
import java.util.List;

import org.adligo.i.adi.client.I_Invoker;

public class MockOutErrInvoker implements I_Invoker {
	private List<String> messages = new ArrayList<String>();

	@Override
	public Object invoke(Object valueObject) {
		messages.add((String) valueObject);
		return null;
	}

	public List<String> getMessages() {
		return messages;
	}
	
}
