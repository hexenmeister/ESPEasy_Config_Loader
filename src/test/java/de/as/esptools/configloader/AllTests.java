package de.as.esptools.configloader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UtilTest.class, DataItemTest.class, ByteArrayItemTest.class, CharArrayItemTest.class,
		NumericArrayItemTest.class })
public class AllTests {

}
