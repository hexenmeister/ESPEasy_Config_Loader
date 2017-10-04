package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.ArrayDataItem;
import de.as.esptools.configloader.datatypes.BooleanArrayItem;
import de.as.esptools.configloader.datatypes.BooleanItem;
import de.as.esptools.configloader.datatypes.ByteArrayItem;
import de.as.esptools.configloader.datatypes.ByteItem;
import de.as.esptools.configloader.datatypes.CharArrayItem;
import de.as.esptools.configloader.datatypes.CharItem;
import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.DataItemCreationException;
import de.as.esptools.configloader.datatypes.DataStructure;
import de.as.esptools.configloader.datatypes.FloatArrayItem;
import de.as.esptools.configloader.datatypes.FloatItem;
import de.as.esptools.configloader.datatypes.Int16Item;
import de.as.esptools.configloader.datatypes.Int8Item;
import de.as.esptools.configloader.datatypes.IntArrayItem;
import de.as.esptools.configloader.datatypes.IntItem;
import de.as.esptools.configloader.datatypes.LongArrayItem;
import de.as.esptools.configloader.datatypes.LongItem;

public class DataStructureTest {

	private DataStructure createSmallStruct() {
		DataStructure struct = new DataStructure("TEST_STRUCT");
		struct.addItem(new BooleanItem());

		return struct;
	}

	private DataStructure createBigStruct() {
		DataStructure struct = new DataStructure("TEST_STRUCT");
		struct.addItem(new BooleanItem());
		struct.addItem(new ByteItem());
		struct.addItem(new CharItem());
		struct.addItem(new FloatItem());
		struct.addItem(new Int16Item(true));
		struct.addItem(new Int16Item(false));
		struct.addItem(new Int8Item(true));
		struct.addItem(new Int8Item(false));
		struct.addItem(new IntItem(true));
		struct.addItem(new IntItem(false));
		struct.addItem(new LongItem(true));
		struct.addItem(new LongItem(false));

		return struct;
	}

