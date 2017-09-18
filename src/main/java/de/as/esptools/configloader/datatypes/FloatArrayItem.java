package de.as.esptools.configloader.datatypes;

public class FloatArrayItem extends NumericArrayItem {

	/**
	 * Datentyp-L�nge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 4;
	
	protected FloatArrayItem(int length, boolean signed) {
		super(length, BYTES_PER_ITEM, signed);
	}

}
