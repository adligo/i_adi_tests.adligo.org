package org.adligo.i.adi_tests.shared;

import org.adligo.i.adi.shared.BaseConfigProvider;
import org.adligo.i.adi.shared.models.ConfigRequest;
import org.adligo.tests.ATest;

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
