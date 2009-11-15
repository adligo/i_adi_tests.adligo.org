package org.adligo.i.adi;

import org.adligo.i.adi.client.ProxyCheckedInvoker;
import org.adligo.tests.ATest;

public class ProxyCheckedInvokerTests extends ATest {

	public void testProxyInvokerFactories() {
		ProxyCheckedInvoker pciA = ProxyCheckedInvoker.getInstance("nameA");
		ProxyCheckedInvoker pciB = ProxyCheckedInvoker.getInstance("nameA");
		assertTrue("It is important that instances match from the Proxy checked Invoker factory"
				, pciA == pciB);
	}
}
