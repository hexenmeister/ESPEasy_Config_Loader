package de.as.esptools.configloader.datatypes;

public class Int16ArrayItem extends ArrayDataItem<Int16Item> {

    private static final int BYTES_PER_ITEM = Int16Item.BYTES_PER_ITEM;

    public Int16ArrayItem(int length, boolean signed) {
        super(Int16Item.NAME, length, BYTES_PER_ITEM, Boolean.valueOf(signed));
    }

    @Override
    protected Int16Item createType(byte[] data, int offset, Object additionalData) {
        return new Int16Item(data, offset, BYTES_PER_ITEM, ((Boolean) additionalData).booleanValue());
    }

}
