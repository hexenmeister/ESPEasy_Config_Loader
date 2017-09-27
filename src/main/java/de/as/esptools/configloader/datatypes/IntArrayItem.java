package de.as.esptools.configloader.datatypes;

public class IntArrayItem extends ArrayDataItem<IntItem, Boolean> {

    private static final int BYTES_PER_ITEM = IntItem.BYTES_PER_ITEM;

    public IntArrayItem(int length, boolean signed) {
        super(IntItem.NAME, length, BYTES_PER_ITEM, Boolean.valueOf(signed));
    }

    @Override
    protected IntItem createType(byte[] data, int offset, Boolean additionalData) {
        return new IntItem(data, offset, BYTES_PER_ITEM, additionalData.booleanValue());
    }

}
