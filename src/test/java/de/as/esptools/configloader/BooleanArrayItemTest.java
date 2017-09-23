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
	public void testImportExportString() throws DataImportException {
		inst.importString("false false false false false false false false false false ");
		Assert.assertEquals("0 0 0 0 0 0 0 0 0 0", inst.exportString());

		inst.importString("true true true true true true true true true true ");
		Assert.assertEquals("1 1 1 1 1 1 1 1 1 1", inst.exportString());

		inst.importString("true false true false true false true false true false ");
		Assert.assertEquals("1 0 1 0 1 0 1 0 1 0", inst.exportString());

		inst.importString("0 0 0 0 0 0 0 0 0 0");
		Assert.assertEquals("0 0 0 0 0 0 0 0 0 0", inst.exportString());

		inst.importString("1 1 1 1 1 1 1 1 1 1");
		Assert.assertEquals("1 1 1 1 1 1 1 1 1 1", inst.exportString());

		inst.importString("00 00 00 00 00 00 00 00 00 00");
		Assert.assertEquals("0 0 0 0 0 0 0 0 0 0", inst.exportString());

		inst.importString("01 01 01 01 01 01 01 01 01 01");
		Assert.assertEquals("1 1 1 1 1 1 1 1 1 1", inst.exportString());

		inst.importString("1 0 1 0 1 0 1 0 1 0");
		Assert.assertEquals("1 0 1 0 1 0 1 0 1 0", inst.exportString());

		try {
			inst.importString("0 0 1 0"); // to short
			Assert.assertEquals("0", inst.exportString());
			fail("Not yet implemented");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			inst.importString("1 0 0 0 1 1 0 0 0 1 1 0 1 0 0 1 0"); // to long
			Assert.assertEquals("1", inst.exportString());
			fail("Not yet implemented");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			inst.importString("test 1 2 3 4 5 6 7 8 9");
			fail("Not yet implemented");
		} catch (DataImportException e) {
			// NOP
		}
	}

}
