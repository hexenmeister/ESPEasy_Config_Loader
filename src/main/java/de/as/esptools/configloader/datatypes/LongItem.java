package de.as.esptools.configloader.datatypes;

public class LongItem extends NumericItem {

	public static final String NAME = "long";

	/**
	 * Datentyp-Länge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 4;

	protected LongItem(boolean signed) {
		super(NAME, BYTES_PER_ITEM, signed);
	}

	LongItem(byte[] data, int offset, int bytesPerItem, boolean singned) {
		super(NAME, data, offset, BYTES_PER_ITEM, singned);
	}

}
