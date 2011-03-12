package org.adligo.i.adi;

import org.adligo.i.adi.client.models.ReferenceDomain;
import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;

/**
 * this tests the overflow of the default 16 thread write suggestion
 * on java.util.concurrent.ConcurrentHashMap
 * to see what it does when you have 100 threads writing
 * and it didn't have a noticeable impact (in line with the javadoc)
 * 
 * @author scott
 *
 */
public class ReferenceDomainToManyRunnable implements Runnable {
	private static final Log log = LogFactory.getLog(ReferenceDomainToManyRunnable.class);
	
	static final ReferenceDomain referenceDomain = new ReferenceDomain();
	private int start_id;
	private int end_id;
	private String path;
	
	public ReferenceDomainToManyRunnable(int p_start_id, int p_end_id, String p_path){
		start_id = p_start_id;
		end_id = p_end_id;
		path = p_path;
	}
	
	@Override
	public void run() {
		for (int i = start_id; i <= end_id; i++) {
			referenceDomain.put(path, i);
			if (log.isDebugEnabled()) {
				log.debug("writing " + path + ", " + i);
			}
		}
		ReferenceDomainTests.toManyThreadsEditingAChildMapThreadDone(this);
	}
	
	public String getPath() {
		return path;
	}


}
