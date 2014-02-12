package org.adligo.i.adi.shared.models.tests;

import org.adligo.i.adi.shared.models.MemoryValue;
import org.adligo.tests.ATest;

public class MemoryValueTests extends ATest {

	public void testToStringEqualsAndHashCode() {
		MemoryValue valueA = new MemoryValue(null, null);
		assertEquals("MemoryValue [value=null,owner=null]", valueA.toString());
		MemoryValue valueB = new MemoryValue(null, null);
		assertEquals(valueA.hashCode(), valueB.hashCode());
		assertEquals(valueA, valueB);
		
		valueA = new MemoryValue("aval", null);
		assertEquals("MemoryValue [value=aval,owner=null]", valueA.toString());
		assertNotSame(valueA.hashCode(), valueB.hashCode());
		assertNotSame(valueA, valueB);
		valueB = new MemoryValue("bval", null);
		assertNotSame(valueA.hashCode(), valueB.hashCode());
		assertNotSame(valueA, valueB);
		
		valueB = new MemoryValue("val", null);
		valueA = new MemoryValue("val", null);
		assertEquals(valueA.hashCode(), valueB.hashCode());
		assertEquals(valueA, valueB);
		
		valueA = new MemoryValue("val", "oa");
		assertEquals("MemoryValue [value=val,owner=oa]", valueA.toString());
		valueB = new MemoryValue("val", null);
		assertNotSame(valueA.hashCode(), valueB.hashCode());
		assertNotSame(valueA, valueB);
		valueB = new MemoryValue("val", "ob");
		
		assertNotSame(valueA.hashCode(), valueB.hashCode());
		assertNotSame(valueA, valueB);
		
		valueB = new MemoryValue("val", "owner");
		valueA = new MemoryValue("val", "owner");
		assertEquals(valueA.hashCode(), valueB.hashCode());
		assertEquals(valueA, valueB);
	}
}
