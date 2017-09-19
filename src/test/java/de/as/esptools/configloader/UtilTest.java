package de.as.esptools.configloader;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.util.Util;

public class UtilTest {

	@Test
	public void testBytesToHex() {
		String hex = Util.bytesToHex(new byte[] { (byte) 0x00 });
		Assert.assertEquals("00", hex);

		hex = Util.bytesToHex(new byte[] { (byte) 0xFF });
		Assert.assertEquals("FF", hex);

		hex = Util.bytesToHex(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF });
		Assert.assertEquals("FF FF FF FF FF", hex);

		hex = Util.bytesToHex(new byte[] { (byte) 0x10, (byte) 0x11, (byte) 0x20, (byte) 0x30, (byte) 0x40, (byte) 0x50,
				(byte) 0x60, (byte) 0x99, (byte) 0xA0, (byte) 0xBA, (byte) 0xCD, (byte) 0xEF });
		Assert.assertEquals("10 11 20 30 40 50 60 99 A0 BA CD EF", hex);
	}

	@Test
	public void testReverseByteArrayToLong() {
		// reverse bytes order!
		byte[] bytes = new byte[] { (byte) 0xD1, (byte) 0x68, (byte) 0x2B, (byte) 0x78 };
		long l = Util.reverseByteArrayToLong(bytes, true);
		Assert.assertEquals(2016110801l, l);

		bytes = new byte[] { (byte) 0xD1, (byte) 0x68, (byte) 0x2B, (byte) 0x78 };
		l = Util.reverseByteArrayToLong(bytes, false);
		Assert.assertEquals(2016110801l, l);

		bytes = new byte[] { (byte) 0x2E, (byte) 0xFD, (byte) 0x69, (byte) 0xB6 };
		l = Util.reverseByteArrayToLong(bytes, true);
		Assert.assertEquals(-1234567890l, l);

		bytes = new byte[] { (byte) 0x2E, (byte) 0xFD, (byte) 0x69, (byte) 0xB6 };
		l = Util.reverseByteArrayToLong(bytes, false);
		Assert.assertEquals(3060399406l, l);

		bytes = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		l = Util.reverseByteArrayToLong(bytes, true);
		Assert.assertEquals(-1l, l);

		bytes = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		l = Util.reverseByteArrayToLong(bytes, false);
		Assert.assertEquals(4294967295l, l);

		bytes = new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
		l = Util.reverseByteArrayToLong(bytes, true);
		Assert.assertEquals(1l, l);

		bytes = new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
		l = Util.reverseByteArrayToLong(bytes, false);
		Assert.assertEquals(1l, l);

		bytes = new byte[] { (byte) 0xFF, (byte) 0xFF };
		l = Util.reverseByteArrayToLong(bytes, true);
		Assert.assertEquals(-1l, l);

		bytes = new byte[] { (byte) 0x01, (byte) 0x00 };
		l = Util.reverseByteArrayToLong(bytes, true);
		Assert.assertEquals(1l, l);

		bytes = new byte[] { (byte) 0xFF };
		l = Util.reverseByteArrayToLong(bytes, true);
		Assert.assertEquals(-1l, l);

		bytes = new byte[] { (byte) 0xFF };
		l = Util.reverseByteArrayToLong(bytes, false);
		Assert.assertEquals(255l, l);

		bytes = new byte[] { (byte) 0x01 };
		l = Util.reverseByteArrayToLong(bytes, true);
		Assert.assertEquals(1l, l);

		bytes = new byte[] { (byte) 0x01 };
		l = Util.reverseByteArrayToLong(bytes, false);
		Assert.assertEquals(1l, l);
	}

	@Test
	public void testLongToReverseByteArray() {
		byte[] bytes = Util.longToReverseByteArray(2016110801l, 4);
		// reverse bytes order!
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0xD1, (byte) 0x68, (byte) 0x2B, (byte) 0x78 }, bytes));

		bytes = Util.longToReverseByteArray(-1234567890l, 4);
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0x2E, (byte) 0xFD, (byte) 0x69, (byte) 0xB6 }, bytes));

		bytes = Util.longToReverseByteArray(-1l, 4);
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, bytes));

		bytes = Util.longToReverseByteArray(1l, 4);
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, bytes));

		bytes = Util.longToReverseByteArray(-1l, 2);
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0xFF, (byte) 0xFF }, bytes));

		bytes = Util.longToReverseByteArray(1l, 2);
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0x01, (byte) 0x00 }, bytes));

		bytes = Util.longToReverseByteArray(-1l, 1);
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0xFF }, bytes));

		bytes = Util.longToReverseByteArray(1l, 1);
		Assert.assertTrue(Arrays.equals(new byte[] { (byte) 0x01 }, bytes));
	}
	
	@Test
	public void testFloat() {
		float f = 1f;
		java.nio.ByteBuffer buf = java.nio.ByteBuffer.allocate(4);
		buf.putFloat(f);
		byte[] bytes = buf.array();
		System.out.println(Arrays.toString(bytes));
		java.nio.ByteBuffer buf2 = java.nio.ByteBuffer.allocate(4);
		buf2.put(bytes);
		float f2 = buf2.getFloat(0);
		
		Assert.assertEquals(f, f2, 0.0);
	}

}
