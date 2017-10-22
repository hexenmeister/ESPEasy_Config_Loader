package de.as.esptools.configloader;

import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.util.TypeLineParser;

public class TypeLineParserTest {

	@Test
	public void testParseStruct() {
		String data = "struct SettingsStruct {                             \r\n"
				+ "  ulong         PID     :                               \r\n"
				+ "  int           Version :                               \r\n"
				+ "  int16         Build   :                               \r\n"
				+ "  byte[4]       IP      :                               \r\n"
				+ "  byte[4]       Gateway :                               \r\n"
				+ "  byte[4][5]    dummy   :                               \r\n"
				+ "  struct test {                                         \r\n"
				+ "    byte[4][5]    dummy2:                               \r\n"
				+ "  }                                                     \r\n"
				+ "  byte[4][5]    dummy3  :                               \r\n"
				+ "  occurs test 10 {                                      \r\n"
				+ "    byte[4][5]    dummyO1:                              \r\n"
				+ "    byte[4][5]    dummyO2:                              \r\n"
				+ "  }                                                     \r\n"
				+ "}                                                       ";

		TypeLineParser inst = new TypeLineParser(data);
		inst.next();
		Assert.assertEquals(9, inst.getChildren().size());
		Assert.assertEquals(1, inst.getChildren().get(6).getChildren().size());
		Assert.assertEquals(2, inst.getChildren().get(8).getChildren().size());

		System.out.println(inst.getCurrent());
		System.out.println(inst.getChildren().get(6));
		System.out.println(inst.getChildren().get(6).getChildren());
		System.out.println(inst.getChildren().get(8));
	}

	@Test
	public void testParserArray() {
		String data = "/* comment 1 test  */ type[4] name : data  ww // comment2\r\n"
				+ "  /* comment 1 test  */ type[4] name : data  ww \r\n"
				+ "                        type[4] name : data  ww // comment2\r\n"
				+ "                        type[4] name : data  ww\r\n"
				+ "  /* comment 1 test  */ type[4]      : data  ww // comment2 x\r\n"
				+ "  /* comment 1 test  */ type[4]      : data  ww\r\n"
				+ "                        type[4]      : data  1 // comment2  \r\n"
				+ "  /* comment 1 test  */           : data  2 // comment2 \r\n"
				+ "  /* comment 1 test  */           : data  3\r\n"
				+ "                                  : data  4 // comment2\r\n"
				+ "                                  : data  5        \r\n"
				+ "                                  : data line 2\r\n" + "/*C1*/T[1] N:D//C2";

		TypeLineParser inst = new TypeLineParser(data);

		// System.out.println(inst);

		inst.next();
		Assert.assertEquals("comment 1 test", inst.getItemComment1());
		Assert.assertEquals("comment2", inst.getItemComment2());
		Assert.assertEquals("type[4]", inst.getItemType());
		Assert.assertEquals("name", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("comment 1 test", inst.getItemComment1());
		Assert.assertEquals("", inst.getItemComment2());
		Assert.assertEquals("type[4]", inst.getItemType());
		Assert.assertEquals("name", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("", inst.getItemComment1());
		Assert.assertEquals("comment2", inst.getItemComment2());
		Assert.assertEquals("type[4]", inst.getItemType());
		Assert.assertEquals("name", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("", inst.getItemComment1());
		Assert.assertEquals("", inst.getItemComment2());
		Assert.assertEquals("type[4]", inst.getItemType());
		Assert.assertEquals("name", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("comment 1 test", inst.getItemComment1());
		Assert.assertEquals("comment2 x", inst.getItemComment2());
		Assert.assertEquals("type[4]", inst.getItemType());
		Assert.assertEquals("", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("comment 1 test", inst.getItemComment1());
		Assert.assertEquals("", inst.getItemComment2());
		Assert.assertEquals("type[4]", inst.getItemType());
		Assert.assertEquals("", inst.getItemName());
		Assert.assertEquals("data  ww", inst.getItemData());

		inst.next();
		Assert.assertEquals("", inst.getItemComment1());
		Assert.assertEquals("comment2", inst.getItemComment2());
		Assert.assertEquals("type[4]", inst.getItemType());
		Assert.assertEquals("", inst.getItemName());
		Assert.assertEquals("data  1 data  2 data  3 data  4 data  5 data line 2", inst.getItemData());

		inst.next();
		Assert.assertEquals("C1", inst.getItemComment1());
		Assert.assertEquals("C2", inst.getItemComment2());
		Assert.assertEquals("T[1]", inst.getItemType());
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

		TypeLineParser inst = new TypeLineParser(data);

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

		TypeLineParser inst = new TypeLineParser(data);
		while (inst.next()) {
			String t = "/* " + inst.getItemComment1() + " */ " + inst.getItemType() + " " + inst.getItemName() + " : "
					+ inst.getItemData() + " // " + inst.getItemComment2();
			Assert.assertEquals("/* comment 1 test */ type name : data  ww // comment2", t);
		}
		// System.out.println(inst);
		// System.out.println(inst.getItemCount());

	}
}
