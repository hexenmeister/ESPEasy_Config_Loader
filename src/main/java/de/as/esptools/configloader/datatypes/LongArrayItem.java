package de.as.esptools.configloader.datatypes;

public class LongArrayItem extends ArrayDataItem<LongItem> {

    private static final int BYTES_PER_ITEM = LongItem.BYTES_PER_ITEM;

    public LongArrayItem(int length, boolean signed) {
        super(LongItem.NAME, length, BYTES_PER_ITEM, Boolean.valueOf(signed));
    }

    @Override
    protected LongItem createType(byte[] data, int offset, Object additionalData) {
        return new LongItem(data, offset, BYTES_PER_ITEM, ((Boolean) additionalData).booleanValue());
    }

}
