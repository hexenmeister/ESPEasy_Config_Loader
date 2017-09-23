package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.BooleanItem;
import de.as.esptools.configloader.datatypes.DataImportException;

public class BooleanItemTest {

	private BooleanItem inst;

	@Before
	public void setUp() throws Exception {
		this.inst = new BooleanItem();
	}

	@Test
	public void testImportExportString() throws DataImportException {
		inst.importString("true");
		Assert.assertEquals("1", inst.exportString());
		
		inst.importString("false");
		Assert.assertEquals("0", inst.exportString());
		
		inst.importString("0");
		Assert.assertEquals("0", inst.exportString());

		inst.importString("1");
		Assert.assertEquals("1", inst.exportString());
		
		inst.importString("00");
		Assert.assertEquals("0", inst.exportString());
		
		inst.importString("01");
		Assert.assertEquals("1", inst.exportString());
		
		try {
			inst.importString("test");
			fail("Not yet implemented");
		} catch (DataImportException e) {
			// NOP
		}
	}

}
