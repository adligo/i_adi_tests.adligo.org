package org.adligo.i.adi;

import org.adligo.i.adi.client.BaseConfigProvider;
import org.adligo.i.adi.client.models.ConfigRequest;
import org.adligo.tests.ATest;

import com.sun.tools.internal.jxc.ConfigReader;

public class BaseConfigProviderTests extends ATest {

	public void testInvoke() {
		BaseConfigProvider provider = new BaseConfigProvider();
		Exception caught = null;
		
		try {
			provider.invoke("String");
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof IllegalArgumentException);
		assertEquals("" + BaseConfigProvider.class.getName() + BaseConfigProvider.TAKES_A +
				ConfigRequest.class.getName() + BaseConfigProvider.AND_WAS_PASSED + "String",
				caught.getMessage());
		ConfigRequest req = new ConfigRequest("someKey", "default");
		assertEquals("default", provider.invoke(req));
		
	}
}
