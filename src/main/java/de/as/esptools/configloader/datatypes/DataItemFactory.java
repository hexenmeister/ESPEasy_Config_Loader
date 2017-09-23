package de.as.esptools.configloader.datatypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataItemFactory {

	public static class DataItemCreationException extends Exception {
		private static final long serialVersionUID = 8320133839039042413L;

		public DataItemCreationException(String msg) {
			super(msg);
		}

	}

	static final class Holder {
		static final DataItemFactory INSTANCE = new DataItemFactory();
	}

	private DataItemFactory() {
	}

	private static final Pattern pattern = Pattern.compile("(\\w+)\\s*(\\[([0-9]{1,})\\])?"); // suchen
																								// nach
																								// Muster:
																								// xxx[num]

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

			Matcher matcher = pattern.matcher(typeDef);
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

			if ("boolean".equals(typeName)) {
				return array ? (new BooleanArrayItem(cardinality)) : (new BooleanItem());
			}
			if ("byte".equals(typeName)) {
				return array ? (new ByteArrayItem(cardinality)) : (new ByteItem());
			}
			if ("char".equals(typeName)) {
				return array ? (new CharArrayItem(cardinality)) : (new CharItem());
			}
			if ("float".equals(typeName)) {
				return array ? (new FloatArrayItem(cardinality)) : (new FloatItem());
			}
			if ("int".equals(typeName)) {
				return array ? (new IntArrayItem(cardinality, true)) : (new IntItem(true));
			}
			if ("int8".equals(typeName)) {
				return array ? (new Int8ArrayItem(cardinality, true)) : (new Int8Item(true));
			}
			if ("int16".equals(typeName)) {
				return array ? (new Int16ArrayItem(cardinality, true)) : (new Int16Item(true));
			}
			if ("long".equals(typeName)) {
				return array ? (new LongArrayItem(cardinality, true)) : (new LongItem(true));
			}
			throw new DataItemCreationException(" unknown type: " + typeDef);
		}

		throw new DataItemCreationException("type name may not be null");
	}
}
