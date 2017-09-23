package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.ByteArrayItem;
import de.as.esptools.configloader.datatypes.DataImportException;

public class ByteArrayItemTest {

	private ByteArrayItem inst;
	private ByteArrayItem inst2;

	@Before
	public void setUp() throws Exception {
		inst = new ByteArrayItem(1);
		inst2 = new ByteArrayItem(8);
	}

	@Test
	public void testName() {
		Assert.assertEquals("byte", inst.getTypeName());
	}

	@Test
	public void testSingleImportExportString() throws DataImportException {
		inst.importDataString("FF");

		Assert.assertEquals("FF", inst.exportDataString());
	}

	@Test
	public void testMultipleImportExportString() throws DataImportException {
		inst2.importDataString("00 10 20 30 FF 50 60 DD");

		Assert.assertEquals("00 10 20 30 FF 50 60 DD", inst2.exportDataString());

		try {
			// to short
			inst2.importDataString("00 10 20 30 FF 50 60");
			fail("to short data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			// to short
			inst2.importDataString("00 10 20 30 FF 50 60 DD FF");
			fail("to long data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			inst.importDataString("test");
			fail("invalid data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}
	}
}
