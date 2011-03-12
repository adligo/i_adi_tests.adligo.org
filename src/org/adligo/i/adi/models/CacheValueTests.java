package org.adligo.i.adi.models;

import org.adligo.i.adi.client.models.CacheValue;
import org.adligo.tests.ATest;

public class CacheValueTests extends ATest {

	public void testTimeCrunchString() {
		CacheValue cv = new CacheValue("", 0, "hey");
		assertEquals("/0/0",cv.getTimeCrunchString());
		
		cv = new CacheValue("",1000, "hey");
		assertEquals("/0/1000",cv.getTimeCrunchString());
		
		cv = new CacheValue("",59999, "hey");
		assertEquals("/0/59999",cv.getTimeCrunchString());
		
		cv = new CacheValue("",60000, "hey");
		assertEquals("/1/60000",cv.getTimeCrunchString());
		
		cv = new CacheValue("",-1, "hey");
		assertEquals("/0/-1",cv.getTimeCrunchString());
		
		cv = new CacheValue("",-59999, "hey");
		assertEquals("/0/-59999",cv.getTimeCrunchString());
		
		cv = new CacheValue("",-60000, "hey");
		assertEquals("/-1/-60000",cv.getTimeCrunchString());
	}
}
