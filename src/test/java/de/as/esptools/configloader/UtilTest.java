package de.as.esptools.configloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public void testFloatBytes() {
		float f = 1f;
		byte[] bytes = Util.floatToByteArray(f);
		Assert.assertEquals(f, Util.byteArrayToFloat(bytes), 0.0);

		f = 1000000000000f;
		bytes = Util.floatToByteArray(f);
		Assert.assertEquals(f, Util.byteArrayToFloat(bytes), 0.0);

		f = 1000000000000.9999f;
		bytes = Util.floatToByteArray(f);
		Assert.assertEquals(f, Util.byteArrayToFloat(bytes), 0.0);

		f = 0.1000000000000f;
		bytes = Util.floatToByteArray(f);
		Assert.assertEquals(f, Util.byteArrayToFloat(bytes), 0.0);

		f = -1f;
		bytes = Util.floatToByteArray(f);
		Assert.assertEquals(f, Util.byteArrayToFloat(bytes), 0.0);

		f = -1000000000000f;
		bytes = Util.floatToByteArray(f);
		Assert.assertEquals(f, Util.byteArrayToFloat(bytes), 0.0);

		f = -1000000000000.9999f;
		bytes = Util.floatToByteArray(f);
		Assert.assertEquals(f, Util.byteArrayToFloat(bytes), 0.0);

		f = -0.1000000000000f;
		bytes = Util.floatToByteArray(f);
		Assert.assertEquals(f, Util.byteArrayToFloat(bytes), 0.0);
	}

	@Test
	public void testSearchTokenForBraces() {
		Assert.assertEquals("test", Util.searchTokenForBraces("{test}"));
		Assert.assertEquals("test", Util.searchTokenForBraces("123{test}456"));
		Assert.assertEquals(" test ", Util.searchTokenForBraces("123{ test }456"));
		Assert.assertEquals(" test ", Util.searchTokenForBraces(" { test } "));
		Assert.assertEquals("123 { test } 456", Util.searchTokenForBraces(" {123 { test } 456}"));
		Assert.assertEquals(null, Util.searchTokenForBraces(" {123 { test } 456"));
		Assert.assertEquals(null, Util.searchTokenForBraces(" {123 "));
		Assert.assertEquals("{123 { test } 456}", Util.searchTokenForBraces(" {{123 { test } 456}}"));
		Assert.assertEquals("{123 { test }{ttt} 456}", Util.searchTokenForBraces(" {{123 { test }{ttt} 456}}"));
		Assert.assertEquals("{123 { test }sdfsdf{ttt} 456}",
				Util.searchTokenForBraces(" {{123 { test }sdfsdf{ttt} 456}}"));
	}

	@Test
	public void testSearchBracedBlock() {
		String data = "test : test\r\n" + "}";
		List<String> l = new ArrayList<String>();
		String rest = Util.readBracedBlock(data, l);
		String list = String.join("|", l);
		Assert.assertEquals("", rest);
		Assert.assertEquals("test : test", list);

		data = "test : testN\r\n" + "test2:testN2\r\n" + "}\r\n" + "test3:testN3\r\n";
		l = new ArrayList<String>();
		rest = Util.readBracedBlock(data, l);
		list = String.join("|", l);
		Assert.assertEquals("test3:testN3\r\n", rest);
		Assert.assertEquals("test : testN|test2:testN2", list);

		data = "test : testN\r\n" + "struct b2 {\r\n" + " 2b : 2b\r\n" + " }\r\n" + "test2:testN2\r\n" + "}\r\n"
				+ "test3:testN3";
		l = new ArrayList<String>();
		rest = Util.readBracedBlock(data, l);
		list = String.join("|", l);
		Assert.assertEquals("test3:testN3", rest);
		Assert.assertEquals("test : testN|struct b2 {| 2b : 2b| }|test2:testN2", list);
	}

}
