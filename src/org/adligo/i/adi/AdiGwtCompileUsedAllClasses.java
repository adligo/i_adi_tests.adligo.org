package org.adligo.i.adi;

import java.util.HashSet;
import java.util.Set;

import org.adligo.i.adi.client.AdiEntryPoint;
import org.adligo.i.util.GwtCompileUsedAllClassesAsserter;
import org.adligo.i.util.client.ClassUtils;
import org.adligo.i.util.client.UtilEntryPoint;
import org.adligo.tests.ATest;

public class AdiGwtCompileUsedAllClasses extends ATest {

	public void testAllClassesUsed() throws Exception {
		
		Set<String> ignore = new HashSet<String>();
		String utilEntryPointClassName = ClassUtils.getClassName(UtilEntryPoint.class);
		ignore.add(ClassUtils.getClassName(AdiEntryPoint.class));
		ignore.add(utilEntryPointClassName);
		
		Set<Class<?>> classes = GwtCompileUsedAllClassesAsserter.getClasses(
				"org.adligo.i.adi.client", ignore);
		assertEquals("there should be classes in org.adligo.i.adi.client ", 26, classes.size());
		
		GwtCompileUsedAllClassesAsserter holder = new GwtCompileUsedAllClassesAsserter();
		AdiEntryPoint entryPoint = new AdiEntryPoint(holder);
		entryPoint.onModuleLoad();
		
		
		assertCollectionEquals(classes, holder.getUsedClasses());
	}
}
