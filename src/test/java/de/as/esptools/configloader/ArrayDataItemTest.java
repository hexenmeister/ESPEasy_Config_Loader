package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.ArrayDataItem;
import de.as.esptools.configloader.datatypes.ByteItem;
import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.DataItem;

public class ArrayDataItemTest {

    private ArrayDataItemInst inst;

    static class ArrayDataItemInst extends ArrayDataItem<DataItem, Void> {

        protected ArrayDataItemInst() {
            super("ARRAY_TEST", 10, 1);
        }

        @Override
        protected DataItem createType(byte[] data, int offset, Void ad) {
            return new ByteItemTest(data, offset);
        }

        static class ByteItemTest extends ByteItem {
            public ByteItemTest(byte[] data, int offset) {
                super(data, offset);
            }
        }

    }

    @Before
    public void setUp() throws Exception {
        inst = new ArrayDataItemInst();
    }

    @Test
    public void testGetTypeName() {
        Assert.assertEquals("ARRAY_TEST", inst.getTypeName());
    }

    @Test
    public void testGetArrayLength() {
        Assert.assertEquals(10, inst.getArrayLength());
    }

    @Test
    public void testGetBytesPerItem() {
        Assert.assertEquals(1, inst.getBytesPerItem());
    }

    @Test
    public void testGetBinLength() {
        Assert.assertEquals(10 * 1, inst.getBinLength());
    }

    @Test
    public void testImportBinOffset() throws DataImportException {
        byte[] inBytes = {(byte) 0xFF, (byte) 0xDE, (byte) 0xAD, (byte) 0xF0, (byte) 0x0D, (byte) 0x00, (byte) 0x10, (byte) 0x20, (byte) 0x30, (byte) 0x40,
                (byte) 0x50, (byte) 0xDE };
        byte[] testBbytes = {(byte) 0xDE, (byte) 0xAD, (byte) 0xF0, (byte) 0x0D, (byte) 0x00, (byte) 0x10, (byte) 0x20, (byte) 0x30, (byte) 0x40, (byte) 0x50 };

        inst.importBin(inBytes, 1);
        Assert.assertTrue(Arrays.equals(testBbytes, inst.exportBin()));

        inst.importBin(inBytes, 0);
        try {
            Assert.assertTrue(Arrays.equals(testBbytes, inst.exportBin()));
            fail("must be falsh");
        } catch (AssertionError e) {
            // NOP
        }
    }

    @Test
    public void testImportExportBin() throws DataImportException {
        byte[] bytes = {(byte) 0xDE, (byte) 0xAD, (byte) 0xF0, (byte) 0x0D, (byte) 0x00, (byte) 0x10, (byte) 0x20, (byte) 0x30, (byte) 0x40, (byte) 0x50 };
        inst.importBin(bytes);
        Assert.assertTrue(Arrays.equals(bytes, inst.exportBin()));
    }

    @Test
    public void testImportExportHex() throws DataImportException {
        inst.importHex("DE AD F0 0D 00 10 20 30 40 50");
        Assert.assertEquals("DE AD F0 0D 00 10 20 30 40 50", inst.exportHex());
    }

    @Test
    public void testImportExportDataString() throws DataImportException {
        inst.importDataString("DE AD F0 0D 00 10 20 30 40 50");
        Assert.assertEquals("DE AD F0 0D 00 10 20 30 40 50", inst.exportDataString());
    }

    @Test
    public void testExportTypeAndDataString() throws DataImportException {
        inst.importDataString("DE AD F0 0D 00 10 20 30 40 50");
        Assert.assertEquals("ARRAY_TEST[10] : DE AD F0 0D 00 10 20 30 40 50", inst.exportTypeAndDataString(false));
    }

}
