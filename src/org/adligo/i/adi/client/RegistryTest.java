package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;
import org.adligo.tests.ATest;

public class RegistryTest extends ATest {
	private static final String KEY_A = "key_a";
	private static final Log log = LogFactory.getLog(RegistryTest.class);
	
	private static final String INVOKER_A_RETURN = "INVOKER_A";
	private static final I_Invoker INVOKER_A = new I_Invoker() {
		public Object invoke(Object valueObject) {
			return INVOKER_A_RETURN;
		}
	};	

	private static final String CHECKED_INVOKER_A_RETURN = "CHECKED_INVOKER_A";
	private static final I_CheckedInvoker CHECKED_INVOKER_A = new I_CheckedInvoker() {
		public Object invoke(Object valueObject) {
			return CHECKED_INVOKER_A_RETURN;
		}
	};	

	private static final String KEY_B = "key_b";
	
	private static final String INVOKER_B_RETURN = "INVOKER_B";
	private static final I_Invoker INVOKER_B = new I_Invoker() {
		public Object invoke(Object valueObject) {
			return INVOKER_B_RETURN;
		}
	};	

	private static final String CHECKED_INVOKER_B_RETURN = "CHECKED_INVOKER_B";
	private static final I_CheckedInvoker CHECKED_INVOKER_B = new I_CheckedInvoker() {
		public Object invoke(Object valueObject) {
			return CHECKED_INVOKER_B_RETURN;
		}
	};
	
	public void testAddInvoker() {
		Registry.clear();
		Registry.debug();
		
		assertAddA();
	}

	private void assertAddA() {
		I_Map map = MapFactory.create();
		map.put(KEY_A, INVOKER_A);
		
		Registry.addInvokerDelegates(map);
		
		I_Invoker isInA = Registry.getInvoker(KEY_A);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
	}
	
	public void testAddCheckedInvokerToInvokerMethod() {
		
		I_Map map = MapFactory.create();
		map.put(KEY_A, CHECKED_INVOKER_A);
		
		ClassCastException cce = null;
		try {
			// adding checked invoker to invokerMethod
			Registry.addInvokerDelegates(map);
		} catch (ClassCastException x) {
			cce = x;
			//log.error(x.getMessage(), x);
		}
		
		assertNotNull("This should have thrown a class cast " +
				"exception I_Invoker from I_CheckedInvoker "
				, cce);
	}
	
	public void testAddChecked() {
		assertAddCheckedA();
	}
	
	private void assertAddCheckedA() {
		I_Map map = MapFactory.create();
		map.put(KEY_A, CHECKED_INVOKER_A);
		
		Registry.addCheckedInvokerDelegates(map);
		
		I_CheckedInvoker isInA = Registry.getCheckedInvoker(KEY_A);
		try {
			assertEquals(CHECKED_INVOKER_A_RETURN, isInA.invoke(null));
		} catch (InvocationException x) {
			log.error(x.getMessage(), x);
			assertFalse("Exception " + x.getMessage(), true);
		}
	}
	
	public void testAddInvokerToCheckedInvokerMethod() {
		
		I_Map map = MapFactory.create();
		map.put(KEY_A, INVOKER_A);
		
		ClassCastException cce = null;
		try {
			// adding checked invoker to invokerMethod
			Registry.addCheckedInvokerDelegates(map);
		} catch (ClassCastException x) {
			cce = x;
			//log.error(x.getMessage(), x);
		}
		
		assertNotNull("This should have thrown a class cast " +
				"exception I_Invoker from I_CheckedInvoker "
				, cce);
	}
	
	public void assertReplaceA_withB_withA() {
		Registry.clear();
		Registry.debug();
		
		I_Map map = MapFactory.create();
		map.put(KEY_A, INVOKER_A);
		
		Registry.replaceInvokerDelegates(map);
		
		I_Invoker isInA = Registry.getInvoker(KEY_A);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
		
		
		map.put(KEY_A, INVOKER_B);
		Registry.replaceInvokerDelegates(map);
		assertEquals(INVOKER_B_RETURN, isInA.invoke(null));
		
		map.put(KEY_A, INVOKER_A);
		Registry.replaceInvokerDelegates(map);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
		
	}
	
	public void assertCheckedReplaceA_withB_withA() {
		Registry.clear();
		Registry.debug();
		
		I_Map map = MapFactory.create();
		map.put(KEY_A, INVOKER_A);
		
		Registry.replaceCheckedInvokerDelegates(map);
		
		I_Invoker isInA = Registry.getInvoker(KEY_A);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
		
		
		map.put(KEY_A, INVOKER_B);
		Registry.replaceCheckedInvokerDelegates(map);
		assertEquals(INVOKER_B_RETURN, isInA.invoke(null));
		
		map.put(KEY_A, INVOKER_A);
		Registry.replaceCheckedInvokerDelegates(map);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
		
	}
}
