package org.adligo.i.adi;

import org.adligo.i.adi.client.models.ReferenceAddressName;
import org.adligo.tests.ATest;

public class ReferenceAddressNameTests extends ATest {

	public void testNameAddress() {
		ReferenceAddressName na = new ReferenceAddressName("");
		assertEquals("", na.getParentFullPath());
		assertEquals("", na.getLocalPath());
		
		na = new ReferenceAddressName(null);
		assertEquals("", na.getParentFullPath());
		assertEquals("", na.getLocalPath());
		
		na = new ReferenceAddressName("/i");
		assertEquals("", na.getParentFullPath());
		assertEquals("/i", na.getLocalPath());
		
		na = new ReferenceAddressName("/a/b");
		assertEquals("/a", na.getParentFullPath());
		assertEquals("/b", na.getLocalPath());
		
		na = new ReferenceAddressName("/a/b/c/d");
		assertEquals("/a/b/c", na.getParentFullPath());
		assertEquals("/d", na.getLocalPath());
		
	}
}
