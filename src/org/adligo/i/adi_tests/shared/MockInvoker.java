package org.adligo.i.adi_tests.shared;

import org.adligo.i.adi.shared.I_Invoker;

public class MockInvoker implements I_Invoker {
	private I_MockInvokerCallback i_mockCallback;
	
	
	public MockInvoker() {}
	
	public MockInvoker(I_MockInvokerCallback in) {
		i_mockCallback = in;
	}
	
	@Override
	public Object invoke(Object valueObject) {
		return i_mockCallback.mockInvoke(valueObject);
	}

	public I_MockInvokerCallback getI_mockCallback() {
		return i_mockCallback;
	}

	public void setI_mockCallback(I_MockInvokerCallback iMockCallback) {
		i_mockCallback = iMockCallback;
	}

}
