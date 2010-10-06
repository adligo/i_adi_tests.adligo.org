package org.adligo.i.adi;

import org.adligo.i.adi.client.I_CheckedInvoker;
import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.InvocationException;
import org.adligo.i.adi.client.Registry;
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
		public Object invoke(Object valueObject) throws InvocationException {
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
		public Object invoke(Object valueObject) throws InvocationException {
			return CHECKED_INVOKER_B_RETURN;
		}
	};
	
	public void testAddInvoker() {
		
		Registry.debug();
		
		assertAddA();
	}

	private void assertAddA() {
		I_Map map = MapFactory.create();
		map.put(KEY_A, INVOKER_A);
		
		Registry.addInvokers(map);
		
		I_Invoker isInA = Registry.getInvoker(KEY_A);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
	}
	
	public void testAddCheckedInvokerToInvokerMethod() {
		
		I_Map map = MapFactory.create();
		map.put(KEY_A, CHECKED_INVOKER_A);
		
		ClassCastException cce = null;
		try {
			// adding checked invoker to invokerMethod
			Registry.addInvokers(map);
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
		
		Registry.addCheckedInvokers(map);
		
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
			Registry.addCheckedInvokers(map);
		} catch (ClassCastException x) {
			cce = x;
			//log.error(x.getMessage(), x);
		}
		
		assertNotNull("This should have thrown a class cast " +
				"exception I_Invoker from I_CheckedInvoker "
				, cce);
	}
	
	public void testReplaceA_withB_withA() {
		
		Registry.debug();
		
		I_Map map = MapFactory.create();
		map.put(KEY_A, INVOKER_A);
		
		Registry.addOrReplaceInvokers(map);
		
		I_Invoker isInA = Registry.getInvoker(KEY_A);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
		
		
		map.put(KEY_A, INVOKER_B);
		Registry.addOrReplaceInvokers(map);
		assertEquals(INVOKER_B_RETURN, isInA.invoke(null));
		
		map.put(KEY_A, INVOKER_A);
		Registry.addOrReplaceInvokers(map);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
		
	}
	
	public void testCheckedReplaceA_withB_withA() throws InvocationException {
		
		Registry.debug();
		
		I_Map map = MapFactory.create();
		map.put(KEY_A, CHECKED_INVOKER_A);
		
		Registry.addOrReplaceCheckedInvokers(map);
		
		I_CheckedInvoker isInA = Registry.getCheckedInvoker(KEY_A);
		final I_CheckedInvoker originalIsInA = isInA;
		assertEquals(CHECKED_INVOKER_A_RETURN, isInA.invoke(null));
		
		
		map.put(KEY_A, CHECKED_INVOKER_B);
		Registry.addOrReplaceCheckedInvokers(map);
		
		isInA = Registry.getCheckedInvoker(KEY_A);
		assertEquals(CHECKED_INVOKER_B_RETURN, isInA.invoke(null));
		assertTrue("Instances from the registry should match ",
				isInA == originalIsInA);
		
		map.put(KEY_A, CHECKED_INVOKER_A);
		Registry.addOrReplaceCheckedInvokers(map);
		
		assertEquals(CHECKED_INVOKER_A_RETURN, isInA.invoke(null));
		
		isInA = Registry.getCheckedInvoker(KEY_A);
		assertTrue("Instances from the registry should match ",
				isInA == originalIsInA);
	}
	
	public void testAddSingleInvoker() {
		
		Registry.addInvoker(KEY_A, INVOKER_A);
		
		I_Invoker isInA = Registry.getInvoker(KEY_A);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
		
		Registry.addInvoker(KEY_A, INVOKER_B);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
		
		
		Registry.addInvoker(KEY_B, INVOKER_B);
		I_Invoker isInB = Registry.getInvoker(KEY_B);
		assertEquals(INVOKER_B_RETURN, isInB.invoke(null));
	}
	
	public void testAddOrReplaceSingleInvoker() {
		
		Registry.addOrReplaceInvoker(KEY_A, INVOKER_A);
		
		I_Invoker isInA = Registry.getInvoker(KEY_A);
		assertEquals(INVOKER_A_RETURN, isInA.invoke(null));
		
		Registry.addOrReplaceInvoker(KEY_A, INVOKER_B);
		assertEquals(INVOKER_B_RETURN, isInA.invoke(null));
		
	}	
	
	
	public void testAddSingleCheckedInvoker() throws Exception {
		
		Registry.addCheckedInvoker(KEY_A, CHECKED_INVOKER_A);
		
		I_CheckedInvoker isInA = Registry.getCheckedInvoker(KEY_A);
		assertEquals(CHECKED_INVOKER_A_RETURN, isInA.invoke(null));
		
		Registry.addCheckedInvoker(KEY_A, CHECKED_INVOKER_B);
		assertEquals(CHECKED_INVOKER_A_RETURN, isInA.invoke(null));
		
		
		Registry.addCheckedInvoker(KEY_B, CHECKED_INVOKER_B);
		I_CheckedInvoker isInB = Registry.getCheckedInvoker(KEY_B);
		assertEquals(CHECKED_INVOKER_B_RETURN, isInB.invoke(null));
	}
	
	public void testAddOrReplaceSingleCheckedInvoker() throws Exception {
		
		Registry.addOrReplaceCheckedInvoker(KEY_A, CHECKED_INVOKER_A);
		
		I_CheckedInvoker isInA = Registry.getCheckedInvoker(KEY_A);
		assertEquals(CHECKED_INVOKER_A_RETURN, isInA.invoke(null));
		
		Registry.addOrReplaceCheckedInvoker(KEY_A, CHECKED_INVOKER_B);
		assertEquals(CHECKED_INVOKER_B_RETURN, isInA.invoke(null));
		
	}	
}
