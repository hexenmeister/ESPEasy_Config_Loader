package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.LongItem;

public class LongItemTest {

	private LongItem insts;
	private LongItem instu;

	@Before
	public void setUp() throws Exception {
		insts = new LongItem(true);
		instu = new LongItem(false);
	}

	@Test
	public void testName() {
		Assert.assertEquals("long", insts.getTypeName());
		Assert.assertEquals("long", instu.getTypeName());
	}

	@Test
	public void testIsSigned() {
		Assert.assertEquals(true, insts.isSigned());
		Assert.assertEquals(false, instu.isSigned());
	}

	@Test
	public void testTypeExportSigned() throws DataImportException {
		insts.importDataString("0");
		Assert.assertEquals("long : 0", insts.exportTypeAndDataString(false));

		insts.importDataString("1");
		Assert.assertEquals("long : 1", insts.exportTypeAndDataString(false));

		insts.importDataString("-1");
		Assert.assertEquals("long : -1", insts.exportTypeAndDataString(false));

		insts.importDataString("1316134912");
		Assert.assertEquals("long : 1316134912", insts.exportTypeAndDataString(false));

		insts.importDataString("1316134913");
		Assert.assertEquals("long : 1316134913", insts.exportTypeAndDataString(false));

		insts.importDataString("-1316134913");
		Assert.assertEquals("long : -1316134913", insts.exportTypeAndDataString(false));

		insts.importDataString("2147483647");
		Assert.assertEquals("long : 2147483647", insts.exportTypeAndDataString(false));

		insts.importDataString("-2147483648");
		Assert.assertEquals("long : -2147483648", insts.exportTypeAndDataString(false));
	}

	@Test
	public void testTypeExportUnsigned() throws DataImportException {
		instu.importDataString("0");
		Assert.assertEquals("long : 0", instu.exportTypeAndDataString(false));

		instu.importDataString("1");
		Assert.assertEquals("long : 1", instu.exportTypeAndDataString(false));

		try {
			instu.importDataString("-1");
			Assert.assertEquals("long : -1", instu.exportTypeAndDataString(false));
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.importDataString("1316134912");
		Assert.assertEquals("long : 1316134912", instu.exportTypeAndDataString(false));

		instu.importDataString("1316134913");
		Assert.assertEquals("long : 1316134913", instu.exportTypeAndDataString(false));

		try {
			instu.importDataString("-1316134913");
			Assert.assertEquals("long : -1316134913", instu.exportTypeAndDataString(false));
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.importDataString("2147483647");
		Assert.assertEquals("long : 2147483647", instu.exportTypeAndDataString(false));

		instu.importDataString("4294967295");
		Assert.assertEquals("long : 4294967295", instu.exportTypeAndDataString(false));

		try {
			instu.importDataString("-2147483648");
			Assert.assertEquals("long : -2147483648", instu.exportTypeAndDataString(false));
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

		insts.importDataString("110000011");
		Assert.assertEquals("110000011", insts.exportDataString());

		insts.importDataString("-110000011");
		Assert.assertEquals("-110000011", insts.exportDataString());

		insts.importDataString("2147483647");
		Assert.assertEquals("2147483647", insts.exportDataString());

		insts.importDataString("-2147483648");
		Assert.assertEquals("-2147483648", insts.exportDataString());

		try {
			insts.importDataString("2147483648");
			// Assert.assertEquals("long : 2147483648",
			// insts.exportTypeAndDataString(false));
			fail("too big");
		} catch (DataImportException e) {
			// NOP
		}
		try {
			insts.importDataString("-2147483649");
			// Assert.assertEquals("long : -2147483649",
			// insts.exportTypeAndDataString(false));
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

		instu.importDataString("110000011");
		Assert.assertEquals("110000011", instu.exportDataString());

		instu.importDataString("4294967295");
		Assert.assertEquals("4294967295", instu.exportDataString());

		try {
			instu.importDataString("4294967296");
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
		insts.setNumber(1);
		Assert.assertEquals("1", insts.exportDataString());

		insts.setNumber(-1);
		Assert.assertEquals("-1", insts.exportDataString());

		insts.setNumber(567890);
		Assert.assertEquals("567890", insts.exportDataString());

		insts.setNumber(-567890);
		Assert.assertEquals("-567890", insts.exportDataString());

		insts.setNumber(0);
		Assert.assertEquals("0", insts.exportDataString());

		insts.setNumber(101);
		Assert.assertEquals("101", insts.exportDataString());

		insts.setNumber(-101l);
		Assert.assertEquals("-101", insts.exportDataString());

		insts.setNumber(2147483647);
		Assert.assertEquals("2147483647", insts.exportDataString());

		insts.setNumber(-2147483648);
		Assert.assertEquals("-2147483648", insts.exportDataString());

		try {
			insts.setNumber(2147483648l);
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			insts.setNumber(-2147483649l);
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testExportStringUnsigned() throws DataImportException {
		instu.setNumber(1);
		Assert.assertEquals("1", instu.exportDataString());

		try {
			instu.setNumber(-1);
			Assert.assertEquals("-1", instu.exportDataString());
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.setNumber(567890);
		Assert.assertEquals("567890", instu.exportDataString());

		try {
			instu.setNumber(-567890);
			Assert.assertEquals("-567890", instu.exportDataString());
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.setNumber(0);
		Assert.assertEquals("0", instu.exportDataString());

		instu.setNumber(101);
		Assert.assertEquals("101", instu.exportDataString());

		try {
			instu.setNumber(-0101);
			Assert.assertEquals("-101", instu.exportDataString());
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		instu.setNumber(4294967295l);
		Assert.assertEquals("4294967295", instu.exportDataString());

		try {
			instu.setNumber(-2147483648l);
			Assert.assertEquals("-2147483648", instu.exportDataString());
			fail("invalid");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			instu.setNumber(4294967296l);
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

		insts.setNumber(1010101);
		Assert.assertEquals(1010101, insts.getNumber());

		insts.setNumber(-1010101);
		Assert.assertEquals(-1010101, insts.getNumber());

		insts.setNumber(2147483647l);
		Assert.assertEquals(2147483647l, insts.getNumber());

		insts.setNumber(-2147483648l);
		Assert.assertEquals(-2147483648l, insts.getNumber());
	}

	@Test
	public void testGetSetNumberUnsigned() throws DataImportException {
		instu.setNumber(0);
		Assert.assertEquals(0, instu.getNumber());

		instu.setNumber(1);
		Assert.assertEquals(1, instu.getNumber());

		instu.setNumber(1010101);
		Assert.assertEquals(1010101, instu.getNumber());

		instu.setNumber(2147483647l);
		Assert.assertEquals(2147483647l, instu.getNumber());

		instu.setNumber(4294967295l);
		Assert.assertEquals(4294967295l, instu.getNumber());
	}

}
