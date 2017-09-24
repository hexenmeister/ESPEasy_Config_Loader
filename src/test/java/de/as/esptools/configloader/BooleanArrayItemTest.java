package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.BooleanArrayItem;
import de.as.esptools.configloader.datatypes.DataImportException;

public class BooleanArrayItemTest {

	private BooleanArrayItem inst;

	@Before
	public void setUp() throws Exception {
		this.inst = new BooleanArrayItem(10);
	}

	@Test
	public void testName() {
		Assert.assertEquals("boolean", inst.getTypeName());
	}

	@Test
	public void testTypeExport() throws DataImportException {
		// TODO
//		inst.importDataString("A");
//		Assert.assertEquals("char[1] : A", inst.exportTypeAndDataString(false));
//		Assert.assertEquals("char[1] :          A", inst.exportTypeAndDataString(true));
//
//		inst2.importDataString("A");
//		Assert.assertEquals("char[8] : A", inst.exportTypeAndDataString(false));
//		Assert.assertEquals("char[8] :          A", inst.exportTypeAndDataString(true));
	}

	@Test
	public void testImportExportString() throws DataImportException {
		inst.importDataString("false false false false false false false false false false ");
		Assert.assertEquals("0 0 0 0 0 0 0 0 0 0", inst.exportDataString());

		inst.importDataString("true true true true true true true true true true ");
		Assert.assertEquals("1 1 1 1 1 1 1 1 1 1", inst.exportDataString());

		inst.importDataString("true false true false true false true false true false ");
		Assert.assertEquals("1 0 1 0 1 0 1 0 1 0", inst.exportDataString());

		inst.importDataString("0 0 0 0 0 0 0 0 0 0");
		Assert.assertEquals("0 0 0 0 0 0 0 0 0 0", inst.exportDataString());

		inst.importDataString("1 1 1 1 1 1 1 1 1 1");
		Assert.assertEquals("1 1 1 1 1 1 1 1 1 1", inst.exportDataString());

		inst.importDataString("00 00 00 00 00 00 00 00 00 00");
		Assert.assertEquals("0 0 0 0 0 0 0 0 0 0", inst.exportDataString());

		inst.importDataString("01 01 01 01 01 01 01 01 01 01");
		Assert.assertEquals("1 1 1 1 1 1 1 1 1 1", inst.exportDataString());

		inst.importDataString("1 0 1 0 1 0 1 0 1 0");
		Assert.assertEquals("1 0 1 0 1 0 1 0 1 0", inst.exportDataString());

		try {
			// to short
			inst.importDataString("0 0 1 0");
			// Assert.assertEquals("0", inst.exportDataString());
			fail("to short data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			// to long
			inst.importDataString("1 0 0 0 1 1 0 0 0 1 1 0 1 0 0 1 0");
			// Assert.assertEquals("1", inst.exportDataString());
			fail("to long data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			inst.importDataString("test 1 2 3 4 5 6 7 8 9");
			fail("invalid data should not be imported");
		} catch (DataImportException e) {
			// NOP
		}
	}

}
