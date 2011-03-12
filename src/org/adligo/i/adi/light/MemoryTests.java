package org.adligo.i.adi.light;

import org.adligo.i.adi.client.light.MemoryReader;
import org.adligo.i.adi.client.light.MemoryWriter;
import org.adligo.i.adi.client.models.MemoryValue;
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
		
		Object firstOwner = new Object();
		token.setValue("owned");
		token.setOwner(firstOwner);
		result = (Boolean) writer.invoke(token);
		assertTrue(result);
		assertEquals("owned", reader.invoke("hey"));
		
		token.setOwner(null);
		caught = null;
		try {
			writer.invoke(token);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof IllegalArgumentException);
		assertEquals(MemoryWriter.ONLY_THE_OWNER_OF_A_MEMORY_VALUE_IS_ALLOWED_TO_MODIFY_IT
				+ token,
				caught.getMessage());
		
		token.setOwner(new Object());
		caught = null;
		try {
			writer.invoke(token);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof IllegalArgumentException);
		assertEquals(MemoryWriter.ONLY_THE_OWNER_OF_A_MEMORY_VALUE_IS_ALLOWED_TO_MODIFY_IT
				+ token,
				caught.getMessage());
		
		token.setValue("owned2");
		token.setOwner(firstOwner);
		result = (Boolean) writer.invoke(token);
		assertTrue(result);
		assertEquals("owned2", reader.invoke("hey"));
		
		
		token.setValue(null);
		result = (Boolean) writer.invoke(token);
		assertTrue(result);
		assertNull(reader.invoke("hey"));
	}
}
