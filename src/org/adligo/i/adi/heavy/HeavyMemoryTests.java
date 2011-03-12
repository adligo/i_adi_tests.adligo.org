package org.adligo.i.adi.heavy;

import org.adligo.i.adi.client.heavy.HeavyMemoryReader;
import org.adligo.i.adi.client.heavy.HeavyMemoryWriter;
import org.adligo.i.adi.client.models.MemoryWriterToken;
import org.adligo.tests.ATest;

public class HeavyMemoryTests extends ATest {



	public void testMemory() {
		HeavyMemoryWriter writer = HeavyMemoryWriterChild.INSTANCE;
		HeavyMemoryReader reader = HeavyMemoryReaderChild.INSTANCE;
		
		Exception caught = null;
		try {
			writer.invoke(null);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof IllegalArgumentException);
		assertEquals(HeavyMemoryWriter.MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN_AND_WAS_NULL, 
				caught.getMessage());
		
		caught = null;
		try {
			writer.invoke("String");
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof IllegalArgumentException);
		assertEquals(HeavyMemoryWriter.MEMORY_WRITER_REQUIRES_A_MEMORY_WRITER_TOKEN + "String", 
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
		assertEquals(HeavyMemoryWriter.MEMORY_WRITER_REQUIRES_A_NON_EMPTY_KEY, caught.getMessage());
		
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
	
	public void testMemoryOwnership() {
		HeavyMemoryWriter writer = HeavyMemoryWriterChild.INSTANCE;
		HeavyMemoryReader reader = HeavyMemoryReaderChild.INSTANCE;
		
		Object owner = new Object();
		MemoryWriterToken token = new MemoryWriterToken();
		token.setKey("a");
		token.setOwner(owner);
		token.setValue("car");
		
		writer.invoke(token);
		Object val = reader.invoke("a");
		assertEquals("car", val);
		
		token.setOwner("ownerB");
		Exception caught = null;
		try {
			writer.invoke(token);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof IllegalArgumentException);
		assertEquals(
				HeavyMemoryWriter.ONLY_THE_OWNER_OF_A_MEMORY_VALUE_IS_ALLOWED_TO_MODIFY_IT, 
				caught.getMessage());
		val = reader.invoke("a");
		assertEquals("car", val);
		
		
		token.setOwner("ownerB");
		token.setValue(null);
		caught = null;
		try {
			writer.invoke(token);
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertTrue(caught instanceof IllegalArgumentException);
		assertEquals(
				HeavyMemoryWriter.ONLY_THE_OWNER_OF_A_MEMORY_VALUE_IS_ALLOWED_TO_MODIFY_IT, 
				caught.getMessage());
		val = reader.invoke("a");
		assertEquals("car", val);
		
		token.setOwner(owner);
		token.setValue("bike");
		writer.invoke(token);
		val = reader.invoke("a");
		assertEquals("bike", val);
		
		token.setOwner(owner);
		token.setValue(null);
		writer.invoke(token);
		val = reader.invoke("a");
		assertNull(val);
		
	}
}
