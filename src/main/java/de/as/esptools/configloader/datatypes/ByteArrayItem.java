package de.as.esptools.configloader.datatypes;

public class ByteArrayItem extends ArrayDataItem<ByteItem, Void> {

	private static final int BYTES_PER_ITEM = ByteItem.BYTES_PER_ITEM;

	public ByteArrayItem(int length) {
		super(ByteItem.NAME, length, BYTES_PER_ITEM);
	}

	@Override
	protected ByteItem createType(byte[] data, int offset, Void ad) {
		return new ByteItem(data, offset);
	}

}
