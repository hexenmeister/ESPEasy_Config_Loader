package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.CharArrayItem;
import de.as.esptools.configloader.datatypes.DataImportException;

public class CharArrayItemTest {

	private CharArrayItem inst;
	private CharArrayItem inst2;

	@Before
	public void setUp() throws Exception {
		inst = new CharArrayItem(1);
		inst2 = new CharArrayItem(8);
	}

	@Test
	public void testName() {
		Assert.assertEquals("char", inst.getTypeName());
	}

	@Test
	public void testSingleImportExportString() throws DataImportException {
		inst.importDataString("A");
		Assert.assertEquals("A", inst.exportDataString());

		inst.importDataString("B");
		Assert.assertNotEquals("b", inst.exportDataString());
	}

	@Test
	public void testMultipleImportExportString() throws DataImportException {
		inst2.importDataString("Test1234");
		Assert.assertEquals("Test1234", inst2.exportDataString());

		inst2.importDataString("ABC");
		Assert.assertEquals("41 42 43 00 00 00 00 00", inst2.exportHex());

		try {
			inst2.importDataString("ABCDEFGHIJ");
			// Assert.assertEquals("41 42 43 44 45 46 47 48",inst2.exportHex());
			fail("too long data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}
	}
}
