package de.as.esptools.configloader.datatypes;

public class Int8ArrayItem extends ArrayDataItem<Int8Item> {

	private static final int BYTES_PER_ITEM = Int8Item.BYTES_PER_ITEM;
	private boolean signed;

	public Int8ArrayItem(int length, boolean signed) {
		super(Int8Item.NAME, length, BYTES_PER_ITEM);
		this.signed = signed;
	}

	@Override
	protected Int8Item createType(byte[] data, int offset) {
		return new Int8Item(data, offset, BYTES_PER_ITEM, this.signed);
	}
 
}
