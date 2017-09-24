package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.ArrayDataItem;
import de.as.esptools.configloader.datatypes.ByteItem;
import de.as.esptools.configloader.datatypes.DataItem;

public class ArrayDataItemTest {

	private ArrayDataItemInst inst;

	static class ArrayDataItemInst extends ArrayDataItem<DataItem> {

		protected ArrayDataItemInst() {
			super("ARRAY_TEST", 10, 1);
		}

		@Override
		protected DataItem createType(byte[] data, int offset) {
			return new ByteItem();
		}

	}

	@Before
	public void setUp() throws Exception {
		inst = new ArrayDataItemInst();
	}

	@Test
	public void testGetArrayLength() {
		Assert.assertEquals(10, inst.getArrayLength());
	}

	@Test
	public void testGetTypeName() {
		Assert.assertEquals("ARRAY_TEST", inst.getTypeName());
	}

	@Test
	public void testGetBinLength() {
		Assert.assertEquals(10 * 1, inst.getBinLength());
	}

	@Test
	public void testGetData() {
		fail("Not yet implemented");
	}

	@Test
	public void testImportBinByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testImportBinByteArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportBin() {
		fail("Not yet implemented");
	}

	@Test
	public void testImportHex() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportHex() {
		fail("Not yet implemented");
	}

	@Test
	public void testImportDataString() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportDataString() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportTypeAndDataString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBytesPerItem() {
		fail("Not yet implemented");
	}

}
