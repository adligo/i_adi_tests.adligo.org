package org.adligo.i.adi_tests.shared;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.adligo.i.adi.shared.models.ReferenceDomain;
import org.adligo.i.log.shared.Log;
import org.adligo.i.log.shared.LogFactory;

/**
 * this populates the referenceDomain with its value under the paths;
 * "", "/0"-"/100", "/0/0"-"/100/100"
 * so if it's value was a and i was 1
 * it would populate
 * "/1",a 
 * "/0/1", a
 * "/1/1", a
 * "/2/1", a
 * ....exc...
 * "/98/1", a
 * "/99/1", a
 * 
 * This way I can assert that I did actually overcome what appears to be a 
 * double checked locking pattern at first glance but is NOT
 * double checked locking (just a similar working pattern)
 * 
 * @author scott
 *
 */
public class ReferenceDomainChildInitRunnable implements Runnable {
	private static final Log log = LogFactory.getLog(ReferenceDomainChildInitRunnable.class);
	
	static final ReferenceDomain referenceDomain = new ReferenceDomain();
	private int id;
	private String value;
	private Map<String, String> contributions = new HashMap<String, String>();
	
	public ReferenceDomainChildInitRunnable(int pId, String p_value){
		value = p_value;
		id = pId;
	}
	
	@Override
	public void run() {
		StringBuilder sbRoot = new StringBuilder();
		sbRoot.append("/");
		sbRoot.append(id);
		String root = sbRoot.toString();
		referenceDomain.put(root, value);
		contributions.put(root, value);
		
		for (int i = 0; i < 100; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append("/");
			sb.append(i);
			sb.append(sbRoot);
			String top = sb.toString();
			referenceDomain.put(top, value);
			contributions.put(top, value);
			if (log.isDebugEnabled()) {
				log.debug(" appended top " + top + " value " + value);
			}
			/*
			for (int j = 0; j < 101; j++) {
				StringBuilder subB = new StringBuilder();
				subB.append(top);
				subB.append("/");
				subB.append(j);
				
				String sub = subB.toString();
				referenceDomain.put(sub, value);
				contributions.put(sub, value);
			}*/
		}
		ReferenceDomainTests.rdChildInitRunDone(this);
	}
	
	public Map<String, String> getContributions() {
		return Collections.unmodifiableMap(contributions);
	}

	public String getValue() {
		return value;
	}


}
