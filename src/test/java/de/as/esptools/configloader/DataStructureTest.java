package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.BooleanItem;
import de.as.esptools.configloader.datatypes.ByteItem;
import de.as.esptools.configloader.datatypes.CharItem;
import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.DataStructure;
import de.as.esptools.configloader.datatypes.FloatItem;
import de.as.esptools.configloader.datatypes.Int16Item;
import de.as.esptools.configloader.datatypes.Int8Item;
import de.as.esptools.configloader.datatypes.IntItem;
import de.as.esptools.configloader.datatypes.LongItem;

public class DataStructureTest {

	private DataStructure createSmallStruct() {
		DataStructure struct = new DataStructure("TEST_STRUCT");
		struct.addItem(new BooleanItem());

		return struct;
	}

	private DataStructure createBigStruct() {
		DataStructure struct = new DataStructure("TEST_STRUCT");
		struct.addItem(new BooleanItem());
		struct.addItem(new ByteItem());
		struct.addItem(new CharItem());
		struct.addItem(new FloatItem());
		struct.addItem(new Int16Item(true));
		struct.addItem(new Int16Item(false));
		struct.addItem(new Int8Item(true));
		struct.addItem(new Int8Item(false));
		struct.addItem(new IntItem(true));
		struct.addItem(new IntItem(false));
		struct.addItem(new LongItem(true));
		struct.addItem(new LongItem(false));

		return struct;
	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testAddItemGetArrayLength() {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals(1, struct.getArrayLength());
		struct.addItem(new Int8Item(true));
		Assert.assertEquals(2, struct.getArrayLength());
	}

	@Test
	public void testGetItems() {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals(1, struct.getItems().size());
		struct.addItem(new Int8Item(true));
		Assert.assertEquals(2, struct.getItems().size());
	}

	@Test
	public void testGetItemCount() {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals(1, struct.getItemCount());
		struct.addItem(new Int8Item(true));
		Assert.assertEquals(2, struct.getItemCount());
	}

	@Test
	public void testGetTypeName() {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals("TEST_STRUCT", struct.getTypeName());
	}

	@Test
	public void testGetBinLength() {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals(1, struct.getBinLength());
		struct.addItem(new Int8Item(true));
		struct.addItem(new Int16Item(true));
		Assert.assertEquals(4, struct.getBinLength());
	}

	@Test
	public void testImportBinByteArray() {
		// TODO
		fail("Not yet implemented");
	}

	@Test
	public void testImportBinByteArrayInt() {
		// TODO
		fail("Not yet implemented");
	}

	// @Test
	// public void testImportHex() {
	// // TODO
	// fail("Not yet implemented");
	// }

	@Test
	public void testImportDataString() {
		// TODO
		fail("Not yet implemented");
	}

	@Test
	public void testExportBin() {
		// TODO
		fail("Not yet implemented");
	}

	@Test
	public void testExportImportHex() throws DataImportException {
		// TODO
		DataStructure struct = this.createBigStruct();
		String data = "01 DE AD 00 00 00 00 12 34 88 23 55 91 02 93 94 00 00 00 00 00 00 00 00 00";
		struct.importHex(data);
		String export = struct.exportHex();
		System.out.println(export);
		Assert.assertEquals(data, export);
	}

	@Test
	public void testExportDataString() {
		// TODO
		fail("Not yet implemented");
	}

	@Test
	public void testExportTypeAndDataString() {
		// TODO
		fail("Not yet implemented");
	}

}
