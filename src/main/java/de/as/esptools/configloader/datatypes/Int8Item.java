package de.as.esptools.configloader.datatypes;

public class Int8Item extends NumericIntegerItem {

	public static final String NAME = "int8";
	
    /**
     * Datentyp-Länge in Bytes.
     */
    static final int BYTES_PER_ITEM = 1;

    public Int8Item(boolean signed) {
        super(NAME, BYTES_PER_ITEM, signed);
    }

    Int8Item(byte[] data, int offset, int bytesPerItem, boolean singned) {
        super(NAME, data, offset, BYTES_PER_ITEM, singned);
    }
}
