package de.as.esptools.configloader.datatypes;

public class Int8ArrayItem extends NumericArrayItem {

	
	/**
	 * Datentyp-Länge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 1;
	
	protected Int8ArrayItem(int length, boolean signed) {
		super(length, BYTES_PER_ITEM, signed);
	}

}
