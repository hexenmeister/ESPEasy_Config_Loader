package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.BooleanItem;
import de.as.esptools.configloader.datatypes.ByteItem;
import de.as.esptools.configloader.datatypes.CharItem;
import de.as.esptools.configloader.datatypes.DataImportException;
import de.as.esptools.configloader.datatypes.DataStructure;
import de.as.esptools.configloader.datatypes.FloatItem;
import de.as.esptools.configloader.datatypes.Int16Item;
import de.as.esptools.configloader.datatypes.Int8Item;
import de.as.esptools.configloader.datatypes.IntItem;
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

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testAddItemGetArrayLength() {
		DataStructure struct = this.createSmallStruct();
		Assert.assertEquals(1, struct.getArrayLength());
		struct.addItem(new Int8Item(true));
		Assert.assertEquals(2, struct.getArrayLength());
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
	public void testExportImportBin() throws DataImportException {
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
	public void testExportImportHex() throws DataImportException {
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
	public void testImportDataString() throws DataImportException {
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
	public void testExportDataString() throws DataImportException {
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
	public void testExportTypeAndDataString() throws DataImportException {
		DataStructure struct = this.createSmallStruct();
		String data = "01 ";
		struct.importHex(data);

		String export = struct.exportTypeAndDataString(false);
		export = export.replaceAll("\n+", "|");
		export = export.replaceAll("\r+", "");
		export = export.replaceAll("\\s+", "");
		System.out.println(export);
		Assert.assertEquals("boolean:1", export);

		struct = this.createBigStruct();
		data = "01 " + "DE " + "48 " + "3F 81 47 AE " + "12 34 " + "88 23 " + "55 " + "91 " + "02 93 " + "94 00 "
				+ "00 00 00 80 " + "FF FF FF FF";
		struct.importHex(data);

		export = struct.exportTypeAndDataString(false);
		export = export.replaceAll("\n+", "|");
		export = export.replaceAll("\r+", "");
		export = export.replaceAll("\\s+", "");
		System.out.println(export);
		Assert.assertEquals(
				"boolean:1|byte:DE|char:H|float:1.01|int16:13330|int16:9096|int8:85|int8:145|int:-27902|int:148|long:-2147483648|long:4294967295",
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

}
