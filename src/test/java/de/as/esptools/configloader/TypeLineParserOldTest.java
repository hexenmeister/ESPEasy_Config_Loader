package de.as.esptools.configloader;

import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.util.TypeLineParserOld;

public class TypeLineParserOldTest {

	@Test
	public void testParser() {
		String data = "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "  /* comment 1 test  */ type name : data  ww \r\n"
				+ "                        type name : data  ww // comment2\r\n"
				+ "                        type name : data  ww\r\n"
				+ "  /* comment 1 test  */ type      : data  ww // comment2 x\r\n"
				+ "  /* comment 1 test  */ type      : data  ww\r\n"
				+ "                        type      : data  1 // comment2  \r\n"
				+ "  /* comment 1 test  */           : data  2 // comment2 \r\n"
				+ "  /* comment 1 test  */           : data  3\r\n"
				+ "                                  : data  4 // comment2\r\n"
				+ "                                  : data  5        \r\n"
				+ "                                  : data line 2\r\n" + "/*C1*/T N:D//C2";

		TypeLineParserOld inst = new TypeLineParserOld(data);

		// System.out.println(inst);

		inst.next();
		Assert.assertEquals("comment 1 test", inst.getItemComment1());
		Assert.assertEquals("comment2", inst.getItemComment2());
		Assert.assertEquals("type", inst.getItemType());
		Assert.assertEquals("name", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("comment 1 test", inst.getItemComment1());
		Assert.assertEquals("", inst.getItemComment2());
		Assert.assertEquals("type", inst.getItemType());
		Assert.assertEquals("name", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("", inst.getItemComment1());
		Assert.assertEquals("comment2", inst.getItemComment2());
		Assert.assertEquals("type", inst.getItemType());
		Assert.assertEquals("name", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("", inst.getItemComment1());
		Assert.assertEquals("", inst.getItemComment2());
		Assert.assertEquals("type", inst.getItemType());
		Assert.assertEquals("name", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("comment 1 test", inst.getItemComment1());
		Assert.assertEquals("comment2 x", inst.getItemComment2());
		Assert.assertEquals("type", inst.getItemType());
		Assert.assertEquals("", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("comment 1 test", inst.getItemComment1());
		Assert.assertEquals("", inst.getItemComment2());
		Assert.assertEquals("type", inst.getItemType());
		Assert.assertEquals("", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("", inst.getItemComment1());
		Assert.assertEquals("comment2", inst.getItemComment2());
		Assert.assertEquals("type", inst.getItemType());
		Assert.assertEquals("", inst.getItemName());
		Assert.assertEquals("data  1 data  2 data  3 data  4 data  5 data line 2", inst.getItemData());
		inst.next();

		Assert.assertEquals("C1", inst.getItemComment1());
		Assert.assertEquals("C2", inst.getItemComment2());
		Assert.assertEquals("T", inst.getItemType());
		Assert.assertEquals("N", inst.getItemName());
		Assert.assertEquals("D", inst.getItemData());
		
		Assert.assertEquals(false, inst.next());
		try {
			inst.getItemName();
			fail("must be out of bounds");
		} catch (IndexOutOfBoundsException e) {
			// Ignore
		}
	}

	@Test
	public void testParserPerf() {
		String data = "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n"
				+ "/* comment 1 test  */ type name : data  ww // comment2\r\n";

		TypeLineParserOld inst = new TypeLineParserOld(data);
		while (inst.next()) {
			String t="/* " + inst.getItemComment1() + " */ " + inst.getItemType() + " " + inst.getItemName()
					+ " : " + inst.getItemData() + " // " + inst.getItemComment2();
			Assert.assertEquals("/* comment 1 test */ type name : data  ww // comment2", t);
		}
		// System.out.println(inst);
		// System.out.println(inst.getItemCount());

	}
}
