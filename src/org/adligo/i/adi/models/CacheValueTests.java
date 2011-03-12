package org.adligo.i.adi.models;

import org.adligo.i.adi.client.models.CacheValue;
import org.adligo.tests.ATest;

public class CacheValueTests extends ATest {

	public void testToString() {
		CacheValue cv = new CacheValue("a", 0, "hey");
		assertEquals("CacheValue [fullPath=a,putTime=0,value=hey]",cv.toString());
		
	}
	
	public void testTimeCrunchString() {
		CacheValue cv = new CacheValue("", 0, "hey");
		assertEquals("/0/0@" + cv.hashCode(),cv.getTimeCrunchString());
		
		cv = new CacheValue("",1000, "hey");
		assertEquals("/0/1000@" + cv.hashCode(),cv.getTimeCrunchString());
		
		cv = new CacheValue("",59999, "hey");
		assertEquals("/0/59999@" + cv.hashCode(),cv.getTimeCrunchString());
		
		cv = new CacheValue("",60000, "hey");
		assertEquals("/1/60000@" + cv.hashCode(),cv.getTimeCrunchString());
		
		cv = new CacheValue("",-1, "hey");
		assertEquals("/0/-1@" + cv.hashCode(),cv.getTimeCrunchString());
		
		cv = new CacheValue("",-59999, "hey");
		assertEquals("/0/-59999@" + cv.hashCode(),cv.getTimeCrunchString());
		
		cv = new CacheValue("",-60000, "hey");
		assertEquals("/-1/-60000@" + cv.hashCode(),cv.getTimeCrunchString());
	}
}
