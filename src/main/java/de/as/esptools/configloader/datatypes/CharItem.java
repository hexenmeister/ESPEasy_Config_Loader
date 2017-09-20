package de.as.esptools.configloader.datatypes;

public class CharItem extends CharArrayItem {

    /**
     * Datentyp-Länge in Bytes.
     */
    static final int BYTES_PER_ITEM = 1;

    public CharItem() {
        super(BYTES_PER_ITEM);
    }

}
