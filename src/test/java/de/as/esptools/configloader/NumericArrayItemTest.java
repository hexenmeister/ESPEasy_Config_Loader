package de.as.esptools.configloader;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.NumericArrayItem;

public class NumericArrayItemTest {

	NumericArrayItemInst inst14s;
	NumericArrayItemInst inst14u;
	
	static class NumericArrayItemInst extends NumericArrayItem {

		protected NumericArrayItemInst(int length, int bytesPerItem, boolean singned) {
			super(length, bytesPerItem, singned);
		}

		@Override
		public long bytesToLong(byte[] bytes) {
			return super.bytesToLong(bytes);
		}

		@Override
		public byte[] longToReverseByteArray(long value, int lenghtInBytes) {
			return super.longToReverseByteArray(value, lenghtInBytes);
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
		byte[] bytes = new byte[] {(byte) 0xD1, (byte) 0x68, (byte) 0x2B, (byte) 0x78}; // reverse order!
		long l = inst14s.bytesToLong(bytes);
		Assert.assertEquals(2016110801l, l);
		
		bytes = new byte[] {(byte) 0x2E, (byte) 0xFD, (byte) 0x69, (byte) 0xB6};
		l = inst14s.bytesToLong(bytes);
		Assert.assertEquals(-1234567890l, l);
		
		bytes = new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
		l = inst14s.bytesToLong(bytes);
		Assert.assertEquals(-1l, l);
		
		bytes = new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00};
		l = inst14s.bytesToLong(bytes);
		Assert.assertEquals(1l, l);
		
		bytes = new byte[] {(byte) 0xFF, (byte) 0xFF};
		l = inst14s.bytesToLong(bytes);
		Assert.assertEquals(-1l, l);
		
		bytes = new byte[] {(byte) 0x01, (byte) 0x00};
		l = inst14s.bytesToLong(bytes);
		Assert.assertEquals(1l, l);
		
		bytes = new byte[] {(byte) 0xFF};
		l = inst14s.bytesToLong(bytes);
		Assert.assertEquals(-1l, l);
		
		bytes = new byte[] {(byte) 0x01};
		l = inst14s.bytesToLong(bytes);
		Assert.assertEquals(1l, l);
	}

	@Test
	public void testLongToReverseByteArray() {
		byte[] bytes = inst14s.longToReverseByteArray(2016110801l, 4);
		Assert.assertTrue(Arrays.equals(new byte[]{(byte) 0xD1, (byte) 0x68, (byte) 0x2B, (byte) 0x78}, bytes)); // reverse order!
		
		bytes = inst14s.longToReverseByteArray(-1234567890l, 4);
		Assert.assertTrue(Arrays.equals(new byte[]{(byte) 0x2E, (byte) 0xFD, (byte) 0x69, (byte) 0xB6}, bytes));
		
		bytes = inst14s.longToReverseByteArray(-1l, 4);
		Assert.assertTrue(Arrays.equals(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, bytes));
		
		bytes = inst14s.longToReverseByteArray(1l, 4);
		Assert.assertTrue(Arrays.equals(new byte[]{(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00}, bytes));
		
		bytes = inst14s.longToReverseByteArray(-1l, 2);
		Assert.assertTrue(Arrays.equals(new byte[]{(byte) 0xFF, (byte) 0xFF}, bytes));
		
		bytes = inst14s.longToReverseByteArray(1l, 2);
		Assert.assertTrue(Arrays.equals(new byte[]{(byte) 0x01, (byte) 0x00}, bytes));
		
		bytes = inst14s.longToReverseByteArray(-1l, 1);
		Assert.assertTrue(Arrays.equals(new byte[]{(byte) 0xFF}, bytes));
		
		bytes = inst14s.longToReverseByteArray(1l, 1);
		Assert.assertTrue(Arrays.equals(new byte[]{(byte) 0x01}, bytes));
	}

}
