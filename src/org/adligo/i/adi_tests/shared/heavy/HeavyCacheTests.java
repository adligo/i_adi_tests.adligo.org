package org.adligo.i.adi_tests.shared.heavy;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.ProxyInvoker;
import org.adligo.i.adi.shared.Registry;
import org.adligo.i.adi.shared.heavy.HeavyCacheReader;
import org.adligo.i.adi.shared.heavy.HeavyCacheRemover;
import org.adligo.i.adi.shared.heavy.HeavyCacheWriter;
import org.adligo.i.adi.shared.models.CacheRemoverToken;
import org.adligo.i.adi.shared.models.CacheWriterToken;
import org.adligo.i.adi_tests.shared.MockClock;
import org.adligo.tests.ATest;

public class HeavyCacheTests extends ATest {
	private static final long TIME_0 = 0;
	private static final long TIME_1 = 1;
	private static final long TIME_2 = 2;
	private static final long TIME_3 = 3;
	private static final long TIME_4 = 4;
	private static final long TIME_5 = 60005;
	private static final long TIME_6 = 60006;
	private static final long TIME_7 = 60007;
	
	private static final long TIME_8 = 1200005;
	private static final long TIME_9 = 1200005;
	private static final long TIME_10 = 1200005;
	
	private static final String VALUE_1 = "value#1";
	private static final String KEY_1 = "/key#1";


