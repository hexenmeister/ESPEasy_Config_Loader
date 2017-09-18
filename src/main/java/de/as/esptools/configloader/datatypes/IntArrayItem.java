package de.as.esptools.configloader.datatypes;

public class IntArrayItem extends NumericArrayItem {

	/**
	 * Datentyp-Länge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 2;
	
	protected IntArrayItem(int length, boolean signed) {
		super(length, BYTES_PER_ITEM, signed);
	}

}
