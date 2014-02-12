package org.adligo.i.adi_tests.shared.models;

import org.adligo.i.adi.shared.models.CacheValue;
import org.adligo.tests.ATest;

public class CacheValueTests extends ATest {

	public void testToString() {
		CacheValue cv = new CacheValue("a", 0, "hey");
		assertEquals("CacheValue [fullPath=a,putTime=0,value=hey]",cv.toString());
		
	}
	
	public void testTimeCrunchString() {
		CacheValue cv = new CacheValue("/parent/local", 0, "hey");
		assertEquals("/0/parent/0@local",cv.getTimeCrunchString());
		
		cv = new CacheValue("/parent/local",1000, "hey");
		assertEquals("/0/parent/1000@local",cv.getTimeCrunchString());
		
		cv = new CacheValue("/parent/local",59999, "hey");
		assertEquals("/0/parent/59999@local",cv.getTimeCrunchString());
		
		cv = new CacheValue("/parent/local",60000, "hey");
		assertEquals("/1/parent/60000@local",cv.getTimeCrunchString());
		
		cv = new CacheValue("/parent/local",-1, "hey");
		assertEquals("/0/parent/-1@local",cv.getTimeCrunchString());
		
		cv = new CacheValue("/parent/local",-59999, "hey");
		assertEquals("/0/parent/-59999@local",cv.getTimeCrunchString());
		
		cv = new CacheValue("/parent/local",-60000, "hey");
		assertEquals("/-1/parent/-60000@local",cv.getTimeCrunchString());
	}
}
