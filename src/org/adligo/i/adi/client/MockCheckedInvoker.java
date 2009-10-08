package org.adligo.i.adi.client;

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
