package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.NumericArrayItem;

public class NumericArrayItemTest {

	NumericArrayItemInst inst14s;
	NumericArrayItemInst inst14u;

	static class NumericArrayItemInst extends NumericArrayItem {

		protected NumericArrayItemInst(int length, int bytesPerItem, boolean singned) {
			super(length, bytesPerItem, singned);
		}
	}

	@Before
	public void setUp() throws Exception {
		inst14s = new NumericArrayItemInst(1, 4, true);
		inst14u = new NumericArrayItemInst(1, 4, false);
	}

	@Test
	public void testImportString() throws DataImportException {
		inst14s.importString("1234");
		Assert.assertEquals(1234, inst14s.getNumber());
		// Assert.assertEquals("1234", inst14s.exportString());

		inst14s.importString("-1234");
		Assert.assertEquals(-1234, inst14s.getNumber());

		inst14s.importString("-0");
		Assert.assertEquals(0, inst14s.getNumber());

		inst14s.importString("0");
		Assert.assertEquals(0, inst14s.getNumber());

		inst14s.importString("123456780");
		Assert.assertEquals(123456780, inst14s.getNumber());

		inst14s.importString("-123456780");
		Assert.assertEquals(-123456780, inst14s.getNumber());

		inst14u.importString("1234");
		Assert.assertEquals(1234, inst14u.getNumber());

		try {
			inst14u.importString("-1234");
			fail("unsigned shouldnot be able to import negative number");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			inst14u.importString("-0");
			fail("unsigned shouldnot be able to import negative number");
		} catch (DataImportException e) {
			// NOP
		}

		inst14u.importString("0");
		Assert.assertEquals(0, inst14u.getNumber());

		inst14u.importString("123456780");
		Assert.assertEquals(123456780, inst14u.getNumber());

		try {
			inst14u.importString("-123456780");
			fail("unsigned shouldnot be able to import negative number");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testExportString() throws DataImportException {
		inst14s.setNumber(1);
		Assert.assertEquals("1", inst14s.exportString());

		inst14s.setNumber(-1);
		Assert.assertEquals("-1", inst14s.exportString());

		inst14s.setNumber(1234567890);
		Assert.assertEquals("1234567890", inst14s.exportString());

		inst14s.setNumber(-1234567890);
		Assert.assertEquals("-1234567890", inst14s.exportString());

		inst14u.setNumber(1);
		Assert.assertEquals("1", inst14u.exportString());

		inst14u.setNumber(987654321);
		Assert.assertEquals("987654321", inst14u.exportString());
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

}
