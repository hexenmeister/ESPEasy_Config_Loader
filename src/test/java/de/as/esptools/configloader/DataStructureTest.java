package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.BooleanItem;
import de.as.esptools.configloader.datatypes.DataStructure;
import de.as.esptools.configloader.datatypes.Int8Item;

import org.junit.Assert;

public class DataStructureTest {

    private DataStructure createSmallStruct() {
        DataStructure struct = new DataStructure("TEST_STRUCT");
        struct.addItem(new BooleanItem());

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
        Assert.assertEquals(1, struct.getItemCount());
    }

    @Test
    public void testGetTypeName() {
        DataStructure struct = this.createSmallStruct();
        Assert.assertEquals("TEST_STRUCT", struct.getTypeName());
    }

    @Test
    public void testGetBinLength() {
        Assert.assertEquals(1, this.createSmallStruct().getItemCount());
        // TODO: more
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

    @Test
    public void testImportHex() {
        // TODO
        fail("Not yet implemented");
    }

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
    public void testExportHex() {
        // TODO
        fail("Not yet implemented");
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