	private DataStructure createArrayStruct() {
		DataStructure struct = new DataStructure("TEST_STRUCT");
		struct.addItem(new BooleanItem());
		struct.addItem(new BooleanArrayItem(2));
		struct.addItem(new ByteItem());
		struct.addItem(new ByteArrayItem(1));
		struct.addItem(new CharItem());
		struct.addItem(new CharArrayItem(1));
		struct.addItem(new CharArrayItem(2));
		struct.addItem(new CharArrayItem(2));
		struct.addItem(new FloatItem());
		struct.addItem(new FloatArrayItem(2));
		struct.addItem(new IntItem(true));
		struct.addItem(new IntArrayItem(2, true));
		struct.addItem(new IntItem(false));
		struct.addItem(new IntArrayItem(2, false));
		struct.addItem(new LongItem(true));
		struct.addItem(new LongArrayItem(2, true));
		struct.addItem(new LongItem(false));
		struct.addItem(new LongArrayItem(2, false));

		return struct;
	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testAddItemGetArrayLength() throws DataItemCreationException {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals(1, struct.getArrayLength());
		struct.addItem(new Int8Item(true));
		Assert.assertEquals(2, struct.getArrayLength());
		struct.fillUp(100);
		Assert.assertEquals(3, struct.getArrayLength());
	}

	@Test
	public void testGetItems() {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals(1, struct.getItems().size());
		struct.addItem(new Int8Item(true));
		Assert.assertEquals(2, struct.getItems().size());
	}

	@Test
	public void testGetItemCount() {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals(1, struct.getItemCount());
		struct.addItem(new Int8Item(true));
		Assert.assertEquals(2, struct.getItemCount());
	}

	@Test
	public void testGetTypeName() {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals("TEST_STRUCT", struct.getTypeName());
	}

	@Test
	public void testGetBinLength() {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals(1, struct.getBinLength());
		struct.addItem(new Int8Item(true));
		struct.addItem(new Int16Item(true));
		Assert.assertEquals(4, struct.getBinLength());
	}

	@Test
	public void testImportBinOffset() throws DataImportException {
		DataStructure struct = this.createBigStruct();
		byte[] bytes = new byte[] { (byte) 0xFF, (byte) 0x01, (byte) 0xDE, (byte) 0xAD, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x88, (byte) 0x23, (byte) 0x55, (byte) 0x91,
				(byte) 0x02, (byte) 0x93, (byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
		struct.importBin(bytes, 1);
		byte[] bytes2 = struct.exportBin();
		byte[] bytesE = new byte[] { (byte) 0x01, (byte) 0xDE, (byte) 0xAD, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x88, (byte) 0x23, (byte) 0x55, (byte) 0x91, (byte) 0x02,
				(byte) 0x93, (byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00 };
		Assert.assertEquals(Arrays.toString(bytesE), Arrays.toString(bytes2));

		struct = this.createArrayStruct();
		bytes = new byte[] { (byte) 0xFF, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0xDE, (byte) 0xDE, (byte) 0xAD,
				(byte) 0xAE, (byte) 0xAF, (byte) 0xB0, (byte) 0xAD, (byte) 0xAD, (byte) 0x3F, (byte) 0x81, (byte) 0x47,
				(byte) 0xAE, (byte) 0x3F, (byte) 0x81, (byte) 0x47, (byte) 0xAE, (byte) 0x3F, (byte) 0x81, (byte) 0x47,
				(byte) 0xAE, (byte) 0x02, (byte) 0x93, (byte) 0x02, (byte) 0x93, (byte) 0x02, (byte) 0x93, (byte) 0x94,
				(byte) 0x00, (byte) 0x94, (byte) 0x00, (byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		struct.importBin(bytes, 1);
		bytes2 = struct.exportBin();
		bytesE = new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0xDE, (byte) 0xDE, (byte) 0xAD, (byte) 0xAE,
				(byte) 0xAF, (byte) 0xB0, (byte) 0xAD, (byte) 0xAD, (byte) 0x3F, (byte) 0x81, (byte) 0x47, (byte) 0xAE,
				(byte) 0x3F, (byte) 0x81, (byte) 0x47, (byte) 0xAE, (byte) 0x3F, (byte) 0x81, (byte) 0x47, (byte) 0xAE,
				(byte) 0x02, (byte) 0x93, (byte) 0x02, (byte) 0x93, (byte) 0x02, (byte) 0x93, (byte) 0x94, (byte) 0x00,
				(byte) 0x94, (byte) 0x00, (byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
		Assert.assertEquals(Arrays.toString(bytesE), Arrays.toString(bytes2));

		try {
			bytes = new byte[] { (byte) 0xFF, (byte) 0x01, (byte) 0xDE, (byte) 0xAD, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x88, (byte) 0x23, (byte) 0x55,
					(byte) 0x91, (byte) 0x02, (byte) 0x93, (byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
			struct.importBin(bytes, 1);
			bytes2 = struct.exportBin();
			Assert.assertEquals(Arrays.toString(bytesE), Arrays.toString(bytes2));
			fail("too long");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			bytes = new byte[] { (byte) 0xFF, (byte) 0x01, (byte) 0xDE, (byte) 0xAD, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x88, (byte) 0x23, (byte) 0x55,
					(byte) 0x91, (byte) 0x02, (byte) 0x93, (byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
			struct.importBin(bytes, 1);
			fail("too short");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testExportImportBin() throws DataImportException, DataItemCreationException {
		DataStructure struct = this.createSmallStruct();
		byte[] bytes = new byte[] { (byte) 0x01 };
		struct.importBin(bytes);
		byte[] bytes2 = struct.exportBin();
		Assert.assertEquals(Arrays.toString(bytes), Arrays.toString(bytes2));

		struct = this.createBigStruct();
		bytes = new byte[] { (byte) 0x01, (byte) 0xDE, (byte) 0xAD, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x12, (byte) 0x34, (byte) 0x88, (byte) 0x23, (byte) 0x55, (byte) 0x91, (byte) 0x02, (byte) 0x93,
				(byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00 };
		struct.importBin(bytes);
		bytes2 = struct.exportBin();
		Assert.assertEquals(Arrays.toString(bytes), Arrays.toString(bytes2));

		struct = this.createBigStruct();
		struct.fillUp(26);
		struct.addItem(new ByteItem());
		bytes = new byte[] { (byte) 0x01, (byte) 0xDE, (byte) 0xAD, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x12, (byte) 0x34, (byte) 0x88, (byte) 0x23, (byte) 0x55, (byte) 0x91, (byte) 0x02, (byte) 0x93,
				(byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0xEE, (byte) 0xFF };
		struct.importBin(bytes);
		bytes2 = struct.exportBin();
		Assert.assertEquals(Arrays.toString(bytes), Arrays.toString(bytes2));

		try {
			struct = this.createBigStruct();
			bytes = new byte[] { (byte) 0x01, (byte) 0xDE, (byte) 0xAD, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x88, (byte) 0x23, (byte) 0x55, (byte) 0x91,
					(byte) 0x02, (byte) 0x93, (byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
			struct.importBin(bytes);
			fail("too short");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			struct = this.createBigStruct();
			bytes = new byte[] { (byte) 0x01, (byte) 0xDE, (byte) 0xAD, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x12, (byte) 0x34, (byte) 0x88, (byte) 0x23, (byte) 0x55, (byte) 0x91,
					(byte) 0x02, (byte) 0x93, (byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
			struct.importBin(bytes);
			fail("too long");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testExportImportHex() throws DataImportException, DataItemCreationException {
		DataStructure struct = this.createSmallStruct();
		String data = "01";
		struct.importHex(data);
		String export = struct.exportHex();
		Assert.assertEquals(data, export);

		struct = this.createBigStruct();
		data = "01 DE AD 00 00 00 00 12 34 88 23 55 91 02 93 94 00 00 00 00 00 00 00 00 00";
		struct.importHex(data);
		export = struct.exportHex();
		Assert.assertEquals(data, export);

		struct = this.createBigStruct();
		struct.fillUp(25);
		struct.addItem(new ByteItem());
		data = "01 DE AD 00 00 00 00 12 34 88 23 55 91 02 93 94 00 00 00 00 00 00 00 00 00 FF";
		struct.importHex(data);
		export = struct.exportHex();
		Assert.assertEquals(data, export);

		struct = this.createBigStruct();
		struct.fillUp(26);
		struct.addItem(new ByteItem());
		data = "01 DE AD 00 00 00 00 12 34 88 23 55 91 02 93 94 00 00 00 00 00 00 00 00 00 EE FF";
		struct.importHex(data);
		export = struct.exportHex();
		Assert.assertEquals(data, export);

		struct = this.createBigStruct();
		struct.fillUp(27);
		struct.addItem(new ByteItem());
		data = "01 DE AD 00 00 00 00 12 34 88 23 55 91 02 93 94 00 00 00 00 00 00 00 00 00 EE EF FF";
		struct.importHex(data);
		export = struct.exportHex();
		Assert.assertEquals(data, export);

		try {
			struct = this.createBigStruct();
			data = "01 DE AD 00 00 00 00 12 34 88 23 55 91 02 93 94 00 00 00 00 00 00 00 00";
			struct.importHex(data);
			fail("too short");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			struct = this.createBigStruct();
			data = "01 DE AD 00 00 00 00 12 34 88 23 55 91 02 93 94 00 00 00 00 00";
			struct.importHex(data);
			fail("too short");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			struct = this.createBigStruct();
			data = "01 DE AD 00 00 00 00 12 34 88 23 55 91 02 93 94 00 00 00 00 00 00 00 00 00 FF";
			struct.importHex(data);
			fail("too long");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testExportNameAndTypeAndDataString() throws DataImportException, DataItemCreationException {
		DataStructure struct = this.createBigStruct();
		String data = "boolean bool1: 01 " + "byte byte1: DE " + "char char1:H " + "float float1: 1.01 "
				+ "int16 int1: 13330 " + "uint16 int2: 9096 " + "int8 int3: 85 " + "uint8 int4: 145 "
				+ "int int5: -27902 " + "uint int6: 148 " + "long long1: -2147483648 " + "ulong long2: 4294967295";
		struct.importTypeAndDataString(data);
		String export = struct.exportTypeAndDataString(false);
		export = export.replaceAll("\n+", "|");
		export = export.replaceAll("\r+", "");
		export = export.replaceAll("\\s+", " ");
		// System.out.println(export);
		Assert.assertEquals("boolean bool1 : 1|byte byte1 : DE|char char1 : H|"
				+ "float float1 : 1.01|int16 int1 : 13330|uint16 int2 : 9096|"
				+ "int8 int3 : 85|uint8 int4 : 145|int int5 : -27902|"
				+ "uint int6 : 148|long long1 : -2147483648|ulong long2 : 4294967295", export);

		struct = this.createBigStruct();
		struct.fillUp(26);
		struct.addItem(new ByteItem());
		data = "boolean bool1: 01 " + "byte byte1: DE " + "char char1:H " + "float float1: 1.01 " + "int16 int1: 13330 "
				+ "uint16 int2: 9096 " + "int8 int3: 85 " + "uint8 int4: 145 " + "int int5: -27902 " + "uint int6: 148 "
				+ "long long1: -2147483648 " + "ulong long2: 4294967295 " + "fillup : 100 " + "byte byte2: FF";
		struct.importTypeAndDataString(data);
		export = struct.exportTypeAndDataString(false);
		export = export.replaceAll("\n+", "|");
		export = export.replaceAll("\r+", "");
		export = export.replaceAll("\\s+", " ");
		// System.out.println(export);
		Assert.assertEquals(
				"boolean bool1 : 1|byte byte1 : DE|char char1 : H|"
						+ "float float1 : 1.01|int16 int1 : 13330|uint16 int2 : 9096|"
						+ "int8 int3 : 85|uint8 int4 : 145|int int5 : -27902|"
						+ "uint int6 : 148|long long1 : -2147483648|ulong long2 : 4294967295|fillup : 26|byte byte2 : FF",
				export);

		struct = this.createBigStruct();
		struct.fillUp(26);
		struct.addItem(new ByteItem());
		data = "boolean bool1: 01 " + "byte byte1: DE " + "char char1:H " + "float float1: 1.01 " + "int16 int1: 13330 "
				+ "uint16 int2: 9096 " + "int8 int3: 85 " + "uint8 int4: 145 " + "int int5: -27902 " + "uint int6: 148 "
				+ "long long1: -2147483648 " + "ulong long2: 4294967295 " + "fillup fill1 : 100 " + "byte byte2: FF";
		struct.importTypeAndDataString(data);
		export = struct.exportTypeAndDataString(false);
		export = export.replaceAll("\n+", "|");
		export = export.replaceAll("\r+", "");
		export = export.replaceAll("\\s+", " ");
		// System.out.println(export);
		Assert.assertEquals(
				"boolean bool1 : 1|byte byte1 : DE|char char1 : H|"
						+ "float float1 : 1.01|int16 int1 : 13330|uint16 int2 : 9096|"
						+ "int8 int3 : 85|uint8 int4 : 145|int int5 : -27902|"
						+ "uint int6 : 148|long long1 : -2147483648|ulong long2 : 4294967295|fillup : 26|byte byte2 : FF",
				export);

		// TODO: Arrays
	}

	@Test
	public void testImportTypeAndDataString() throws DataImportException, DataItemCreationException {
		DataStructure struct = this.createSmallStruct();
		String data = /* boolean */ "boolean: 01";
		struct.importTypeAndDataString(data);
		String export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1", export);

		struct = this.createSmallStruct();
		data = /* boolean */ "boolean test: 01";
		struct.importTypeAndDataString(data);
		export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1", export);

		struct = this.createBigStruct();
		data = "boolean : 01 " + "byte : DE " + "char :H " + "float : 1.01 " + "int16 : 13330 " + "uint16 : 9096 "
				+ "int8 : 85 " + "uint8 : 145 " + "int : -27902 " + "uint : 148 " + "long : -2147483648 "
				+ "ulong : 4294967295";
		struct.importTypeAndDataString(data);
		export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1|DE|H|1.01|13330|9096|85|145|-27902|148|-2147483648|4294967295", export);

		struct = this.createBigStruct();
		data = "boolean bool1: 01 " + "byte byte1: DE " + "char char1:H " + "float float1: 1.01 " + "int16 int1: 13330 "
				+ "uint16 int2: 9096 " + "int8 int3: 85 " + "uint8 int4: 145 " + "int int5: -27902 " + "uint int6: 148 "
				+ "long long1: -2147483648 " + "ulong long2: 4294967295";
		struct.importTypeAndDataString(data);
		export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1|DE|H|1.01|13330|9096|85|145|-27902|148|-2147483648|4294967295", export);

		struct = this.createBigStruct();
		struct.fillUp(100);
		struct.addItem(new ByteItem());
		data = "boolean bool1: 01 " + "byte byte1: DE " + "char char1:H " + "float float1: 1.01 " + "int16 int1: 13330 "
				+ "uint16 int2: 9096 " + "int8 int3: 85 " + "uint8 int4: 145 " + "int int5: -27902 " + "uint int6: 148 "
				+ "long long1: -2147483648 " + "ulong long2: 4294967295 " + "fillup: 100 " + "byte byte2: FF";
		struct.importTypeAndDataString(data);
		export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1|DE|H|1.01|13330|9096|85|145|-27902|148|-2147483648|4294967295|100|FF", export);

		// TODO: Arrays

		fail("todo");
	}

	@Test
	public void testCreateFiller() throws DataItemCreationException {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals(1, struct.getBinLength());
		struct.fillUp(100);
		Assert.assertEquals(100, struct.getBinLength());
		struct.addItem(new ByteItem());
		Assert.assertEquals(101, struct.getBinLength());

		struct = this.createBigStruct();
		Assert.assertEquals(25, struct.getBinLength());
		struct.fillUp(100);
		Assert.assertEquals(100, struct.getBinLength());
		struct.addItem(new ByteItem());
		Assert.assertEquals(101, struct.getBinLength());

		struct = this.createBigStruct();
		Assert.assertEquals(25, struct.getBinLength());
		struct.fillUp(25);
		Assert.assertEquals(25, struct.getBinLength());
		struct.addItem(new ByteItem());
		Assert.assertEquals(26, struct.getBinLength());

		try {
			struct = this.createBigStruct();
			Assert.assertEquals(25, struct.getBinLength());
			struct.fillUp(24);
			fail("filler should nob be created");
		} catch (DataItemCreationException e) {
			// NOP
		}

	}

	@Test
	public void testImportDataString() throws DataImportException, DataItemCreationException {
		DataStructure struct = this.createSmallStruct();
		String data = /* boolean */ "01";
		struct.importDataString(data);
		String export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1", export);

		struct = this.createBigStruct();
		data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "H " + /* float */ "1.01 "
				+ /* int16 signed */ "13330 " + /* int16 unsigned */ "9096 "
				+ /* int8 signed */ "85 " + /* int8 unsigned */ "145 "
				+ /* int signed */ "-27902 " + /* int unsigned */ "148 "
				+ /* long signed */ "-2147483648 " + /* int unsigned */ "4294967295";
		struct.importDataString(data);
		export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1|DE|H|1.01|13330|9096|85|145|-27902|148|-2147483648|4294967295", export);

		struct = this.createBigStruct();
		data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "\"H\" " + /* float */ "1.01 "
				+ /* int16 signed */ "13330 " + /* int16 unsigned */ "9096 "
				+ /* int8 signed */ "85 " + /* int8 unsigned */ "145 "
				+ /* int signed */ "-27902 " + /* int unsigned */ "148 "
				+ /* long signed */ "-2147483648 " + /* int unsigned */ "4294967295";
		struct.importDataString(data);
		export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1|DE|H|1.01|13330|9096|85|145|-27902|148|-2147483648|4294967295", export);

		struct = this.createBigStruct();
		data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "\" \" " + /* float */ "1.01 "
				+ /* int16 signed */ "13330 " + /* int16 unsigned */ "9096 "
				+ /* int8 signed */ "85 " + /* int8 unsigned */ "145 "
				+ /* int signed */ "-27902 " + /* int unsigned */ "148 "
				+ /* long signed */ "-2147483648 " + /* int unsigned */ "4294967295";
		struct.importDataString(data);
		export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1|DE|\"|\"|1.01|13330|9096|85|145|-27902|148|-2147483648|4294967295", export);

		struct = this.createBigStruct();
		struct.fillUp(100);
		struct.addItem(new ByteItem());
		data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "\" \" " + /* float */ "1.01 "
				+ /* int16 signed */ "13330 " + /* int16 unsigned */ "9096 "
				+ /* int8 signed */ "85 " + /* int8 unsigned */ "145 "
				+ /* int signed */ "-27902 " + /* int unsigned */ "148 "
				+ /* long signed */ "-2147483648 " + /* int unsigned */ "4294967295 " + /* filler */ "100 "
				+ /* byte */" FF";
		struct.importDataString(data);
		export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1|DE|\"|\"|1.01|13330|9096|85|145|-27902|148|-2147483648|4294967295|100|FF", export);

		try {
			struct = this.createBigStruct();
			data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "\" \" " + /* float */ "1.01 "
					+ /* int16 signed */ "13330 " + /* int16 unsigned */ "9096 "
					+ /* int8 signed */ "85 " + /* int8 unsigned */ "145 "
					+ /* int signed */ "-27902 " + /* int unsigned */ "148 "
					+ /* long signed */ "-2147483648 ";
			struct.importDataString(data);
			fail("too short");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			struct = this.createBigStruct();
			data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "\" \" " + /* float */ "1.01 "
					+ /* int16 signed */ "13330 " + /* int16 unsigned */ "9096 "
					+ /* int8 signed */ "85 " + /* int8 unsigned */ "145 "
					+ /* int signed */ "-27902 " + /* int unsigned */ "148 "
					+ /* long signed */ "-2147483648 " + /* int unsigned */ "4294967295" + /* fill */ "X";
			struct.importDataString(data);
			fail("too long");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testExportDataString() throws DataImportException, DataItemCreationException {
		DataStructure struct = this.createSmallStruct();
		String data = /* boolean */ "01";
		struct.importHex(data);
		String export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1", export);

		struct = this.createBigStruct();
		data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "48 " + /* float */ "3F 81 47 AE "
				+ /* int16 signed */ "12 34 " + /* int16 unsigned */ "88 23 "
				+ /* int8 signed */ "55 " + /* int8 unsigned */ "91 "
				+ /* int signed */ "02 93 " + /* int unsigned */ "94 00 "
				+ /* long signed */ "00 00 00 80 " + /* long unsigned */ "FF FF FF FF";
		struct.importHex(data);
		export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1|DE|H|1.01|13330|9096|85|145|-27902|148|-2147483648|4294967295", export);

		struct = this.createBigStruct();
		struct.fillUp(26);
		struct.addItem(new ByteItem());
		data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "48 " + /* float */ "3F 81 47 AE "
				+ /* int16 signed */ "12 34 " + /* int16 unsigned */ "88 23 "
				+ /* int8 signed */ "55 " + /* int8 unsigned */ "91 "
				+ /* int signed */ "02 93 " + /* int unsigned */ "94 00 "
				+ /* long signed */ "00 00 00 80 " + /* long unsigned */ "FF FF FF FF" + /* filler */" EE"
				+ /* byte */" FF";
		struct.importHex(data);
		export = struct.exportDataString();
		export = export.replaceAll("\\s+", "|");
		Assert.assertEquals("1|DE|H|1.01|13330|9096|85|145|-27902|148|-2147483648|4294967295|26|FF", export);

		try {
			struct = this.createBigStruct();
			data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "48 " + /* float */ "3F 81 47 AE "
					+ /* int16 signed */ "12 34 " + /* int16 unsigned */ "88 23 "
					+ /* int8 signed */ "55 " + /* int8 unsigned */ "91 "
					+ /* int signed */ "02 93 " + /* int unsigned */ "94 00 "
					+ /* long signed */ "00 00 00 80 ";
			struct.importHex(data);
			fail("too short");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			struct = this.createBigStruct();
			data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "48 " + /* float */ "3F 81 47 AE "
					+ /* int16 signed */ "12 34 " + /* int16 unsigned */ "88 23 "
					+ /* int8 signed */ "55 " + /* int8 unsigned */ "91 "
					+ /* int signed */ "02 93 " + /* int unsigned */ "94 00 "
					+ /* long signed */ "00 00 00 80 " + /* corrupt */ "FF FF FF";
			struct.importHex(data);
			fail("too short (element)");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			struct = this.createBigStruct();
			data = /* boolean */ "01 " + /* byte */ "DE " + /* char */ "48 " + /* float */ "3F 81 47 AE "
					+ /* int16 signed */ "12 34 " + /* int16 unsigned */ "88 23 "
					+ /* int8 signed */ "55 " + /* int8 unsigned */ "91 "
					+ /* int signed */ "02 93 " + /* int unsigned */ "94 00 "
					+ /* long signed */ "00 00 00 80 " + /* long unsigned */ "FF FF FF FF" + /* fill */ "FF";
			struct.importHex(data);
			fail("too long");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
	public void testExportTypeAndDataString() throws DataImportException, DataItemCreationException {
		DataStructure struct = this.createSmallStruct();
		String data = "01 ";
		struct.importHex(data);
		String export = struct.exportTypeAndDataString(false);
		export = export.replaceAll("\n+", "|");
		export = export.replaceAll("\r+", "");
		export = export.replaceAll("\\s+", "");
		// System.out.println(export);
		Assert.assertEquals("boolean:1", export);

		struct = this.createBigStruct();
		data = "01 " + "DE " + "48 " + "3F 81 47 AE " + "12 34 " + "88 23 " + "55 " + "91 " + "02 93 " + "94 00 "
				+ "00 00 00 80 " + "FF FF FF FF";
		struct.importHex(data);
		export = struct.exportTypeAndDataString(false);
		export = export.replaceAll("\n+", "|");
		export = export.replaceAll("\r+", "");
		export = export.replaceAll("\\s+", "");
		// System.out.println(export);
		Assert.assertEquals(
				"boolean:1|byte:DE|char:H|float:1.01|int16:13330|uint16:9096|int8:85|uint8:145|int:-27902|uint:148|long:-2147483648|ulong:4294967295",
				export);

		struct = this.createBigStruct();
		struct.fillUp(26);
		struct.addItem(new ByteItem());
		data = "01 " + "DE " + "48 " + "3F 81 47 AE " + "12 34 " + "88 23 " + "55 " + "91 " + "02 93 " + "94 00 "
				+ "00 00 00 80 " + "FF FF FF FF " + "EE " + "FF";
		struct.importHex(data);
		export = struct.exportTypeAndDataString(false);
		export = export.replaceAll("\n+", "|");
		export = export.replaceAll("\r+", "");
		export = export.replaceAll("\\s+", "");
		// System.out.println(export);
		Assert.assertEquals(
				"boolean:1|byte:DE|char:H|float:1.01|int16:13330|uint16:9096|int8:85|uint8:145|int:-27902|uint:148|long:-2147483648|ulong:4294967295|fillup:26|byte:FF",
				export);

		try {
			struct = this.createBigStruct();
			data = "01 " + "DE " + "48 " + "3F 81 47 AE " + "12 34 " + "88 23 " + "55 " + "91 " + "02 93 " + "94 00 "
					+ "00 00 00 80 ";
			struct.importHex(data);
			fail("too short");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			struct = this.createBigStruct();
			data = "01 " + "DE " + "48 " + "3F 81 47 AE " + "12 34 " + "88 23 " + "55 " + "91 " + "02 93 " + "94 00 "
					+ "00 00 00 80 " + "FF FF FF";
			struct.importHex(data);
			fail("too short (element)");
		} catch (DataImportException e) {
			// NOP
		}

		try {
			struct = this.createBigStruct();
			data = "01 " + "DE " + "48 " + "3F 81 47 AE " + "12 34 " + "88 23 " + "55 " + "91 " + "02 93 " + "94 00 "
					+ "00 00 00 80 " + "FF FF FF FF" + " DD";
			struct.importHex(data);
			fail("too long");
		} catch (DataImportException e) {
			// NOP
		}
	}

	@Test
    public void testClone() throws CloneNotSupportedException, DataImportException, DataItemCreationException {
	    DataStructure struct = this.createBigStruct();
        String data = "boolean bool1: 01 " + "byte byte1: DE " + "char char1:H " + "float float1: 1.01 "
                + "int16 int1: 13330 " + "uint16 int2: 9096 " + "int8 int3: 85 " + "uint8 int4: 145 "
                + "int int5: -27902 " + "uint int6: 148 " + "long long1: -2147483648 " + "ulong long2: 4294967295";
        struct.importTypeAndDataString(data);
        DataStructure instClone = (DataStructure) struct.clone();
        String export = instClone.exportTypeAndDataString(false);
        export = export.replaceAll("\n+", "|");
        export = export.replaceAll("\r+", "");
        export = export.replaceAll("\\s+", " ");
        Assert.assertEquals("boolean bool1 : 1|byte byte1 : DE|char char1 : H|"
                + "float float1 : 1.01|int16 int1 : 13330|uint16 int2 : 9096|"
                + "int8 int3 : 85|uint8 int4 : 145|int int5 : -27902|"
                + "uint int6 : 148|long long1 : -2147483648|ulong long2 : 4294967295", export);
	    
        struct = this.createBigStruct();
        struct.fillUp(26);
        struct.addItem(new ByteItem());
        data = "boolean bool1: 01 " + "byte byte1: DE " + "char char1:H " + "float float1: 1.01 " + "int16 int1: 13330 "
                + "uint16 int2: 9096 " + "int8 int3: 85 " + "uint8 int4: 145 " + "int int5: -27902 " + "uint int6: 148 "
                + "long long1: -2147483648 " + "ulong long2: 4294967295 " + "fillup fill1 : 100 " + "byte byte2: FF";
        struct.importTypeAndDataString(data);
        instClone = (DataStructure) struct.clone();
        export = instClone.exportTypeAndDataString(false);
        export = export.replaceAll("\n+", "|");
        export = export.replaceAll("\r+", "");
        export = export.replaceAll("\\s+", " ");
        Assert.assertEquals(
                "boolean bool1 : 1|byte byte1 : DE|char char1 : H|"
                        + "float float1 : 1.01|int16 int1 : 13330|uint16 int2 : 9096|"
                        + "int8 int3 : 85|uint8 int4 : 145|int int5 : -27902|"
                        + "uint int6 : 148|long long1 : -2147483648|ulong long2 : 4294967295|fillup : 26|byte byte2 : FF",
                export);
        
        // TODO Arrays
    }
	
}
