package org.adligo.i.adi_tests.shared.light;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.ProxyInvoker;
import org.adligo.i.adi.shared.Registry;
import org.adligo.i.adi.shared.light.CacheWriter;
import org.adligo.i.adi.shared.light.LightStandardInvokers;
import org.adligo.i.adi.shared.models.CacheRemoverToken;
import org.adligo.i.adi.shared.models.CacheWriterToken;
import org.adligo.i.adi_tests.shared.MockClock;
import org.adligo.tests.ATest;

public class CacheTests extends ATest {
	private static final long TIME_1 = 1;
	private static final long TIME_2 = 2;
	private static final long TIME_3 = 3;
	private static final long TIME_4 = 4;
	private static final long TIME_5 = 5;
	
	private static final String VALUE_1 = "value#1";
	private static final String KEY_1 = "/key#1";


	
	public void testCacheInteraction() {
		I_Invoker CACHE_WRITER = LightStandardInvokers.get(InvokerNames.CACHE_WRITER);
		Registry.addOrReplaceInvoker(InvokerNames.CLOCK, MockClock.INSTANCE);
		I_Invoker wtfClock = CacheWriter.getCLOCK();
		assertTrue(wtfClock instanceof ProxyInvoker);
		I_Invoker delegate = ((ProxyInvoker) wtfClock).getDelegate();
		assertTrue(delegate instanceof MockClock);
		
		I_Invoker CACHE_READER = LightStandardInvokers.get(InvokerNames.CACHE_READER);
		I_Invoker CACHE_REMOVER = LightStandardInvokers.get(InvokerNames.CACHE_REMOVER);
		
		CacheRemoverToken removerSizeToken = new CacheRemoverToken();
		removerSizeToken.setType(CacheRemoverToken.GET_SIZE_TYPE);
		assertEquals(0, CACHE_REMOVER.invoke(removerSizeToken));
		
		Exception caught = null;
		try {
			CACHE_WRITER.invoke("String");
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals("org.adligo.i.adi.shared.light.CacheWriter takes a org.adligo.i.adi.shared.models.CacheWriterToken and you passed it a String", 
				caught.getMessage());
		removerSizeToken.setType(CacheRemoverToken.GET_SIZE_TYPE);
		assertEquals(0, CACHE_REMOVER.invoke(removerSizeToken));
		
		CacheWriterToken token = new CacheWriterToken();
		token.setName(KEY_1);
		token.setValue(VALUE_1);
		MockClock.INSTANCE.setTime(TIME_1);
		
		//should write
		CACHE_WRITER.invoke(token);
		assertEquals(VALUE_1, CACHE_READER.invoke(KEY_1));
		removerSizeToken.setType(CacheRemoverToken.GET_SIZE_TYPE);
		assertEquals(1, CACHE_REMOVER.invoke(removerSizeToken));
		
		//shouldn't write
		token.setSetPolicy(CacheWriterToken.ADD_ONLY_IF_NOT_PRESENT);
		MockClock.INSTANCE.setTime(TIME_2);
		token.setValue(VALUE_1 + "a");
		CACHE_WRITER.invoke(token);
		assertEquals(VALUE_1, CACHE_READER.invoke(KEY_1));
		removerSizeToken.setType(CacheRemoverToken.GET_SIZE_TYPE);
		assertEquals(1, CACHE_REMOVER.invoke(removerSizeToken));
		
		//should replace
		token.setSetPolicy(CacheWriterToken.REPLACE_ONLY_IF_PRESENT);
		MockClock.INSTANCE.setTime(TIME_3);
		token.setValue(VALUE_1 + "b");
		CACHE_WRITER.invoke(token);
		assertEquals(VALUE_1 + "b", CACHE_READER.invoke(KEY_1));
		removerSizeToken.setType(CacheRemoverToken.GET_SIZE_TYPE);
		assertEquals(1, CACHE_REMOVER.invoke(removerSizeToken));
		
		String readResultOne = (String) CACHE_READER.invoke(KEY_1);
		assertEquals(VALUE_1 + "b", readResultOne);
		
		//shouldn't remove
		CacheRemoverToken removalToken = new CacheRemoverToken();
		removalToken.setStaleDate(TIME_5);
		removalToken.setType(CacheRemoverToken.SWEEP_ALL_TYPE);
		CACHE_REMOVER.invoke(removalToken);
		
		removerSizeToken.setType(CacheRemoverToken.GET_SIZE_TYPE);
		assertEquals(1, CACHE_REMOVER.invoke(removerSizeToken));
		readResultOne = (String) CACHE_READER.invoke(KEY_1);
		assertEquals(VALUE_1 + "b", readResultOne);
		
		//should remove (anything older than some time ago)
		removalToken.setStaleDate(TIME_3);
		CACHE_REMOVER.invoke(removalToken);
		
		readResultOne = (String) CACHE_READER.invoke(KEY_1);
		assertNull(readResultOne);
		
		token = new CacheWriterToken();
		token.setName(KEY_1);
		token.setValue(VALUE_1);
		MockClock.INSTANCE.setTime(TIME_4);
		CACHE_WRITER.invoke(token);
		
		readResultOne = (String) CACHE_READER.invoke(KEY_1);
		assertEquals(VALUE_1, readResultOne);
		//remove by key
		removalToken.setType(CacheRemoverToken.REMOVE_LIST_TYPE);
		removalToken.addKey(KEY_1);
		CACHE_REMOVER.invoke(removalToken);
		
		readResultOne = (String) CACHE_READER.invoke(KEY_1);
		assertNull(readResultOne);
		
		removerSizeToken.setType(CacheRemoverToken.GET_SIZE_TYPE);
		assertEquals(0, CACHE_REMOVER.invoke(removerSizeToken));
		
	}
}
