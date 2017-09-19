package de.as.esptools.configloader.datatypes;

public class LongItem extends NumericItem {

	/**
	 * Datentyp-Länge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 4;

	protected LongItem(boolean signed) {
		super(BYTES_PER_ITEM, signed);
	}

	LongItem(byte[] data, int offset, int bytesPerItem, boolean singned) {
		super(data, offset, BYTES_PER_ITEM, singned);
	}

}
