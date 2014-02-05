package org.adligo.i.adi.tests;

import org.adligo.i.adi.client.InvocationException;

public interface I_MockCheckedInvokerCallback {
	public Object mockCheckedInvoke(Object obj) throws InvocationException;
}
