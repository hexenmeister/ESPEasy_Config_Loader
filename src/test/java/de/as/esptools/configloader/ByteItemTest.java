package de.as.esptools.configloader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.ByteItem;
import de.as.esptools.configloader.datatypes.DataImportException;

public class ByteItemTest {

	private ByteItem inst;
	
	@Before
	public void setUp() throws Exception {
		this.inst = new ByteItem();
	}

	@Test
	public void testImportExportString() throws DataImportException {
		inst.importString("FF");
		Assert.assertEquals("FF", inst.exportString());
		
		inst.importString("00");
		Assert.assertEquals("00", inst.exportString());

		inst.importString("01");
		Assert.assertEquals("01", inst.exportString());
		
		inst.importString("0A");
		Assert.assertEquals("0A", inst.exportString());
		
		inst.importString("C0");
		Assert.assertEquals("C0", inst.exportString());
		
		inst.importString("99");
		Assert.assertEquals("99", inst.exportString());
		
		inst.importString("DA");
		Assert.assertEquals("DA", inst.exportString());

		inst.importString("87");
		Assert.assertEquals("87", inst.exportString());
	}

}
