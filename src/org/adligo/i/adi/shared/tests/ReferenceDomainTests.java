package org.adligo.i.adi.shared.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.adligo.i.adi.shared.models.ReferenceDomain;
import org.adligo.i.log.shared.Log;
import org.adligo.i.log.shared.LogFactory;
import org.adligo.tests.ATest;
import org.adligo.tests.I_TimedTest;

public class ReferenceDomainTests extends ATest implements I_TimedTest {
	private static final Log log = LogFactory.getLog(ReferenceDomainTests.class);
	
	//three minutes is about 300 times the length that I am seeing
	// so adjust for some slower machines 
	private static final int MILLISECONDS_TO_RUN = 180000;
	private static List<ReferenceDomainChildInitRunnable> initRunnablesDone = 
		new ArrayList<ReferenceDomainChildInitRunnable>();
	private static boolean doneRunningInitThreads = false;
	
	private static List<ReferenceDomainToManyRunnable> toManyRunnablesDone = 
		new ArrayList<ReferenceDomainToManyRunnable>();
	private static boolean doneRunningToManyThreads = false;
	
	public void testReferenceDomain() {
		ReferenceDomain rd = new ReferenceDomain();
		rd.put("/usr", "usr");
		assertEquals("usr", rd.get("/usr"));
		rd.put("/usr", null);
		assertEquals("usr", rd.get("/usr"));
		rd.remove("/usr");
		assertNull(rd.get("/usr"));
		
		rd.put("/usr", "usr2");
		assertEquals("usr2", rd.get("/usr"));
		
		rd.put("/usr/local", "me");
		assertEquals("me", rd.get("/usr/local"));
		rd.remove("/usr/local");
		assertNull(rd.get("/usr/local"));
		
		rd.put("/usr/local/cvs", "code");
		assertEquals("code", rd.get("/usr/local/cvs"));
		rd.remove("/usr/local/cvs");
		assertNull(rd.get("/usr/local/cvs"));
	}
	
	public void testMultithreadedChildMapInitalizationTest() throws Exception {
		ReferenceDomainChildInitRunnable runA = new ReferenceDomainChildInitRunnable(0,"a");
		ReferenceDomainChildInitRunnable runB = new ReferenceDomainChildInitRunnable(1,"b");
		ReferenceDomainChildInitRunnable runC = new ReferenceDomainChildInitRunnable(2,"c");
		ReferenceDomainChildInitRunnable runD = new ReferenceDomainChildInitRunnable(3,"d");
		ReferenceDomainChildInitRunnable runE = new ReferenceDomainChildInitRunnable(4,"e");
		ReferenceDomainChildInitRunnable runF = new ReferenceDomainChildInitRunnable(5,"f");
		ReferenceDomainChildInitRunnable runG = new ReferenceDomainChildInitRunnable(6,"g");
		ReferenceDomainChildInitRunnable runH = new ReferenceDomainChildInitRunnable(7,"h");
		
		ReferenceDomainChildInitRunnable runI = new ReferenceDomainChildInitRunnable(8,"i");
		ReferenceDomainChildInitRunnable runJ = new ReferenceDomainChildInitRunnable(9,"j");
		ReferenceDomainChildInitRunnable runK = new ReferenceDomainChildInitRunnable(10,"k");
		ReferenceDomainChildInitRunnable runL = new ReferenceDomainChildInitRunnable(11,"l");
		ReferenceDomainChildInitRunnable runM = new ReferenceDomainChildInitRunnable(12,"m");
		ReferenceDomainChildInitRunnable runN = new ReferenceDomainChildInitRunnable(13,"n");
		ReferenceDomainChildInitRunnable runO = new ReferenceDomainChildInitRunnable(14,"o");
		ReferenceDomainChildInitRunnable runP = new ReferenceDomainChildInitRunnable(15,"p");
		
		Thread tA = new Thread(runA);
		Thread tB = new Thread(runB);
		Thread tC = new Thread(runC);
		Thread tD = new Thread(runD);
		Thread tE = new Thread(runE);
		Thread tF = new Thread(runF);
		Thread tG = new Thread(runG);
		Thread tH = new Thread(runH);
		
		Thread tI = new Thread(runI);
		Thread tJ = new Thread(runJ);
		Thread tK = new Thread(runK);
		Thread tL = new Thread(runL);
		Thread tM = new Thread(runM);
		Thread tN = new Thread(runN);
		Thread tO = new Thread(runO);
		Thread tP = new Thread(runP);
		
		tA.start();
		tB.start();
		tC.start();
		tD.start();
		tE.start();
		tF.start();
		tG.start();
		tH.start();
		
		tI.start();
		tJ.start();
		tK.start();
		tL.start();
		tM.start();
		tN.start();
		tO.start();
		tP.start();
		int times = 0;
		while (!doneRunningInitThreads) {
			Thread.sleep(1000);
			if (times * 1000 > MILLISECONDS_TO_RUN) {
				ATest.assertTrue("Test took to long.",false);
			}
		}
		assertMultiThreadResult();
	}

