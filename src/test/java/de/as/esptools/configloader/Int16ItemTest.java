package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.Int16Item;

public class Int16ItemTest {

	private Int16Item insts;
	private Int16Item instu;

	@Before
	public void setUp() throws Exception {
		insts = new Int16Item(true);
		instu = new Int16Item(false);
	}

	@Test
	public void testName() {
		Assert.assertEquals("int16", insts.getTypeName());
		Assert.assertEquals("int16", instu.getTypeName());
	}

	@Test
	public void testIsSigned() {
		Assert.assertEquals(true, insts.isSigned());
		Assert.assertEquals(false, instu.isSigned());
	}

	@Test
	public void testTypeExportSigned() throws DataImportException {
		insts.importDataString("0");
		Assert.assertEquals("int16 : 0", insts.exportTypeAndDataString(false));

		insts.importDataString("1");
		Assert.assertEquals("int16 : 1", insts.exportTypeAndDataString(false));

		insts.importDataString("-1");
		Assert.assertEquals("int16 : -1", insts.exportTypeAndDataString(false));

		insts.importDataString("127");
		Assert.assertEquals("int16 : 127", insts.exportTypeAndDataString(false));

		insts.importDataString("-128");
		Assert.assertEquals("int16 : -128", insts.exportTypeAndDataString(false));

		insts.importDataString("32767");
		Assert.assertEquals("int16 : 32767", insts.exportTypeAndDataString(false));

		insts.importDataString("-32768");
		Assert.assertEquals("int16 : -32768", insts.exportTypeAndDataString(false));

		try {
			insts.importDataString("32768");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			insts.importDataString("-32769");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testTypeExportUnsigned() throws DataImportException {
		instu.importDataString("0");
		Assert.assertEquals("int16 : 0", instu.exportTypeAndDataString(false));

		instu.importDataString("1");
		Assert.assertEquals("int16 : 1", instu.exportTypeAndDataString(false));

		try {
			instu.importDataString("-1");
			Assert.assertEquals("int16 : -1", instu.exportTypeAndDataString(false));
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.importDataString("127");
		Assert.assertEquals("int16 : 127", instu.exportTypeAndDataString(false));

		try {
			instu.importDataString("-128");
			Assert.assertEquals("int16 : -128", instu.exportTypeAndDataString(false));
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.importDataString("65535");
		Assert.assertEquals("int16 : 65535", instu.exportTypeAndDataString(false));

		try {
			instu.importDataString("65536");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testImportStringSigned() throws DataImportException {
		insts.importDataString("0");
		Assert.assertEquals("0", insts.exportDataString());

		insts.importDataString("-0");
		Assert.assertEquals("0", insts.exportDataString());

		insts.importDataString("1");
		Assert.assertEquals("1", insts.exportDataString());

		insts.importDataString("-1");
		Assert.assertEquals("-1", insts.exportDataString());

		insts.importDataString("127");
		Assert.assertEquals("127", insts.exportDataString());

		insts.importDataString("255");
		Assert.assertEquals("255", insts.exportDataString());

		insts.importDataString("-128");
		Assert.assertEquals("-128", insts.exportDataString());

		insts.importDataString("32767");
		Assert.assertEquals("32767", insts.exportDataString());

		insts.importDataString("-32768");
		Assert.assertEquals("-32768", insts.exportDataString());

		try {
			insts.importDataString("32768");
			fail("too big");
		} catch (DataImportException e) {
			// NOP
		}
		try {
			insts.importDataString("-32769");
			fail("too small");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			insts.importDataString("0.1");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			insts.importDataString("test");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testImportStringUnsigned() throws DataImportException {
		instu.importDataString("0");
		Assert.assertEquals("0", instu.exportDataString());

		instu.importDataString("1");
		Assert.assertEquals("1", instu.exportDataString());

		instu.importDataString("127");
		Assert.assertEquals("127", instu.exportDataString());

		instu.importDataString("255");
		Assert.assertEquals("255", instu.exportDataString());

		instu.importDataString("65535");
		Assert.assertEquals("65535", instu.exportDataString());

		try {
			instu.importDataString("65536");
			fail("too big");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			instu.importDataString("-1");
			fail("too small");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			instu.importDataString("0.1");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			instu.importDataString("test");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testExportStringSigned() throws DataImportException {
		insts.setNumber(0);
		Assert.assertEquals("0", insts.exportDataString());

		insts.setNumber(1);
		Assert.assertEquals("1", insts.exportDataString());

		insts.setNumber(-1);
		Assert.assertEquals("-1", insts.exportDataString());

		insts.setNumber(127);
		Assert.assertEquals("127", insts.exportDataString());

		insts.setNumber(-128);
		Assert.assertEquals("-128", insts.exportDataString());

		insts.setNumber(32767);
		Assert.assertEquals("32767", insts.exportDataString());

		insts.setNumber(-32768);
		Assert.assertEquals("-32768", insts.exportDataString());

		try {
			insts.setNumber(32768);
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			insts.setNumber(-32769);
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testExportStringUnsigned() throws DataImportException {
		instu.setNumber(0);
		Assert.assertEquals("0", instu.exportDataString());

		instu.setNumber(1);
		Assert.assertEquals("1", instu.exportDataString());

		try {
			instu.setNumber(-1);
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.setNumber(127);
		Assert.assertEquals("127", instu.exportDataString());

		instu.setNumber(255);
		Assert.assertEquals("255", instu.exportDataString());

		instu.setNumber(65535);
		Assert.assertEquals("65535", instu.exportDataString());

		try {
			instu.setNumber(65536);
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testGetSetNumberSigned() throws DataImportException {
		insts.setNumber(0);
		Assert.assertEquals(0, insts.getNumber());

		insts.setNumber(1);
		Assert.assertEquals(1, insts.getNumber());

		insts.setNumber(-1);
		Assert.assertEquals(-1, insts.getNumber());

		insts.setNumber(127);
		Assert.assertEquals(127, insts.getNumber());

		insts.setNumber(-128);
		Assert.assertEquals(-128, insts.getNumber());

		insts.setNumber(32767);
		Assert.assertEquals(32767, insts.getNumber());

		insts.setNumber(-32768);
		Assert.assertEquals(-32768, insts.getNumber());
	}

	@Test
	public void testGetSetNumberUnsigned() throws DataImportException {
		instu.setNumber(0);
		Assert.assertEquals(0, instu.getNumber());

		instu.setNumber(1);
		Assert.assertEquals(1, instu.getNumber());

		instu.setNumber(127);
		Assert.assertEquals(127, instu.getNumber());

		instu.setNumber(255);
		Assert.assertEquals(255, instu.getNumber());

		instu.setNumber(65535);
		Assert.assertEquals(65535, instu.getNumber());
	}

}
