package de.as.esptools.configloader.datatypes;

public class IntItem extends NumericItem {

	/**
	 * Datentyp-L�nge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 2;
	
	protected IntItem(boolean signed) {
		super(BYTES_PER_ITEM, signed);
	}

}
