package de.as.esptools.configloader.datatypes;

public class FloatArrayItem extends ArrayDataItem<FloatItem, Void> {

    private static final int BYTES_PER_ITEM = FloatItem.BYTES_PER_ITEM;

    public FloatArrayItem(int length) {
        super(FloatItem.NAME, length, BYTES_PER_ITEM);
    }

    @Override
    protected FloatItem createType(byte[] data, int offset, Void ad) {
        return new FloatItem(data, offset);
    }

}
