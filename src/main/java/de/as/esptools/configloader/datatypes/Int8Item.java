package de.as.esptools.configloader.datatypes;

public class Int8Item extends NumericItem {

	
	/**
	 * Datentyp-Länge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 1;
	
	protected Int8Item(boolean signed) {
		super(BYTES_PER_ITEM, signed);
	}

}
