package org.adligo.i.adi_tests.shared;

import org.adligo.i.adi.shared.I_CheckedInvoker;
import org.adligo.i.adi.shared.InvocationException;

public class MockCheckedInvoker implements I_CheckedInvoker {
	private I_MockCheckedInvokerCallback checkedInvoker;
	
	
	public MockCheckedInvoker() {}
	
	public MockCheckedInvoker(I_MockCheckedInvokerCallback in ) {
		checkedInvoker = in;
	}
	
	@Override
	public Object invoke(Object valueObject) throws InvocationException {
		return checkedInvoker.mockCheckedInvoke(valueObject);
	}

	public I_MockCheckedInvokerCallback getCheckedInvoker() {
		return checkedInvoker;
	}

	public void setCheckedInvoker(I_MockCheckedInvokerCallback checkedInvoker) {
		this.checkedInvoker = checkedInvoker;
	}

	
}
