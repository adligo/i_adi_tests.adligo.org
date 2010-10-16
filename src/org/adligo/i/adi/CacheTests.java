package org.adligo.i.adi;

import org.adligo.i.adi.client.Cache;
import org.adligo.i.adi.client.CacheReader;
import org.adligo.i.adi.client.CacheRemover;
import org.adligo.i.adi.client.CacheWriter;
import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.InvokerNames;
import org.adligo.i.adi.client.ProxyInvoker;
import org.adligo.i.adi.client.Registry;
import org.adligo.i.adi.client.models.CacheRemoverToken;
import org.adligo.i.adi.client.models.CacheWriterToken;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;
import org.adligo.tests.ATest;

public class CacheTests extends ATest {
	private static final long TIME_1 = 1;
	private static final long TIME_2 = 2;
	private static final long TIME_3 = 3;
	private static final long TIME_4 = 4;
	private static final long TIME_5 = 5;
	
	private static final String VALUE_1 = "value#1";
	private static final String KEY_1 = "/key#1";

	public void testRegistryInvokers() {
		setUp();
		
		I_Invoker CLOCK = Registry.getInvoker(InvokerNames.CLOCK);
		assertTrue(CLOCK instanceof ProxyInvoker);
		I_Invoker delegate = ((ProxyInvoker) CLOCK).getDelegate();
		assertTrue(delegate instanceof MockClock);
		
		I_Invoker CACHE_WRITER = Registry.getInvoker(InvokerNames.CACHE_WRITER);
		assertTrue(CACHE_WRITER instanceof ProxyInvoker);
		delegate = ((ProxyInvoker) CACHE_WRITER).getDelegate();
		assertTrue(delegate instanceof CacheWriter);
		
		I_Invoker CACHE_READER = Registry.getInvoker(InvokerNames.CACHE_READER);
		assertTrue(CACHE_READER instanceof ProxyInvoker);
		delegate = ((ProxyInvoker) CACHE_READER).getDelegate();
		assertTrue(delegate instanceof CacheReader);
		
		I_Invoker CACHE_REMOVER = Registry.getInvoker(InvokerNames.CACHE_REMOVER);
		assertTrue(CACHE_REMOVER instanceof ProxyInvoker);
		delegate = ((ProxyInvoker) CACHE_REMOVER).getDelegate();
		assertTrue(delegate instanceof CacheRemover);
	}

	public void setUp() {
		I_Map map = MapFactory.create();
		map.put(InvokerNames.CLOCK, MockClock.INSTANCE);
		map.put(InvokerNames.CACHE_WRITER, CacheWriter.INSTANCE);
		map.put(InvokerNames.CACHE_READER, CacheReader.INSTANCE);
		map.put(InvokerNames.CACHE_REMOVER, CacheRemover.INSTANCE);
		
		Registry.addOrReplaceInvokers(map);
	}
	
	public void testCacheInteraction() {
		//add extra call for gwt unit test
		setUp();
		I_Invoker CACHE_WRITER = Registry.getInvoker(InvokerNames.CACHE_WRITER);
		assertTrue(CACHE_WRITER instanceof ProxyInvoker);
		I_Invoker delegate = ((ProxyInvoker) CACHE_WRITER).getDelegate();
		assertTrue(delegate instanceof CacheWriter);
		I_Invoker wtfClock = CacheWriter.getCLOCK();
		assertTrue(wtfClock instanceof ProxyInvoker);
		delegate = ((ProxyInvoker) wtfClock).getDelegate();
		assertTrue(delegate instanceof MockClock);
		
		I_Invoker CACHE_READER = Registry.getInvoker(InvokerNames.CACHE_READER);
		I_Invoker CACHE_REMOVER = Registry.getInvoker(InvokerNames.CACHE_REMOVER);
		
		Exception caught = null;
		try {
			CACHE_WRITER.invoke("String");
		} catch (Exception x) {
			caught = x;
		}
		assertNotNull(caught);
		assertEquals("org.adligo.i.adi.client.CacheWriter takes a org.adligo.i.adi.client.models.CacheWriterToken and you passed it a String", 
				caught.getMessage());
		
		CacheWriterToken token = new CacheWriterToken();
		token.setName(KEY_1);
		token.setValue(VALUE_1);
		MockClock.INSTANCE.setTime(TIME_1);
		
		//should write
		CACHE_WRITER.invoke(token);
		assertEquals(VALUE_1, Cache.getItem(KEY_1));
		Long time =  Cache.getTime(KEY_1);
		assertEquals(TIME_1, time.longValue());
		
		//shouldn't write
		token.setSetPolicy(CacheWriterToken.ADD_ONLY_IF_NOT_PRESENT);
		MockClock.INSTANCE.setTime(TIME_2);
		token.setValue(VALUE_1 + "a");
		CACHE_WRITER.invoke(token);
		assertEquals(VALUE_1, Cache.getItem(KEY_1));
		time =  Cache.getTime(KEY_1);
		assertEquals(TIME_1, time.longValue());
		
		//should replace
		token.setSetPolicy(CacheWriterToken.REPLACE_ONLY_IF_PRESENT);
		MockClock.INSTANCE.setTime(TIME_3);
		token.setValue(VALUE_1 + "b");
		CACHE_WRITER.invoke(token);
		assertEquals(VALUE_1 + "b", Cache.getItem(KEY_1));
		time =  Cache.getTime(KEY_1);
		assertEquals(TIME_3, time.longValue());
		
		String readResultOne = (String) CACHE_READER.invoke(KEY_1);
		assertEquals(VALUE_1 + "b", readResultOne);
		
		//shouldn't remove
		CacheRemoverToken removalToken = new CacheRemoverToken();
		removalToken.setStaleDate(TIME_5);
		removalToken.setType(CacheRemoverToken.SWEEP_ALL_TYPE);
		CACHE_REMOVER.invoke(removalToken);
		
		readResultOne = (String) CACHE_READER.invoke(KEY_1);
		assertEquals(VALUE_1 + "b", readResultOne);
		
		//should remove (anything older than some time ago)
		removalToken.setStaleDate(TIME_3);
		CACHE_REMOVER.invoke(removalToken);
		
		readResultOne = (String) CACHE_READER.invoke(KEY_1);
		assertNull(readResultOne);
		time =  Cache.getTime(KEY_1);
		assertNull(time);
		
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
		time =  Cache.getTime(KEY_1);
		assertNull(time);
	}
}
