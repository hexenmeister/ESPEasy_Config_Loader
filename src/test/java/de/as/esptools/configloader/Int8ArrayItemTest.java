package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.Int8ArrayItem;

public class Int8ArrayItemTest {

	private Int8ArrayItem insts;
	private Int8ArrayItem instu;

	@Before
	public void setUp() throws Exception {
		this.insts = new Int8ArrayItem(4, true);
		this.instu = new Int8ArrayItem(4, false);
	}

	@Test
	public void testName() {
		Assert.assertEquals("int8", insts.getTypeName());
		Assert.assertEquals("int8", instu.getTypeName());
	}

	@Test
	public void testTypeExportSigned() throws DataImportException {
		insts.importDataString("1 0 -0 -1");
		Assert.assertEquals("int8[4] : 1 0 0 -1", insts.exportTypeAndDataString(false));

		insts.importDataString("127 127 127 127");
		Assert.assertEquals("int8[4] : 127 127 127 127", insts.exportTypeAndDataString(false));

		insts.importDataString("-128 -128 -128 -128");
		Assert.assertEquals("int8[4] : -128 -128 -128 -128", insts.exportTypeAndDataString(false));
	}

	@Test
	public void testTypeExportUnsigned() throws DataImportException {
		try {
			instu.importDataString("1 0 -0 -1");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			instu.importDataString("100 100 100 -100");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.importDataString("255 255 255 255");
		Assert.assertEquals("int8[4] : 255 255 255 255", instu.exportTypeAndDataString(false));


		try {
			instu.importDataString("-123 56 -89 098");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testImportExportStringSigned() throws DataImportException {
		insts.importDataString("92 28 37 2");
		Assert.assertEquals("92 28 37 2", insts.exportDataString());

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
		instu.importDataString("92 28 87 2");
		Assert.assertEquals("92 28 87 2", instu.exportDataString());

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
