package de.as.esptools.configloader.datatypes;

public class IntItem extends NumericItem {

	/**
	 * Datentyp-Länge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 2;
	
	protected IntItem(boolean signed) {
		super(BYTES_PER_ITEM, signed);
	}

	IntItem(byte[] data, int offset, int bytesPerItem, boolean singned) {
        super(data, offset, BYTES_PER_ITEM, singned);
    }

}
