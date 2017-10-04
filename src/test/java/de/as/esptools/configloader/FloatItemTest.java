package de.as.esptools.configloader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.DataItem;
import de.as.esptools.configloader.datatypes.FloatItem;

public class FloatItemTest {

	private FloatItem inst;

	@Before
	public void setUp() throws Exception {
		inst = new FloatItem();
	}

	@Test
	public void testName() {
		Assert.assertEquals("float", inst.getTypeName());
	}

	@Test
	public void testBinLength() {
		Assert.assertEquals(4, inst.getBinLength());
	}

	@Test
	public void testTypeExport() throws DataImportException {
		inst.importDataString("1.01");
		Assert.assertEquals("float : 1.01", inst.exportTypeAndDataString(false));

		inst.importDataString("1000.99");
		Assert.assertEquals("float : 1000.99", inst.exportTypeAndDataString(false));

		inst.importDataString("1");
		Assert.assertEquals("float : 1", inst.exportTypeAndDataString(false));

		inst.importDataString("0");
		Assert.assertEquals("float : 0", inst.exportTypeAndDataString(false));
	}

	@Test
	public void testImportString() throws DataImportException {
		inst.importDataString("1234");
		Assert.assertEquals(1234f, inst.getFloat(), 0.0);
		// Assert.assertEquals("1234", inst.exportString());

		inst.importDataString("-1234");
		Assert.assertEquals(-1234f, inst.getFloat(), 0.0);

		inst.importDataString("-0");
		Assert.assertEquals(0f, inst.getFloat(), 0.0);

		inst.importDataString("0");
		Assert.assertEquals(0f, inst.getFloat(), 0.0);

		inst.importDataString("123456780");
		Assert.assertEquals(123456780f, inst.getFloat(), 0.0);

		inst.importDataString("-123456780");
		Assert.assertEquals(-123456780f, inst.getFloat(), 0.0);

		inst.importDataString("1234.1234");
		Assert.assertEquals(1234.1234f, inst.getFloat(), 0.0);

		inst.importDataString("0.00000000001");
		Assert.assertEquals(0.00000000001f, inst.getFloat(), 0.0);

		inst.importDataString("123456780.0987654321");
		Assert.assertEquals(123456780.0987654321f, inst.getFloat(), 0.0);

		inst.importDataString("-0.00000000001");
		Assert.assertEquals(-0.00000000001f, inst.getFloat(), 0.0);

		inst.importDataString("-123456780.0987654321");
		Assert.assertEquals(-123456780.0987654321f, inst.getFloat(), 0.0);
	}

	@Test
	public void testExportString() throws DataImportException {
		inst.setFloat(1f);
		Assert.assertEquals("1", inst.exportDataString());

		inst.setFloat(-1f);
		Assert.assertEquals("-1", inst.exportDataString());

		inst.setFloat(567890f);
		Assert.assertEquals("567890", inst.exportDataString());

		inst.setFloat(-567890f);
		Assert.assertEquals("-567890", inst.exportDataString());

		inst.setFloat(0.001f);
		Assert.assertEquals("0.001", inst.exportDataString());

		inst.setFloat(0.101f);
		Assert.assertEquals("0.101", inst.exportDataString());

		inst.setFloat(-0.101f);
		Assert.assertEquals("-0.101", inst.exportDataString());

		inst.setFloat(1.101f);
		Assert.assertEquals("1.101", inst.exportDataString());

		inst.setFloat(54321.02f);
		Assert.assertEquals("54321.02", inst.exportDataString());
	}

	@Test
	public void testGetSetFloat() throws DataImportException {
		inst.setFloat(1234567890f);
		Assert.assertEquals(1234567890f, inst.getFloat(), 0.0);

		inst.setFloat(-1234567890f);
		Assert.assertEquals(-1234567890f, inst.getFloat(), 0.0);

		inst.setFloat(0f);
		Assert.assertEquals(0f, inst.getFloat(), 0.0);

		inst.setFloat(-0f);
		Assert.assertEquals(0f, inst.getFloat(), 0.0);

		inst.setFloat(1f);
		Assert.assertEquals(1f, inst.getFloat(), 0.0);

		inst.setFloat(-1f);
		Assert.assertEquals(-1f, inst.getFloat(), 0.0);

		inst.setFloat(-1.00f);
		Assert.assertEquals(-1f, inst.getFloat(), 0.0);

		inst.setFloat(1234567890.0987654321f);
		Assert.assertEquals(1234567890.0987654321f, inst.getFloat(), 0.0);

		inst.setFloat(0.000000000000001f);
		Assert.assertEquals(0.000000000000001f, inst.getFloat(), 0.0);

		inst.setFloat(-0.000000000000001f);
		Assert.assertEquals(-0.000000000000001f, inst.getFloat(), 0.0);
	}

	@Test
    public void testClone() throws CloneNotSupportedException, DataImportException {
        inst.setFloat(-0.1f);
        DataItem instClone = inst.clone();
        Assert.assertEquals("-0.1", instClone.exportDataString());
    }
	
}
