package org.adligo.i.adi;

import org.adligo.i.adi.client.I_Invoker;

public class MockClock implements I_Invoker {
	public static final MockClock INSTANCE = new MockClock();
	
	private MockClock() {}
	
	private long time;
	
	@Override
	public Object invoke(Object valueObject) {
		return time;
	}

	public void setTime(long p_time) {
		time = p_time;
	}
	
	public long getTime() {
		return time;
	}
}

