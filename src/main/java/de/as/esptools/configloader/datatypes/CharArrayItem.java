package de.as.esptools.configloader.datatypes;

public class CharArrayItem extends ArrayDataItem<CharItem> {

    private static final int BYTES_PER_ITEM = CharItem.BYTES_PER_ITEM;

    public CharArrayItem(int length) {
        super(CharItem.NAME, length, BYTES_PER_ITEM, Boolean.TRUE);
    }

    public CharArrayItem(int length, boolean nullTerminated) {
        super(CharItem.NAME, length, BYTES_PER_ITEM, Boolean.valueOf(nullTerminated));
    }

    @Override
    protected CharItem createType(byte[] data, int offset, Object ad) {
        return new CharItem(data, offset, ((Boolean) ad).booleanValue());
    }

    @Override
    protected String getExportDelimeter() {
        return "";
    }

}
