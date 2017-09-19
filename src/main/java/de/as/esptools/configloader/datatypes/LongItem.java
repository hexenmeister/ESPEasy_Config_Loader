package de.as.esptools.configloader.datatypes;

public class LongItem extends NumericItem {

	/**
	 * Datentyp-Länge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 4;
	
	protected LongItem(boolean signed) {
		super(BYTES_PER_ITEM, signed);
	}

}
