package org.adligo.i.adi.shared.tests;

import org.adligo.i.adi.shared.BaseConfigProvider;
import org.adligo.i.adi.shared.I18nConstantsFactory;
import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.ProxyInvoker;
import org.adligo.i.adi.shared.Registry;
import org.adligo.i.adi.shared.SimpleClock;
import org.adligo.i.adi.shared.SimpleSystemErr;
import org.adligo.i.adi.shared.SimpleSystemOut;
import org.adligo.i.adi.shared.StandardInvokers;
import org.adligo.i.adi.shared.heavy.HeavyCacheReader;
import org.adligo.i.adi.shared.heavy.HeavyCacheRemover;
import org.adligo.i.adi.shared.heavy.HeavyCacheWriter;
import org.adligo.i.adi.shared.heavy.HeavyMemoryReader;
import org.adligo.i.adi.shared.heavy.HeavyMemoryWriter;
import org.adligo.tests.ATest;

public class StandardInvokersTests extends ATest {

	public void setUp() {
		Registry.addOrReplaceInvoker(InvokerNames.CLOCK, SimpleClock.INSTANCE);
	}
	
	public void testCacheWriter() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.CACHE_WRITER);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(HeavyCacheWriter.class, pi.getDelegate().getClass());
	}
	
	public void testCacheReader() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.CACHE_READER);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(HeavyCacheReader.class, pi.getDelegate().getClass());
	}
	
	public void testCacheRemover() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.CACHE_REMOVER);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(HeavyCacheRemover.class, pi.getDelegate().getClass());
	}
	
	public void testMemoryReader() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.MEMORY_READER);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(HeavyMemoryReader.class, pi.getDelegate().getClass());
	}
	
	
	public void testMemoryWriter() {
		I_Invoker in =  StandardInvokers.get(InvokerNames.MEMORY_WRITER);
		assertNotNull(in);
		assertEquals(ProxyInvoker.class, in.getClass());
		ProxyInvoker pi = (ProxyInvoker) in;
		assertNotNull(pi.getDelegate());
		assertEquals(HeavyMemoryWriter.class, pi.getDelegate().getClass());
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