	public void testCacheInteraction() {
		I_Invoker CACHE_WRITER = Registry.getInvoker(InvokerNames.CACHE_WRITER);
		I_Invoker CACHE_READER = Registry.getInvoker(InvokerNames.CACHE_READER);
		I_Invoker CACHE_REMOVER = Registry.getInvoker(InvokerNames.CACHE_REMOVER);
		assertCacheImpls(CACHE_WRITER, CACHE_READER, CACHE_REMOVER);
		getAndAssertMockClock();
		
		
		
		Exception caught = null;
		try {
			CACHE_WRITER.invoke("String");
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals("org.adligo.i.adi.client.heavy.HeavyCacheWriter takes a org.adligo.i.adi.client.models.CacheWriterToken and you passed it a String", 
				caught.getMessage());
		
		CacheWriterToken token = new CacheWriterToken();
		token.setName(KEY_1);
		token.setValue(VALUE_1);
		MockClock.INSTANCE.setTime(TIME_1);
		
		//should write
		CACHE_WRITER.invoke(token);
		assertEquals(VALUE_1, CACHE_READER.invoke(KEY_1));
		
		//shouldn't write
		token.setSetPolicy(CacheWriterToken.ADD_ONLY_IF_NOT_PRESENT);
		MockClock.INSTANCE.setTime(TIME_2);
		token.setValue(VALUE_1 + "a");
		CACHE_WRITER.invoke(token);
		assertEquals(VALUE_1, CACHE_READER.invoke(KEY_1));
		
		//should replace
		token.setSetPolicy(CacheWriterToken.REPLACE_ONLY_IF_PRESENT);
		MockClock.INSTANCE.setTime(TIME_3);
		token.setValue(VALUE_1 + "b");
		CACHE_WRITER.invoke(token);
		assertEquals(VALUE_1 + "b", CACHE_READER.invoke(KEY_1));
		
		String readResultOne = (String) CACHE_READER.invoke(KEY_1);
		assertEquals(VALUE_1 + "b", readResultOne);
		
		//shouldn't remove
		CacheRemoverToken removalToken = new CacheRemoverToken();
		removalToken.setStaleDate(TIME_0);
		removalToken.setType(CacheRemoverToken.SWEEP_ALL_TYPE);
		CACHE_REMOVER.invoke(removalToken);
		
		readResultOne = (String) CACHE_READER.invoke(KEY_1);
		assertEquals(VALUE_1 + "b", readResultOne);
		
		//should remove (anything older than some time ago 3)
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
	}

	private void getAndAssertMockClock() {
		Registry.addOrReplaceInvoker(InvokerNames.CLOCK, 
				MockClock.INSTANCE);
		I_Invoker clock = HeavyCacheWriter.getCLOCK();
		assertTrue(clock instanceof ProxyInvoker);
		I_Invoker delegate = ((ProxyInvoker) clock).getDelegate();
		assertTrue(delegate instanceof MockClock);
	}

	private void assertCacheImpls(I_Invoker CACHE_WRITER,
			I_Invoker CACHE_READER, I_Invoker CACHE_REMOVER) {
		assertTrue(CACHE_WRITER instanceof ProxyInvoker);
		I_Invoker delegate = ((ProxyInvoker) CACHE_WRITER).getDelegate();
		assertTrue(delegate instanceof HeavyCacheWriter);
		assertTrue(CACHE_READER instanceof ProxyInvoker);
		delegate = ((ProxyInvoker) CACHE_READER).getDelegate();
		assertTrue(delegate instanceof HeavyCacheReader);
		assertTrue(CACHE_REMOVER instanceof ProxyInvoker);
		delegate = ((ProxyInvoker) CACHE_REMOVER).getDelegate();
		assertTrue(delegate instanceof HeavyCacheRemover);
	}
	
	public void testCacheInteractionOver3Minutes() {
		I_Invoker CACHE_WRITER = Registry.getInvoker(InvokerNames.CACHE_WRITER);
		I_Invoker CACHE_READER = Registry.getInvoker(InvokerNames.CACHE_READER);
		I_Invoker CACHE_REMOVER = Registry.getInvoker(InvokerNames.CACHE_REMOVER);
		assertCacheImpls(CACHE_WRITER, CACHE_READER, CACHE_REMOVER);
		getAndAssertMockClock();
		
		MockClock.INSTANCE.setTime(TIME_1);
		CacheWriterToken token = new CacheWriterToken();
		token.setName("/0/1");
		token.setValue("a");
		CACHE_WRITER.invoke(token);
		
		MockClock.INSTANCE.setTime(TIME_2);
		token.setName("/0/2");
		token.setValue("b");
		CACHE_WRITER.invoke(token);
		
		MockClock.INSTANCE.setTime(TIME_3);
		token.setName("/0/3");
		token.setValue("c");
		CACHE_WRITER.invoke(token);
		
		MockClock.INSTANCE.setTime(TIME_5);
		token.setName("/0/5");
		token.setValue("d");
		CACHE_WRITER.invoke(token);
		
		MockClock.INSTANCE.setTime(TIME_6);
		token.setName("/0/6");
		token.setValue("e");
		CACHE_WRITER.invoke(token);
		
		MockClock.INSTANCE.setTime(TIME_7);
		token.setName("/0/7");
		token.setValue("f");
		CACHE_WRITER.invoke(token);
		
		MockClock.INSTANCE.setTime(TIME_8);
		token.setName("/0/8");
		token.setValue("g");
		CACHE_WRITER.invoke(token);
		
		MockClock.INSTANCE.setTime(TIME_9);
		token.setName("/0/9");
		token.setValue("h");
		CACHE_WRITER.invoke(token);
		
		MockClock.INSTANCE.setTime(TIME_10);
		token.setName("/0/10");
		token.setValue("i");
		CACHE_WRITER.invoke(token);
		
		assertEquals("a", CACHE_READER.invoke("/0/1"));
		assertEquals("b", CACHE_READER.invoke("/0/2"));
		assertEquals("c", CACHE_READER.invoke("/0/3"));
		assertEquals("d", CACHE_READER.invoke("/0/5"));
		assertEquals("e", CACHE_READER.invoke("/0/6"));
		assertEquals("f", CACHE_READER.invoke("/0/7"));
		assertEquals("g", CACHE_READER.invoke("/0/8"));
		assertEquals("h", CACHE_READER.invoke("/0/9"));
		assertEquals("i", CACHE_READER.invoke("/0/10"));
		
		CacheRemoverToken crToken = new CacheRemoverToken();
		crToken.setType(CacheRemoverToken.SWEEP_ALL_TYPE);
		crToken.setStaleDate(TIME_6);
		CACHE_REMOVER.invoke(crToken);
		
		assertNull(CACHE_READER.invoke("/0/1"));
		assertNull(CACHE_READER.invoke("/0/2"));
		assertNull(CACHE_READER.invoke("/0/3"));
		assertNull(CACHE_READER.invoke("/0/5"));
		assertNull(CACHE_READER.invoke("/0/6"));
		
		assertEquals("f", CACHE_READER.invoke("/0/7"));
		assertEquals("g", CACHE_READER.invoke("/0/8"));
		assertEquals("h", CACHE_READER.invoke("/0/9"));
		assertEquals("i", CACHE_READER.invoke("/0/10"));
	}
}
