package org.adligo.i.adi.shared.tests;

import org.adligo.i.adi.shared.I_Invoker;

public class MockBooleanProvider implements I_Invoker {
	private static boolean is = false;

	public static boolean isIs() {
		return is;
	}

	public static void setIs(boolean is) {
		MockBooleanProvider.is = is;
	}

	@Override
	public Object invoke(Object valueObject) {
		return is;
	}
	
}
