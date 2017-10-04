package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.ArrayDataItem;
import de.as.esptools.configloader.datatypes.ByteArrayItem;
import de.as.esptools.configloader.datatypes.ByteItem;
import de.as.esptools.configloader.datatypes.DataImportException;

public class ByteArrayItemTest {

    private ByteArrayItem inst;

    private ByteArrayItem inst2;

    @Before
    public void setUp() throws Exception {
        inst = new ByteArrayItem(1);
        inst2 = new ByteArrayItem(8);
    }

    @Test
    public void testName() {
        Assert.assertEquals("byte[1]", inst.getTypeName());
    }

    @Test
    public void testTypeExport() throws DataImportException {
        inst.importDataString("00");
        Assert.assertEquals("byte[1] : 00", inst.exportTypeAndDataString(false));

        inst.importDataString("01");
        Assert.assertEquals("byte[1] : 01", inst.exportTypeAndDataString(false));

        inst.importDataString("DE");
        Assert.assertEquals("byte[1] : DE", inst.exportTypeAndDataString(false));

        inst.importDataString("FF");
        Assert.assertEquals("byte[1] : FF", inst.exportTypeAndDataString(false));

        inst2.importDataString("00 00 00 00 00 00 00 00");
        Assert.assertEquals("byte[8] : 00 00 00 00 00 00 00 00", inst2.exportTypeAndDataString(false));

        inst2.importDataString("01 10 02 20 03 30 9A FF");
        Assert.assertEquals("byte[8] : 01 10 02 20 03 30 9A FF", inst2.exportTypeAndDataString(false));

        inst2.importDataString("DE AD BE EF 77 88 99 AA");
        Assert.assertEquals("byte[8] : DE AD BE EF 77 88 99 AA", inst2.exportTypeAndDataString(false));

        inst2.importDataString("FF FF FF FF FF FF FF FF");
        Assert.assertEquals("byte[8] : FF FF FF FF FF FF FF FF", inst2.exportTypeAndDataString(false));
    }

    @Test
    public void testSingleImportExportString() throws DataImportException {
        inst.importDataString("FF");
        Assert.assertEquals("FF", inst.exportDataString());

        inst.importDataString("00");
        Assert.assertEquals("00", inst.exportDataString());

        inst.importDataString("7A");
        Assert.assertEquals("7A", inst.exportDataString());

        try {
            // to short
            inst.importDataString("");
            fail("invalid data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            inst.importDataString("100");
            fail("invalid data");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            inst2.importDataString("100");
            fail("invalid data");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            inst2.importDataString("00 100 00 11 22 33 44 55");
            fail("invalid data");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testMultipleImportExportString() throws DataImportException {
        inst2.importDataString("00 10 20 30 FF 50 60 DD");
        Assert.assertEquals("00 10 20 30 FF 50 60 DD", inst2.exportDataString());

        try {
            // to short
            inst2.importDataString("");
            fail("invalid data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            // to short
            inst2.importDataString("00 10 20 30 FF 50 60");
            fail("to short data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            // to short
            inst2.importDataString("00 10 20 30 FF 50 60 DD FF");
            fail("to long data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            inst.importDataString("test");
            fail("invalid data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            inst.importDataString("00 XX 20 30 FF 50 60 DD");
            fail("invalid data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testClone() throws CloneNotSupportedException, DataImportException {
        inst2.importDataString("00 10 20 30 FF 50 60 DD");
        ArrayDataItem<ByteItem, Void> instClone = inst2.clone();
        inst2.importDataString("FF 10 20 30 FF 50 60 DD");
        Assert.assertEquals("00 10 20 30 FF 50 60 DD", instClone.exportDataString());
    }
}
