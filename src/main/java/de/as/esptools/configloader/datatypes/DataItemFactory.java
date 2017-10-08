package de.as.esptools.configloader.datatypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataItemFactory {

	static final class Holder {
		static final DataItemFactory INSTANCE = new DataItemFactory();
	}

	private DataItemFactory() {
	}

	// suchen nach Muster: xxx[num]
	private static final Pattern TYPE_PATTERN = Pattern.compile("(\\w+)\\s*(\\[([0-9]{1,})\\])?");

	public static final DataItemFactory getInstance() {
		return Holder.INSTANCE;
	}

	public IDataType createDataItem(String typeDef) throws DataItemCreationException {
		if (typeDef != null) {
			typeDef = typeDef.trim().toLowerCase();
			if (typeDef.startsWith(":")) {
				// gehört zu dem vorheigen ("Zeilenumbruch")
				return null;
			}

			Matcher matcher = TYPE_PATTERN.matcher(typeDef);
			String typeName = null;
			String arrayCardinality = null;
			boolean array = typeDef.matches(".*\\[.*\\].*");
			if (matcher.find()) {
				typeName = matcher.group(1);
				arrayCardinality = matcher.group(3);
			}

			if (array && arrayCardinality == null) {
				throw new DataItemCreationException("wrong syntax: invalid array length");
			}

			int cardinality = 0;
			if (array) {
				cardinality = Integer.parseInt(arrayCardinality);
			}

			if (array && cardinality < 1) {
				throw new DataItemCreationException("array length may not be null or negative");
			}

			if (BooleanItem.NAME.equals(typeName)) {
				return array ? (new BooleanArrayItem(cardinality)) : (new BooleanItem());
			}
			if (ByteItem.NAME.equals(typeName)) {
				return array ? (new ByteArrayItem(cardinality)) : (new ByteItem());
			}
			if (CharItem.NAME.equals(typeName)) {
				return array ? (new CharArrayItem(cardinality)) : (new CharItem());
			}
			if (FloatItem.NAME.equals(typeName)) {
				return array ? (new FloatArrayItem(cardinality)) : (new FloatItem());
			}
			if (IntItem.NAME.equals(typeName)) {
				return array ? (new IntArrayItem(cardinality, true)) : (new IntItem(true));
			}
			if (Int8Item.NAME.equals(typeName)) {
				return array ? (new Int8ArrayItem(cardinality, true)) : (new Int8Item(true));
			}
			if (Int16Item.NAME.equals(typeName)) {
				return array ? (new Int16ArrayItem(cardinality, true)) : (new Int16Item(true));
			}
			if (LongItem.NAME.equals(typeName)) {
				return array ? (new LongArrayItem(cardinality, true)) : (new LongItem(true));
			}
			if (("u" + IntItem.NAME).equals(typeName)) {
				return array ? (new IntArrayItem(cardinality, true)) : (new IntItem(false));
			}
			if (("u" + Int8Item.NAME).equals(typeName)) {
				return array ? (new Int8ArrayItem(cardinality, true)) : (new Int8Item(false));
			}
			if (("u" + Int16Item.NAME).equals(typeName)) {
				return array ? (new Int16ArrayItem(cardinality, true)) : (new Int16Item(false));
			}
			if (("u" + LongItem.NAME).equals(typeName)) {
				return array ? (new LongArrayItem(cardinality, true)) : (new LongItem(false));
			}
			throw new DataItemCreationException(" unknown type: " + typeDef);
		}

		throw new DataItemCreationException("type name may not be null");
	}
}
