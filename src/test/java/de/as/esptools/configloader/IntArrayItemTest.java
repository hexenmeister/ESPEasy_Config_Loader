package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.IntArrayItem;

public class IntArrayItemTest {

	private IntArrayItem insts;

	private IntArrayItem instu;

	@Before
	public void setUp() throws Exception {
		this.insts = new IntArrayItem(4, true);
		this.instu = new IntArrayItem(4, false);
	}

	@Test
	public void testName() {
		Assert.assertEquals("int[4]", insts.getTypeName());
		Assert.assertEquals("int[4]", instu.getTypeName());
	}

	@Test
	public void testTypeExportSigned() throws DataImportException {
		insts.importDataString("1 0 -0 -1");
		Assert.assertEquals("int[4] : 1 0 0 -1", insts.exportTypeAndDataString(false));

		insts.importDataString("127 127 127 127");
		Assert.assertEquals("int[4] : 127 127 127 127", insts.exportTypeAndDataString(false));

		insts.importDataString("-128 -128 -128 -128");
		Assert.assertEquals("int[4] : -128 -128 -128 -128", insts.exportTypeAndDataString(false));

		insts.importDataString("255 255 255 255");
		Assert.assertEquals("int[4] : 255 255 255 255", insts.exportTypeAndDataString(false));

		insts.importDataString("32767 32767 32767 32767");
		Assert.assertEquals("int[4] : 32767 32767 32767 32767", insts.exportTypeAndDataString(false));

		insts.importDataString("-32768 -32768 -32768 -32768");
		Assert.assertEquals("int[4] : -32768 -32768 -32768 -32768", insts.exportTypeAndDataString(false));

		insts.importDataString("1000 1000 1000 -1000");
		Assert.assertEquals("int[4] : 1000 1000 1000 -1000", insts.exportTypeAndDataString(false));

		insts.importDataString("123 456 789 098");
		Assert.assertEquals("int[4] : 123 456 789 98", insts.exportTypeAndDataString(false));

		insts.importDataString("-123 456 -789 098");
		Assert.assertEquals("int[4] : -123 456 -789 98", insts.exportTypeAndDataString(false));
	}

	@Test
	public void testTypeExportUnsigned() throws DataImportException {
		try {
			instu.importDataString("1 0 -0 -1");
			// Assert.assertEquals("int[4] : 1 0 0 -1",
			// instu.exportTypeAndDataString(false));
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			instu.importDataString("1000 1000 1000 -1000");
			// Assert.assertEquals("int[4] : 1000 1000 1000 -1000",
			// instu.exportTypeAndDataString(false));
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.importDataString("255 255 255 255");
		Assert.assertEquals("int[4] : 255 255 255 255", instu.exportTypeAndDataString(false));

		instu.importDataString("32767 32767 32767 32767");
		Assert.assertEquals("int[4] : 32767 32767 32767 32767", instu.exportTypeAndDataString(false));

		instu.importDataString("65535 65535 65535 65535");
		Assert.assertEquals("int[4] : 65535 65535 65535 65535", instu.exportTypeAndDataString(false));

		instu.importDataString("123 456 789 098");
		Assert.assertEquals("int[4] : 123 456 789 98", instu.exportTypeAndDataString(false));

		try {
			instu.importDataString("-123 456 -789 098");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testImportExportStringSigned() throws DataImportException {
		insts.importDataString("992 928 387 2");
		Assert.assertEquals("992 928 387 2", insts.exportDataString());

		insts.importDataString("00 0001 001 -01");
		Assert.assertEquals("0 1 1 -1", insts.exportDataString());

		try {
			// to short
			insts.importDataString("999 10 1");
			// Assert.assertEquals("0", inst.exportDataString());
			fail("to short data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			// to long
			insts.importDataString("12 20 30 40 51");
			// Assert.assertEquals("1", inst.exportDataString());
			fail("to long data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			insts.importDataString("test 1 2 3");
			fail("invalid data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testImportExportStringUnsigned() throws DataImportException {
		instu.importDataString("992 928 387 2");
		Assert.assertEquals("992 928 387 2", instu.exportDataString());

		instu.importDataString("00 0001 001 01");
		Assert.assertEquals("0 1 1 1", instu.exportDataString());

		try {
			instu.importDataString("00 0001 001 -01");
			// Assert.assertEquals("0 1 1 -1", instu.exportDataString());
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			// to short
			instu.importDataString("999 10 1");
			// Assert.assertEquals("0", instu.exportDataString());
			fail("to short data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			// to long
			instu.importDataString("12 20 30 40 51");
			// Assert.assertEquals("1", instu.exportDataString());
			fail("to long data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			instu.importDataString("test 1 2 3");
			fail("invalid data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}
	}

}
