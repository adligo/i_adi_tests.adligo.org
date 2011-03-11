package org.adligo.i.adi.light;

import org.adligo.i.adi.client.light.CacheReader;

/**
 * this just allows me to make the CacheReader.INSTANCE protected
 * @author scott
 *
 */
public class CacheReaderChild extends CacheReader {
	protected static final CacheReader INSTANCE = CacheReader.INSTANCE;
}
