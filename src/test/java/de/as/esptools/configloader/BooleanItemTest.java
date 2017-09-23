package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.BooleanItem;
import de.as.esptools.configloader.datatypes.DataImportException;

public class BooleanItemTest {

	private BooleanItem inst;

	@Before
	public void setUp() throws Exception {
		this.inst = new BooleanItem();
	}

	@Test
	public void testName() {
		Assert.assertEquals("boolean", inst.getTypeName());
	}

	@Test
	public void testImportExportString() throws DataImportException {
		inst.importDataString("true");
		Assert.assertEquals("1", inst.exportDataString());

		inst.importDataString("false");
		Assert.assertEquals("0", inst.exportDataString());

		inst.importDataString("0");
		Assert.assertEquals("0", inst.exportDataString());

		inst.importDataString("1");
		Assert.assertEquals("1", inst.exportDataString());

		inst.importDataString("00");
		Assert.assertEquals("0", inst.exportDataString());

		inst.importDataString("01");
		Assert.assertEquals("1", inst.exportDataString());

		try {
			inst.importDataString("test");
			fail("invalid data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}
		
		try {
			inst.importDataString("");
			fail("to short data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}
		
		try {
			inst.importDataString("00 00");
			fail("to long data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}
	}

}
