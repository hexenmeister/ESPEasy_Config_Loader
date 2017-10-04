package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.DataItem;
import de.as.esptools.configloader.datatypes.NumericIntegerItem;

public class NumericItemTest {

	NumericArrayItemInst inst14s;
	NumericArrayItemInst inst14u;

	static class NumericArrayItemInst extends NumericIntegerItem {

		protected NumericArrayItemInst(int bytesPerItem, boolean singned) {
			super("NUMERIC_TEST", bytesPerItem, singned);
		}
	}

	@Before
	public void setUp() throws Exception {
		inst14s = new NumericArrayItemInst(4, true);
		inst14u = new NumericArrayItemInst(4, false);
	}

	@Test
	public void testImportString() throws DataImportException {
		inst14s.importDataString("1234");
		Assert.assertEquals(1234, inst14s.getNumber());
		// Assert.assertEquals("1234", inst14s.exportString());

		inst14s.importDataString("-1234");
		Assert.assertEquals(-1234, inst14s.getNumber());

		inst14s.importDataString("-0");
		Assert.assertEquals(0, inst14s.getNumber());

		inst14s.importDataString("0");
		Assert.assertEquals(0, inst14s.getNumber());

		inst14s.importDataString("123456780");
		Assert.assertEquals(123456780, inst14s.getNumber());

		inst14s.importDataString("-123456780");
		Assert.assertEquals(-123456780, inst14s.getNumber());

		inst14u.importDataString("1234");
		Assert.assertEquals(1234, inst14u.getNumber());

		try {
			inst14u.importDataString("-1234");
			fail("unsigned shouldnot be able to import negative number");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			inst14u.importDataString("-0");
			fail("unsigned shouldnot be able to import negative number");
		} catch (DataImportException e) {
			// NOP
		}

		inst14u.importDataString("0");
		Assert.assertEquals(0, inst14u.getNumber());

		inst14u.importDataString("123456780");
		Assert.assertEquals(123456780, inst14u.getNumber());

		try {
			inst14u.importDataString("-123456780");
			fail("unsigned shouldnot be able to import negative number");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testExportString() throws DataImportException {
		inst14s.setNumber(1);
		Assert.assertEquals("1", inst14s.exportDataString());

		inst14s.setNumber(-1);
		Assert.assertEquals("-1", inst14s.exportDataString());

		inst14s.setNumber(1234567890);
		Assert.assertEquals("1234567890", inst14s.exportDataString());

		inst14s.setNumber(-1234567890);
		Assert.assertEquals("-1234567890", inst14s.exportDataString());

		inst14u.setNumber(1);
		Assert.assertEquals("1", inst14u.exportDataString());

		inst14u.setNumber(987654321);
		Assert.assertEquals("987654321", inst14u.exportDataString());
	}

	@Test
	public void testGetSetNumber() throws DataImportException {
		inst14s.setNumber(1234567890);
		Assert.assertEquals(1234567890, inst14s.getNumber());

		inst14s.setNumber(0);
		Assert.assertEquals(0, inst14s.getNumber());

		inst14s.setNumber(-1);
		Assert.assertEquals(-1, inst14s.getNumber());

		inst14u.setNumber(1234567890);
		Assert.assertEquals(1234567890, inst14u.getNumber());

		inst14u.setNumber(0);
		Assert.assertEquals(0, inst14u.getNumber());

		try {
			inst14u.setNumber(-1);
			fail("unsigned shouldnot be able to import negative number");
		} catch (DataImportException e) {
			// NOP
		}

	}

	@Test
    public void testClone() throws CloneNotSupportedException, DataImportException {
        inst14s.setNumber(75);
        DataItem instClone = inst14s.clone();
        inst14s.setNumber(12);
        Assert.assertEquals("75", instClone.exportDataString());
    }
}
