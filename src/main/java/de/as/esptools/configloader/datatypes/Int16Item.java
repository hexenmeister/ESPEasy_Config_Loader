package de.as.esptools.configloader.datatypes;

public class Int16Item extends NumericItem {

	/**
	 * Datentyp-L�nge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 2;
	
	protected Int16Item(boolean signed) {
		super(BYTES_PER_ITEM, signed);
	}

	Int16Item(byte[] data, int offset, int bytesPerItem, boolean singned) {
        super(data, offset, BYTES_PER_ITEM, singned);
    }
}
