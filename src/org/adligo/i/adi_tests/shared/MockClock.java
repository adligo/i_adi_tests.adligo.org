package org.adligo.i.adi_tests.shared;

import org.adligo.i.adi.shared.I_Invoker;

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

