package de.as.esptools.configloader.datatypes;

public class FloatArrayItem extends ArrayDataItem<FloatItem> {

	private static final int BYTES_PER_ITEM = FloatItem.BYTES_PER_ITEM;

	public FloatArrayItem(int length) {
		super(length, BYTES_PER_ITEM);
	}

	@Override
	protected FloatItem createType(byte[] data, int offset) {
		return new FloatItem(data, offset);
	}

}
