package de.as.esptools.configloader.datatypes;

public class LongArrayItem extends ArrayDataItem<LongItem> {

    private static final int BYTES_PER_ITEM = LongItem.BYTES_PER_ITEM;

    private boolean signed;

    public LongArrayItem(int length, boolean signed) {
        super(LongItem.NAME, length, BYTES_PER_ITEM);
        this.signed = signed;
    }

    @Override
    protected LongItem createType(byte[] data, int offset) {
        return new LongItem(data, offset, BYTES_PER_ITEM, this.signed);
    }

}
