package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.CharItem;
import de.as.esptools.configloader.datatypes.DataImportException;

public class CharItemTest {

	private CharItem inst;

	@Before
	public void setUp() throws Exception {
		inst = new CharItem();
	}

	@Test
	public void testName() {
		Assert.assertEquals("char", inst.getTypeName());
	}

	@Test
	public void testBinLength() {
		Assert.assertEquals(1, inst.getBinLength());
	}

	@Test
	public void testTypeExport() throws DataImportException {
		inst.importDataString("A");
		Assert.assertEquals("char : A", inst.exportTypeAndDataString(false));
		Assert.assertEquals("char :          A", inst.exportTypeAndDataString(true));
	}

	@Test
	public void testImportExportString() throws DataImportException {
		inst.importDataString("\"A\"");
		Assert.assertEquals("A", inst.exportDataString());

		inst.importDataString("A");
		Assert.assertEquals("A", inst.exportDataString());

		inst.importDataString("B");
		Assert.assertNotEquals("b", inst.exportDataString());

		inst.importDataString("");
		Assert.assertEquals("00", inst.exportHex());

		inst.importDataString("\"\"");
		Assert.assertEquals("00", inst.exportHex());

		try {
			inst.importDataString("ABCDEFGHIJ");
			// Assert.assertEquals("41 42 43 44 45 46 47 48",inst2.exportHex());
			fail("too long data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}

	}

}
