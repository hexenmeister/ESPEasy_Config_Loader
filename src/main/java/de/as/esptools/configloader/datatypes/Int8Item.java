package de.as.esptools.configloader.datatypes;

public class Int8Item extends NumericItem {

	
	/**
	 * Datentyp-Länge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 1;
	
	protected Int8Item(boolean signed) {
		super(BYTES_PER_ITEM, signed);
	}

	Int8Item(byte[] data, int offset, int bytesPerItem, boolean singned) {
        super(data, offset, BYTES_PER_ITEM, singned);
    }
}
