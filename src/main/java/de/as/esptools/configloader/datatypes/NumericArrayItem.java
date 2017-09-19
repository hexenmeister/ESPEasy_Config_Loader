package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public abstract class NumericArrayItem extends DataItem {

	private boolean signed = true;
	private int bytesPerItem;

	protected NumericArrayItem(int length, int bytesPerItem, boolean singned) {
		super(length * bytesPerItem);
		this.signed = singned;
		this.bytesPerItem = bytesPerItem;
	}

	public boolean isSigned() {
		return this.signed;
	}

	/**
	 * Liefert Länge eines Items in Bytes.
	 * 
	 * @return Länge
	 */
	public int getItemLengthInBytes() {
		return this.bytesPerItem;
	}

	@Override
	public void importString(String data) throws DataImportException {
		long longNum;
		if (isSigned()) {
			longNum = Long.parseLong(data);
		} else {
			longNum = Long.parseUnsignedLong(data);
		}
		byte[] bytes = Util.longToReverseByteArray(longNum, this.getItemLengthInBytes());
		setData(bytes);
	}

	@Override
	public String exportString() {
		return Long.toString(Util.reverseByteArrayToLong(this.getData(), this.isSigned()));
	}

}
