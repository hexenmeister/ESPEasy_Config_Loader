package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.BooleanItem;
import de.as.esptools.configloader.datatypes.DataStructure;
import org.junit.Assert;

public class DataStructureTest {

	private DataStructure struct;

	@Before
	public void setUp() throws Exception {
		this.struct = new DataStructure("TEST_STRUCT");
		this.struct.addItem(new BooleanItem());
	}

	@Test
	public void testDataStructure() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetItems() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetItemCount() {
		Assert.assertEquals(1, struct.getItemCount());
	}

	@Test
	public void testGetTypeName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBinLength() {
		Assert.assertEquals(1, struct.getItemCount());
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
	public void testImportHex() {
		fail("Not yet implemented");
	}

	@Test
	public void testImportDataString() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportBin() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportHex() {
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
	public void testGetArrayLength() {
		fail("Not yet implemented");
	}

}
