package de.as.esptools.configloader.datatypes;

public class LongArrayItem extends NumericArrayItem {

	/**
	 * Datentyp-Länge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 4;
	
	protected LongArrayItem(int length, boolean signed) {
		super(length, BYTES_PER_ITEM, signed);
	}

}
