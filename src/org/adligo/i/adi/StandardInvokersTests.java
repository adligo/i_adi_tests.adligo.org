package org.adligo.i.adi;

import junit.framework.TestCase;

import org.adligo.i.adi.client.BaseConfigProvider;
import org.adligo.i.adi.client.CacheReader;
import org.adligo.i.adi.client.CacheRemover;
import org.adligo.i.adi.client.CacheWriter;
import org.adligo.i.adi.client.I18nConstantsFactory;
import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.InvokerNames;
import org.adligo.i.adi.client.ProxyInvoker;
import org.adligo.i.adi.client.SimpleClock;
import org.adligo.i.adi.client.SimpleSystemErr;
import org.adligo.i.adi.client.SimpleSystemOut;
import org.adligo.i.adi.client.StandardInvokers;

public class StandardInvokersTests extends TestCase {

	public void testCacheWriter() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.CACHE_WRITER);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(CacheWriter.class, pi.getDelegate().getClass());
	}
	
	public void testCacheReader() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.CACHE_READER);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(CacheReader.class, pi.getDelegate().getClass());
	}
	
	public void testCacheRemover() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.CACHE_REMOVER);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(CacheRemover.class, pi.getDelegate().getClass());
	}
	
	public void testConfigProvider() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.CONFIGURATION_PROVIDER);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(BaseConfigProvider.class, pi.getDelegate().getClass());
	}
	
	public void testClock() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.CLOCK);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(SimpleClock.class, pi.getDelegate().getClass());
	}
	
	public void testOut() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.OUT);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(SimpleSystemOut.class, pi.getDelegate().getClass());
	}
	
	public void testErr() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.ERR);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(SimpleSystemErr.class, pi.getDelegate().getClass());
	}
	
	public void testI18nConstantsFactory() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.CONSTANTS_FACTORY);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(I18nConstantsFactory.class, pi.getDelegate().getClass());
	}
}
