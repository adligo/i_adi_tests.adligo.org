package org.adligo.i.adi_tests.shared;

import org.adligo.i.adi.shared.InvocationException;

public interface I_MockCheckedInvokerCallback {
	public Object mockCheckedInvoke(Object obj) throws InvocationException;
}
