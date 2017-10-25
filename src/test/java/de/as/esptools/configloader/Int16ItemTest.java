package de.as.esptools.configloader;

import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.Int16Item;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class Int16ItemTest {

    private Int16Item insts;
    private Int16Item instu;

    @Before
    public void setUp() throws Exception {
        this.insts = new Int16Item(true);
        this.instu = new Int16Item(false);
    }

    @Test
    public void testName() {
        Assert.assertEquals("int16", this.insts.getTypeName());
        Assert.assertEquals("uint16", this.instu.getTypeName());
    }

    @Test
    public void testBinLength() {
        Assert.assertEquals(2, this.insts.getBinLength());
        Assert.assertEquals(2, this.instu.getBinLength());
    }

    @Test
    public void testIsSigned() {
        Assert.assertEquals(true, this.insts.isSigned());
        Assert.assertEquals(false, this.instu.isSigned());
    }

    @Test
    public void testTypeExportSigned() throws DataImportException {
        this.insts.importDataString("0");
        Assert.assertEquals("int16 : 0", this.insts.exportTypeAndDataString(false));

        this.insts.importDataString("1");
        Assert.assertEquals("int16 : 1", this.insts.exportTypeAndDataString(false));

        this.insts.importDataString("-1");
        Assert.assertEquals("int16 : -1", this.insts.exportTypeAndDataString(false));

        this.insts.importDataString("127");
        Assert.assertEquals("int16 : 127", this.insts.exportTypeAndDataString(false));

        this.insts.importDataString("-128");
        Assert.assertEquals("int16 : -128", this.insts.exportTypeAndDataString(false));

        this.insts.importDataString("32767");
        Assert.assertEquals("int16 : 32767", this.insts.exportTypeAndDataString(false));

        this.insts.importDataString("-32768");
        Assert.assertEquals("int16 : -32768", this.insts.exportTypeAndDataString(false));

        try {
            this.insts.importDataString("32768");
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            this.insts.importDataString("-32769");
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testTypeExportUnsigned() throws DataImportException {
        this.instu.importDataString("0");
        Assert.assertEquals("uint16 : 0", this.instu.exportTypeAndDataString(false));

        this.instu.importDataString("1");
        Assert.assertEquals("uint16 : 1", this.instu.exportTypeAndDataString(false));

        try {
            this.instu.importDataString("-1");
            Assert.assertEquals("uint16 : -1", this.instu.exportTypeAndDataString(false));
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }

        this.instu.importDataString("127");
        Assert.assertEquals("uint16 : 127", this.instu.exportTypeAndDataString(false));

        try {
            this.instu.importDataString("-128");
            Assert.assertEquals("uint16 : -128", this.instu.exportTypeAndDataString(false));
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }

        this.instu.importDataString("65535");
        Assert.assertEquals("uint16 : 65535", this.instu.exportTypeAndDataString(false));

        try {
            this.instu.importDataString("65536");
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testImportStringSigned() throws DataImportException {
        this.insts.importDataString("0");
        Assert.assertEquals("0", this.insts.exportDataString());

        this.insts.importDataString("-0");
        Assert.assertEquals("0", this.insts.exportDataString());

        this.insts.importDataString("1");
        Assert.assertEquals("1", this.insts.exportDataString());

        this.insts.importDataString("-1");
        Assert.assertEquals("-1", this.insts.exportDataString());

        this.insts.importDataString("127");
        Assert.assertEquals("127", this.insts.exportDataString());

        this.insts.importDataString("255");
        Assert.assertEquals("255", this.insts.exportDataString());

        this.insts.importDataString("-128");
        Assert.assertEquals("-128", this.insts.exportDataString());

        this.insts.importDataString("32767");
        Assert.assertEquals("32767", this.insts.exportDataString());

        this.insts.importDataString("-32768");
        Assert.assertEquals("-32768", this.insts.exportDataString());

        try {
            this.insts.importDataString("32768");
            fail("too big");
        } catch (DataImportException e) {
            // NOP
        }
        try {
            this.insts.importDataString("-32769");
            fail("too small");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            this.insts.importDataString("0.1");
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            this.insts.importDataString("test");
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testImportStringUnsigned() throws DataImportException {
        this.instu.importDataString("0");
        Assert.assertEquals("0", this.instu.exportDataString());

        this.instu.importDataString("1");
        Assert.assertEquals("1", this.instu.exportDataString());

        this.instu.importDataString("127");
        Assert.assertEquals("127", this.instu.exportDataString());

        this.instu.importDataString("255");
        Assert.assertEquals("255", this.instu.exportDataString());

        this.instu.importDataString("65535");
        Assert.assertEquals("65535", this.instu.exportDataString());

        try {
            this.instu.importDataString("65536");
            fail("too big");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            this.instu.importDataString("-1");
            fail("too small");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            this.instu.importDataString("0.1");
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            this.instu.importDataString("test");
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testExportStringSigned() throws DataImportException {
        this.insts.setNumber(0);
        Assert.assertEquals("0", this.insts.exportDataString());

        this.insts.setNumber(1);
        Assert.assertEquals("1", this.insts.exportDataString());

        this.insts.setNumber(-1);
        Assert.assertEquals("-1", this.insts.exportDataString());

        this.insts.setNumber(127);
        Assert.assertEquals("127", this.insts.exportDataString());

        this.insts.setNumber(-128);
        Assert.assertEquals("-128", this.insts.exportDataString());

        this.insts.setNumber(32767);
        Assert.assertEquals("32767", this.insts.exportDataString());

        this.insts.setNumber(-32768);
        Assert.assertEquals("-32768", this.insts.exportDataString());

        try {
            this.insts.setNumber(32768);
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }

        try {
            this.insts.setNumber(-32769);
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testExportStringUnsigned() throws DataImportException {
        this.instu.setNumber(0);
        Assert.assertEquals("0", this.instu.exportDataString());

        this.instu.setNumber(1);
        Assert.assertEquals("1", this.instu.exportDataString());

        try {
            this.instu.setNumber(-1);
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }

        this.instu.setNumber(127);
        Assert.assertEquals("127", this.instu.exportDataString());

        this.instu.setNumber(255);
        Assert.assertEquals("255", this.instu.exportDataString());

        this.instu.setNumber(65535);
        Assert.assertEquals("65535", this.instu.exportDataString());

        try {
            this.instu.setNumber(65536);
            fail("invalid");
        } catch (DataImportException e) {
            // NOP
        }
    }

    @Test
    public void testGetSetNumberSigned() throws DataImportException {
        this.insts.setNumber(0);
        Assert.assertEquals(0, this.insts.getNumber());

        this.insts.setNumber(1);
        Assert.assertEquals(1, this.insts.getNumber());

        this.insts.setNumber(-1);
        Assert.assertEquals(-1, this.insts.getNumber());

        this.insts.setNumber(127);
        Assert.assertEquals(127, this.insts.getNumber());

        this.insts.setNumber(-128);
        Assert.assertEquals(-128, this.insts.getNumber());

        this.insts.setNumber(32767);
        Assert.assertEquals(32767, this.insts.getNumber());

        this.insts.setNumber(-32768);
        Assert.assertEquals(-32768, this.insts.getNumber());
    }

    @Test
    public void testGetSetNumberUnsigned() throws DataImportException {
        this.instu.setNumber(0);
        Assert.assertEquals(0, this.instu.getNumber());

        this.instu.setNumber(1);
        Assert.assertEquals(1, this.instu.getNumber());

        this.instu.setNumber(127);
        Assert.assertEquals(127, this.instu.getNumber());

        this.instu.setNumber(255);
        Assert.assertEquals(255, this.instu.getNumber());

        this.instu.setNumber(65535);
        Assert.assertEquals(65535, this.instu.getNumber());
    }

}
