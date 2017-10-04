package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.DataItem;
import org.junit.Assert;

public class DataItemTest {

    private DataItem inst;

    private DataItem inst2;

    private static final int LENGTH = 8;

    private static class DataItemTestClass extends DataItem {

        protected DataItemTestClass() {
            super("DATA_TEST", LENGTH);
        }

        @Override
        public String importDataStringIntern(String data) throws DataImportException {
            return null;
        }

        @Override
        public String exportDataStringIntern() {
            return null;
        }
    }

    private static class DataItemTest2Class extends DataItemTestClass {

        @Override
        protected boolean allowShortDataImport() {
            return true;
        }

        @Override
        protected boolean allowLongDataImport() {
            return true;
        }
    }

    @Before
    public void setUp() throws Exception {
        inst = new DataItemTestClass();
        inst2 = new DataItemTest2Class();
    }

    @Test
    public void testDataItem() {
        Assert.assertEquals(LENGTH, inst.getBinLength());
    }

    @Test
    public void testGetBinLength() {
        Assert.assertEquals(LENGTH, inst.getBinLength());
    }

    @Test
    public void testImportBinByteArray() throws DataImportException {
        byte[] bytes = new byte[] {0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, (byte) 0xFF };
        inst.importBin(bytes);
        Assert.assertTrue(Arrays.equals(bytes, inst.exportBin()));

        // too long
        bytes = new byte[] {0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, (byte) 0xFF, (byte) 0xEE };
        try {
            inst.importBin(bytes);
            fail("too long data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }

        // too short
        bytes = new byte[] {0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60 };
        try {
            inst.importBin(bytes);
            fail("too short data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testImportExportBinByteArrayInt() throws DataImportException {
        byte[] bytes = new byte[] {(byte) 0xAA, 0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, (byte) 0xFF, (byte) 0xEE };
        inst.importBin(bytes, 1);
        byte[] bytes2 = new byte[LENGTH];
        System.arraycopy(bytes, 1, bytes2, 0, bytes2.length);
        Assert.assertTrue(Arrays.equals(bytes2, inst.exportBin()));

        // too long
        bytes = new byte[] {(byte) 0xAA, 0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, (byte) 0xFF, (byte) 0xEE };
        inst.importBin(bytes, 1);

        // too short
        bytes = new byte[] {(byte) 0xAA, 0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60 };
        try {
            inst.importBin(bytes, 1);
            fail("too short data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testImportHex() throws DataImportException {
        String hex = "00 10 20 30 40 50 60 FF";
        byte[] bytes = new byte[] {0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, (byte) 0xFF };
        inst.importHex(hex);
        Assert.assertTrue(Arrays.equals(bytes, inst.exportBin()));

        // too long
        try {
            hex = "00 10 20 30 40 50 60 FF EE";
            inst.importHex(hex);
            fail("too long data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }

        // too long
        hex = "00 10 20 30 40 50 60 FF EE";
        inst2.importHex(hex);
        Assert.assertTrue(Arrays.equals(bytes, inst2.exportBin()));

        // too short
        try {
            hex = "00 10 20 30 40 50 60 ";
            inst.importHex(hex);
            fail("too short data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }

        // too short allowed
        hex = "00 10 20 30 40 50 60";
        inst2.importHex(hex);
        bytes = new byte[] {0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, 0x00 };
        // fail(inst2.exportHex());
        Assert.assertTrue(Arrays.equals(bytes, inst2.exportBin()));
    }

    @Test
    public void testExportHex() throws DataImportException {
        byte[] bytes = new byte[] {0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, (byte) 0xFF };
        inst.importBin(bytes);
        Assert.assertEquals("00 10 20 30 40 50 60 FF", inst.exportHex());
    }

    @Test
    public void testClone() throws CloneNotSupportedException, DataImportException {
        byte[] bytes = new byte[] {0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, (byte) 0xFF };
        inst.importBin(bytes);
        DataItem instClone = inst.clone();
        bytes = new byte[] {(byte) 0xDD, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, (byte) 0xFF };
        inst.importBin(bytes);
        Assert.assertEquals("00 10 20 30 40 50 60 FF", instClone.exportHex());
    }
}
