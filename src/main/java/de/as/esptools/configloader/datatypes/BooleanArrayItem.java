package de.as.esptools.configloader.datatypes;

public class BooleanArrayItem extends ArrayDataItem<BooleanItem> {

	private static final int BYTES_PER_ITEM = BooleanItem.BYTES_PER_ITEM;

	public BooleanArrayItem(int length) {
		super(length, BYTES_PER_ITEM);
	}

	@Override
	protected BooleanItem createType(byte[] data, int offset) {
		return new BooleanItem(data, offset);
	}

}