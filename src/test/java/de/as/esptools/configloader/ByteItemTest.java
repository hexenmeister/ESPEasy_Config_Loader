package de.as.esptools.configloader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.ByteItem;
import de.as.esptools.configloader.datatypes.DataImportException;

public class ByteItemTest {

	private ByteItem inst;

	@Before
	public void setUp() throws Exception {
		this.inst = new ByteItem();
	}

	@Test
	public void testName() {
		Assert.assertEquals("byte", inst.getTypeName());
	}

	@Test
	public void testTypeExport() throws DataImportException {
		// TODO Test
//		inst.importDataString("A");
//		Assert.assertEquals("char[1] : A", inst.exportTypeAndDataString(false));
//		Assert.assertEquals("char[1] :          A", inst.exportTypeAndDataString(true));
//
//		inst2.importDataString("A");
//		Assert.assertEquals("char[8] : A", inst.exportTypeAndDataString(false));
//		Assert.assertEquals("char[8] :          A", inst.exportTypeAndDataString(true));
	}
	
	@Test
	public void testImportExportString() throws DataImportException {
		inst.importDataString("FF");
		Assert.assertEquals("FF", inst.exportDataString());

		inst.importDataString("00");
		Assert.assertEquals("00", inst.exportDataString());

		inst.importDataString("01");
		Assert.assertEquals("01", inst.exportDataString());

		inst.importDataString("0A");
		Assert.assertEquals("0A", inst.exportDataString());

		inst.importDataString("C0");
		Assert.assertEquals("C0", inst.exportDataString());

		inst.importDataString("99");
		Assert.assertEquals("99", inst.exportDataString());

		inst.importDataString("DA");
		Assert.assertEquals("DA", inst.exportDataString());

		inst.importDataString("87");
		Assert.assertEquals("87", inst.exportDataString());
	}

}
