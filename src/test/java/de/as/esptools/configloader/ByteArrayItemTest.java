package de.as.esptools.configloader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.ByteArrayItem;
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
	public void testSingleImportExportString() throws DataImportException {
		inst.importString("FF");
		
		Assert.assertEquals("FF", inst.exportString());
	}

	@Test
	public void testMultipleImportExportString() throws DataImportException {
		inst2.importString("00 10 20 30 FF 50 60 DD");
		
		Assert.assertEquals("00 10 20 30 FF 50 60 DD", inst2.exportString());
	}
}
