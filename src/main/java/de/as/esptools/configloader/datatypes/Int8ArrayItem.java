package de.as.esptools.configloader.datatypes;

public class Int8ArrayItem extends ArrayDataItem<Int8Item, Boolean> {

    private static final int BYTES_PER_ITEM = Int8Item.BYTES_PER_ITEM;

    public Int8ArrayItem(int length, boolean signed) {
        super(Int8Item.NAME, length, BYTES_PER_ITEM, Boolean.valueOf(signed));
    }

    @Override
    protected Int8Item createType(byte[] data, int offset, Boolean additionalData) {
        return new Int8Item(data, offset, BYTES_PER_ITEM, additionalData.booleanValue());
    }

}
