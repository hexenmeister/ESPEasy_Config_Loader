package de.as.esptools.configloader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.as.esptools.configloader.datatypes.NumericArrayItemTest;

@RunWith(Suite.class)
@SuiteClasses({ DataItemTest.class, ByteArrayItemTest.class, CharArrayItemTest.class, NumericArrayItemTest.class })
public class AllTests {

}
