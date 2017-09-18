package de.as.esptools.configloader.datatypes;

public class Int16ArrayItem extends NumericArrayItem {

	/**
	 * Datentyp-Länge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 2;
	
	protected Int16ArrayItem(int length, boolean signed) {
		super(length, BYTES_PER_ITEM, signed);
	}

	// TODO
	
}
