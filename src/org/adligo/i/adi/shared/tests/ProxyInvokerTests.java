package org.adligo.i.adi.shared.tests;

import org.adligo.i.adi.shared.ProxyInvoker;
import org.adligo.tests.ATest;

public class ProxyInvokerTests extends ATest {

	
	public void testInstances() {
		ProxyInvoker instanceA = ProxyInvoker.getInstance("nameA");
		ProxyInvoker instanceB = ProxyInvoker.getInstance("nameA");
		assertTrue("It is important that instances match from the Proxy Invoker factory"
				, instanceA == instanceB);
	}
}

