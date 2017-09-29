package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.Int8Item;

public class Int8ItemTest {

	private Int8Item insts;
	private Int8Item instu;

	@Before
	public void setUp() throws Exception {
		insts = new Int8Item(true);
		instu = new Int8Item(false);
	}

	@Test
	public void testName() {
		Assert.assertEquals("int8", insts.getTypeName());
		Assert.assertEquals("int8", instu.getTypeName());
	}
	
	@Test
	public void testBinLength() {
		Assert.assertEquals(1, insts.getBinLength());
		Assert.assertEquals(1, instu.getBinLength());
	}
	
	@Test
	public void testIsSigned() {
		Assert.assertEquals(true, insts.isSigned());
		Assert.assertEquals(false, instu.isSigned());
	}

	@Test
	public void testTypeExportSigned() throws DataImportException {
		insts.importDataString("0");
		Assert.assertEquals("int8 : 0", insts.exportTypeAndDataString(false));

		insts.importDataString("1");
		Assert.assertEquals("int8 : 1", insts.exportTypeAndDataString(false));

		insts.importDataString("-1");
		Assert.assertEquals("int8 : -1", insts.exportTypeAndDataString(false));

		insts.importDataString("127");
		Assert.assertEquals("int8 : 127", insts.exportTypeAndDataString(false));

		insts.importDataString("-128");
		Assert.assertEquals("int8 : -128", insts.exportTypeAndDataString(false));

		try {
			insts.importDataString("128");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			insts.importDataString("-129");
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testTypeExportUnsigned() throws DataImportException {
		instu.importDataString("0");
		Assert.assertEquals("int8 : 0", instu.exportTypeAndDataString(false));

		instu.importDataString("1");
		Assert.assertEquals("int8 : 1", instu.exportTypeAndDataString(false));

		try {
			instu.importDataString("-1");
			Assert.assertEquals("int8 : -1", instu.exportTypeAndDataString(false));
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.importDataString("127");
		Assert.assertEquals("int8 : 127", instu.exportTypeAndDataString(false));

		try {
			instu.importDataString("-128");
			Assert.assertEquals("int8 : -128", instu.exportTypeAndDataString(false));
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.importDataString("255");
		Assert.assertEquals("int8 : 255", instu.exportTypeAndDataString(false));

		try {
			instu.importDataString("256");
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

		insts.importDataString("-128");
		Assert.assertEquals("-128", insts.exportDataString());

		try {
			insts.importDataString("128");
			fail("too big");
		} catch (DataImportException e) {
			// NOP
		}
		try {
			insts.importDataString("-129");
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

		try {
			instu.importDataString("256");
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

		try {
			insts.setNumber(128);
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			insts.setNumber(-129);
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

		try {
			instu.setNumber(256);
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
	}

}
