package de.as.esptools.configloader.datatypes;

public class IntArrayItem extends ArrayDataItem<IntItem> {

	private static final int BYTES_PER_ITEM = IntItem.BYTES_PER_ITEM;
	private boolean signed;

	public IntArrayItem(int length, boolean signed) {
		super(IntItem.NAME, length, BYTES_PER_ITEM);
		this.signed = signed;
	}

	@Override
	protected IntItem createType(byte[] data, int offset) {
		return new IntItem(data, offset, BYTES_PER_ITEM, this.signed);
	}

}
