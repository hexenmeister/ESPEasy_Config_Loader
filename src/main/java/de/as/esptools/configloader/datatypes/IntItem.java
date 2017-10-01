package de.as.esptools.configloader.datatypes;

public class IntItem extends NumericIntegerItem {

	public static final String NAME = "int";

	/**
	 * Datentyp-Länge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 2;

	public IntItem(boolean signed) {
		super(NAME, BYTES_PER_ITEM, signed);
	}

	IntItem(byte[] data, int offset, int bytesPerItem, boolean singned) {
		super(NAME, data, offset, BYTES_PER_ITEM, singned);
	}

}
