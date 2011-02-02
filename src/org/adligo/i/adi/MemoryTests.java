package org.adligo.i.adi;

import org.adligo.i.adi.client.MemoryReader;
import org.adligo.i.adi.client.MemoryWriter;
import org.adligo.i.adi.client.models.MemoryWriterToken;
import org.adligo.tests.ATest;

public class MemoryTests extends ATest {



	public void testMemory() {
		MemoryWriter writer = MemoryWriterChild.INSTANCE;
		MemoryReader reader = MemoryReaderChild.INSTANCE;
		
		Exception caught = null;
		try {
			writer.invoke(null);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof IllegalArgumentException);
		assertEquals(MemoryWriter.MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN_AND_WAS_NULL, 
				caught.getMessage());
		
		caught = null;
		try {
			writer.invoke("String");
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof IllegalArgumentException);
		assertEquals(MemoryWriter.MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN + "String", 
				caught.getMessage());
		
		caught = null;
		MemoryWriterToken token = new MemoryWriterToken();
		try {
			writer.invoke(token);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof IllegalArgumentException);
		assertEquals(MemoryWriter.MEMORY_WRITER_REQUIRES_A_NON_EMPTY_KEY, caught.getMessage());
		
		token.setKey("hey");
		token.setValue("man");
		Boolean result = (Boolean) writer.invoke(token);
		assertTrue(result);
		
		Object obj = reader.invoke("hey");
		assertEquals("man", obj);
		
		obj = reader.invoke("man");
		assertNull(obj);
		
		token.setValue(null);
		result = (Boolean) writer.invoke(token);
		assertTrue(result);
		obj = reader.invoke("hey");
		assertNull(obj);
	}
}
