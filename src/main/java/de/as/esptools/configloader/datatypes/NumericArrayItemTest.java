package de.as.esptools.configloader.datatypes;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NumericArrayItemTest {

	NumericArrayItem inst14s;
	NumericArrayItem inst14u;
	
	static class NumericArrayItemInst extends NumericArrayItem {

		protected NumericArrayItemInst(int length, int bytesPerItem, boolean singned) {
			super(length, bytesPerItem, singned);
		}
		
	}
	
	@Before
	public void setUp() throws Exception {
		inst14s = new NumericArrayItemInst(1, 4, true);
		inst14u = new NumericArrayItemInst(1, 4, false);
	}

	@Test
	public void testImportString() {
		fail("Not yet implemented");
	}

	@Test
	public void testExportString() {
		fail("Not yet implemented");
	}

	@Test
	public void testBytesToLong() {
		byte[] bytes = new byte[] {(byte) 0xD1, (byte) 0x68, (byte) 0x2B, (byte) 0x78, (byte) 0x02};
		long l = NumericArrayItem.bytesToLong(bytes);
		Assert.assertEquals(2016110801l, l);
	}

	@Test
	public void testLongToReverseByteArray() {
		fail("Not yet implemented");
	}

}
