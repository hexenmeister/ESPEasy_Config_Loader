package de.as.esptools.configloader.datatypes;

public class CharArrayItem extends ArrayDataItem<CharItem> {

	private static final int BYTES_PER_ITEM = CharItem.BYTES_PER_ITEM;

	public CharArrayItem(int length) {
		super(CharItem.NAME, length, BYTES_PER_ITEM);
	}

	@Override
	protected CharItem createType(byte[] data, int offset) {
		return new CharItem(data, offset, true);
	}

	@Override
	protected String getExportDelimeter() {
		return "";
	}

}
