package org.adligo.i.adi.heavy;

import org.adligo.i.adi.client.heavy.HeavyCacheReader;

/**
 * this just allows me to make the CacheReader.INSTANCE protected
 * @author scott
 *
 */
public class HeavyCacheReaderChild extends HeavyCacheReader {
	protected static final HeavyCacheReader INSTANCE = HeavyCacheReader.INSTANCE;
}
