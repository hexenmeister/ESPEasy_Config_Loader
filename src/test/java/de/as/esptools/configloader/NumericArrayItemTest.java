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
		Assert.assertEquals("1234", inst14s.exportString());
		
		fail("Not yet implemented");
	}

	@Test
	public void testExportString() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetNumber() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSetNumber() {
		fail("Not yet implemented");
	}

}
