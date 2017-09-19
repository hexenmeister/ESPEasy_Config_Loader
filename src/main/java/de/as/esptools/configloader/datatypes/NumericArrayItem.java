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
		this.setNumber(longNum);
	}

	@Override
	public String exportString() {
		return Long.toString(this.getNumber());
	}

	/**
	 * Liefert den Wert numerisch (als long),
	 * @return Wert
	 */
	public long getNumber() {
		return Util.reverseByteArrayToLong(this.getData(), this.isSigned());
	}

	/**
	 * Übernimmt den gegebenen numerischen Wert.
	 * @param number Wert
	 * @throws DataImportException
	 */
	public void setNumber(long number) throws DataImportException {
		// TODO: Prüfen, ob die Länge (Array-Länge) ausreicht
		byte[] bytes = Util.longToReverseByteArray(number, this.getItemLengthInBytes());
		setData(bytes);
	}
}
