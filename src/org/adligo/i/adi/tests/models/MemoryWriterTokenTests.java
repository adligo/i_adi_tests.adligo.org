package org.adligo.i.adi.tests.models;

import org.adligo.i.adi.client.models.MemoryWriterToken;
import org.adligo.tests.ATest;

public class MemoryWriterTokenTests extends ATest {

	public void testToString() {
		MemoryWriterToken token = new MemoryWriterToken();
		
		assertEquals("MemoryWriterToken [key=null,value=null,owner=null]", token.toString());
		token.setKey("a");
		assertEquals("MemoryWriterToken [key=a,value=null,owner=null]", token.toString());
		token.setValue("b");
		assertEquals("MemoryWriterToken [key=a,value=b,owner=null]", token.toString());
		token.setOwner("c");
		assertEquals("MemoryWriterToken [key=a,value=b,owner=c]", token.toString());
		
	
	}
}
