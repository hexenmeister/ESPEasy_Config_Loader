package de.as.esptools.configloader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UtilTest.class, DataItemTest.class, ArrayDataItemTest.class, ByteArrayItemTest.class,
		ByteItemTest.class, CharArrayItemTest.class, CharItemTest.class, NumericItemTest.class, BooleanItemTest.class,
		BooleanArrayItemTest.class, DataItemFactoryTest.class, FloatItemTest.class, FloatArrayItemTest.class,
		LongItemTest.class, LongArrayItemTest.class, IntItemTest.class, IntArrayItemTest.class, Int16ItemTest.class,
		Int16ArrayItemTest.class, Int8ItemTest.class, Int8ArrayItemTest.class, DataStructureTest.class,
		TypeLineParserOldTest.class, TypeLineParserTest.class })
public class AllTests {

}
