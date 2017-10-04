package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.ArrayDataItem;
import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.FloatArrayItem;
import de.as.esptools.configloader.datatypes.FloatItem;

public class FloatArrayItemTest {

    private FloatArrayItem inst;

    @Before
    public void setUp() throws Exception {
        this.inst = new FloatArrayItem(4);
    }

    @Test
    public void testName() {
        Assert.assertEquals("float[4]", inst.getTypeName());
    }

    @Test
    public void testTypeExport() throws DataImportException {
        inst.importDataString("1 0 1.01 -1");
        Assert.assertEquals("float[4] : 1 0 1.01 -1", inst.exportTypeAndDataString(false));

        inst.importDataString("1000.99 1000.99 1000.99 -1000.99");
        Assert.assertEquals("float[4] : 1000.99 1000.99 1000.99 -1000.99", inst.exportTypeAndDataString(false));

        inst.importDataString("123 456 789 098");
        Assert.assertEquals("float[4] : 123 456 789 98", inst.exportTypeAndDataString(false));

        inst.importDataString("-123 456 -789 098.1");
        Assert.assertEquals("float[4] : -123 456 -789 98.1", inst.exportTypeAndDataString(false));
    }

    @Test
    public void testImportExportString() throws DataImportException {
        inst.importDataString("992 928 387 2");
        Assert.assertEquals("992 928 387 2", inst.exportDataString());

        inst.importDataString("00.01 000.001 0.01 .01");
        Assert.assertEquals("0.01 0.001 0.01 0.01", inst.exportDataString());

        try {
            // to short
            inst.importDataString("999 10 1");
            // Assert.assertEquals("0", inst.exportDataString());
            fail("to short data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            // to long
            inst.importDataString("12 20 30 40 51");
            // Assert.assertEquals("1", inst.exportDataString());
            fail("to long data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            inst.importDataString("test 1 2 3");
            fail("invalid data should not be imported");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testClone() throws CloneNotSupportedException, DataImportException {
        inst.importDataString("992 928 387 2");
        ArrayDataItem<FloatItem, Void> instClone = inst.clone();
        Assert.assertEquals("992 928 387 2", instClone.exportDataString());
    }
}
