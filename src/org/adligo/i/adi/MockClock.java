package org.adligo.i.adi;

import org.adligo.i.adi.client.I_Invoker;

public class MockClock implements I_Invoker {
	private static long time;
	
	@Override
	public Object invoke(Object valueObject) {
		return time;
	}

	public static void setTime(long p_time) {
		time = p_time;
	}
	
	public static long getTime() {
		return time;
	}
}