	public static synchronized  void rdChildInitRunDone(ReferenceDomainChildInitRunnable whichOne) {
		initRunnablesDone.add(whichOne);
		if (initRunnablesDone.size() == 16) {
			log.warn("do some testing ");
			doneRunningInitThreads = true;
		}
	}

	private static void assertMultiThreadResult() {
		//keep a basic assert here 
		ReferenceDomain rd = ReferenceDomainChildInitRunnable.referenceDomain;
		ATest.assertEquals("a", rd.get("/0"));
		ATest.assertEquals("b", rd.get("/1"));
		ATest.assertEquals("c", rd.get("/2"));
		ATest.assertEquals("d", rd.get("/3"));
		ATest.assertEquals("e", rd.get("/4"));
		ATest.assertEquals("f", rd.get("/5"));
		ATest.assertEquals("g", rd.get("/6"));
		ATest.assertEquals("h", rd.get("/7"));
		
		ATest.assertEquals("i", rd.get("/8"));
		ATest.assertEquals("j", rd.get("/9"));
		ATest.assertEquals("k", rd.get("/10"));
		ATest.assertEquals("l", rd.get("/11"));
		ATest.assertEquals("m", rd.get("/12"));
		ATest.assertEquals("n", rd.get("/13"));
		ATest.assertEquals("o", rd.get("/14"));
		ATest.assertEquals("p", rd.get("/15"));
		
		int totalAsserts = 0;
		//assert all values
		for (ReferenceDomainChildInitRunnable run: initRunnablesDone) {
			Map<String,String> contributions = run.getContributions();
			Set<String> keys = contributions.keySet();
			SortedSet<String> sortedKeys = new TreeSet<String>();
			sortedKeys.addAll(keys);
			
			for (String key : sortedKeys) {
				ATest.assertEquals(run.getValue(), rd.get(key));
				if (log.isDebugEnabled()) {
					log.debug("asserted " + run.getValue() + " for key " + key);
				}
				totalAsserts++;
			}
		}
		assertEquals(1616, totalAsserts);
	}
	
	@Override
	public void setTimedout() {
		ATest.assertTrue("Test timed out", false);
	}
	
	public void testToManyThreadsEditingAChildMap() throws Exception {
		for (int i = 0; i < 100; i++) {
			ReferenceDomainToManyRunnable run = new ReferenceDomainToManyRunnable(0, 1000, "/" + i);
			Thread t = new Thread(run);
			t.start();
		}
		int times = 0;
		while (!doneRunningToManyThreads) {
			Thread.sleep(1000);
			if (times * 1000 > MILLISECONDS_TO_RUN) {
				ATest.assertTrue("Test took to long.",false);
			}
		}
		//not nothing to assert here, just seeing what impact thread contention 
		// would have for 100 threads writing when the default suggestion is 16
		// for java.util.concurrent.ConcurrentHashMap
	}
	
	public static synchronized void toManyThreadsEditingAChildMapThreadDone(ReferenceDomainToManyRunnable p) {
		toManyRunnablesDone.add(p);
		if (toManyRunnablesDone.size() == 100) {
			doneRunningToManyThreads = true;
		}
	}
}
