package de.as.esptools.configloader;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import de.as.esptools.configloader.datatypes.BooleanArrayItem;
import de.as.esptools.configloader.datatypes.BooleanItem;
import de.as.esptools.configloader.datatypes.ByteArrayItem;
import de.as.esptools.configloader.datatypes.ByteItem;
import de.as.esptools.configloader.datatypes.CharArrayItem;
import de.as.esptools.configloader.datatypes.CharItem;
import de.as.esptools.configloader.datatypes.DataItemCreationException;
import de.as.esptools.configloader.datatypes.DataItemFactory;
import de.as.esptools.configloader.datatypes.FloatArrayItem;
import de.as.esptools.configloader.datatypes.FloatItem;
import de.as.esptools.configloader.datatypes.IArrayDataType;
import de.as.esptools.configloader.datatypes.IDataType;
import de.as.esptools.configloader.datatypes.Int16ArrayItem;
import de.as.esptools.configloader.datatypes.Int16Item;
import de.as.esptools.configloader.datatypes.Int8ArrayItem;
import de.as.esptools.configloader.datatypes.Int8Item;
import de.as.esptools.configloader.datatypes.IntArrayItem;
import de.as.esptools.configloader.datatypes.IntItem;
import de.as.esptools.configloader.datatypes.LongArrayItem;
import de.as.esptools.configloader.datatypes.LongItem;

public class DataItemFactoryTest {

	@Test
	public void testCreateBoolean() throws DataItemCreationException {
		testCreateType("boolean", BooleanItem.class, BooleanArrayItem.class);
	}

	@Test
	public void testCreateByte() throws DataItemCreationException {
		testCreateType("byte", ByteItem.class, ByteArrayItem.class);
	}

	@Test
	public void testCreateChar() throws DataItemCreationException {
		testCreateType("char", CharItem.class, CharArrayItem.class);
	}

	@Test
	public void testCreateFloat() throws DataItemCreationException {
		testCreateType("float", FloatItem.class, FloatArrayItem.class);
	}

	@Test
	public void testCreateLong() throws DataItemCreationException {
		testCreateType("long", LongItem.class, LongArrayItem.class);
	}

	@Test
	public void testCreateULong() throws DataItemCreationException {
		testCreateType("ulong", LongItem.class, LongArrayItem.class);
	}

	@Test
	public void testCreateInt() throws DataItemCreationException {
		testCreateType("int", IntItem.class, IntArrayItem.class);
	}

	@Test
	public void testCreateUInt() throws DataItemCreationException {
		testCreateType("uint", IntItem.class, IntArrayItem.class);
	}

	@Test
	public void testCreateInt8() throws DataItemCreationException {
		testCreateType("int8", Int8Item.class, Int8ArrayItem.class);
	}

	@Test
	public void testCreateUInt8() throws DataItemCreationException {
		testCreateType("uint8", Int8Item.class, Int8ArrayItem.class);
	}

	@Test
	public void testCreateInt16() throws DataItemCreationException {
		testCreateType("int16", Int16Item.class, Int16ArrayItem.class);
	}

	@Test
	public void testCreateUInt16() throws DataItemCreationException {
		testCreateType("uint16", Int16Item.class, Int16ArrayItem.class);
	}

	@Test
	public void testCreateUnknown() {
		try {
			testCreateType("unknown", null, null);
			fail("invalid type maynot be created");
		} catch (DataItemCreationException e) {
			// NOP
		}
	}

	private void testCreateType(String type, Class<? extends IDataType> itemClass,
			Class<? extends IArrayDataType> arrayClass) throws DataItemCreationException {

		testType(type + "", itemClass);
		testType(type + " ", itemClass);
		testType(" " + type + " ", itemClass);
		testType(type + "[1]", arrayClass, 1);
		testType(type + " [1]", arrayClass, 1);
		testType(type + " [1] ", arrayClass, 1);
		testType("  " + type + "  [1]  ", arrayClass, 1);
		testType(type + "[5]", arrayClass, 5);
		testType(type + "[255]", arrayClass, 255);

		try {
			testType(type + "[]", arrayClass, 5);
			fail("wrong type: should not by created");
		} catch (DataItemCreationException e) {
			// NOP
		}

		try {
			testType(type + "[0]", arrayClass, 5);
			fail("wrong type: should not by created");
		} catch (DataItemCreationException e) {
			// NOP
		}

		try {
			testType(type + "[-1]", arrayClass, 5);
			fail("wrong type: should not by created");
		} catch (DataItemCreationException e) {
			// NOP
		}

		try {
			testType(type + "[x]", arrayClass, 5);
			fail("wrong type: should not by created");
		} catch (DataItemCreationException e) {
			// NOP
		}

		try {
			testType(type + "[1x]", arrayClass, 5);
			fail("wrong type: should not by created");
		} catch (DataItemCreationException e) {
			// NOP
		}

		try {
			testType(type + "[1x1]", arrayClass, 5);
			fail("wrong type: should not by created");
		} catch (DataItemCreationException e) {
			// NOP
		}

		try {
			testType(type + "[x1]", arrayClass, 5);
			fail("wrong type: should not by created");
		} catch (DataItemCreationException e) {
			// NOP
		}

		try {
			testType(type + "[1.0]", arrayClass, 5);
			fail("wrong type: should not by created");
		} catch (DataItemCreationException e) {
			// NOP
		}
	}

	private void testType(String typeDef, Class<? extends IArrayDataType> clazz, int cardinality)
			throws DataItemCreationException {
		IArrayDataType type = (IArrayDataType) testType(typeDef, clazz);
		int fcardinality = type.getArrayLength();
		Assert.assertEquals("wrong array cardinality:", cardinality, fcardinality);
	}

	private IDataType testType(String typeDef, Class<? extends IDataType> clazz) throws DataItemCreationException {
		IDataType type = DataItemFactory.getInstance().createDataItem(typeDef);
		if (type == null) {
			fail("no type crated (null)");
		}
		if (!clazz.isAssignableFrom(type.getClass())) {
			fail("wrong type created. Expected: " + clazz.getName() + " but was found:" + type.getClass().getName());
		}
		return type;
	}

}
