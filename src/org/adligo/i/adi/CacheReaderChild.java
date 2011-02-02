package org.adligo.i.adi;

import org.adligo.i.adi.client.CacheReader;

/**
 * this just allows me to make the CacheReader.INSTANCE protected
 * @author scott
 *
 */
public class CacheReaderChild extends CacheReader {
	protected static final CacheReader INSTANCE = CacheReader.INSTANCE;
}
