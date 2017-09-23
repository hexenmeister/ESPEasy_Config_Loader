package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public abstract class NumericItem extends DataItem {

	private boolean signed = true;
	private int bytesPerItem;

	protected NumericItem(String name, int bytesPerItem, boolean singned) {
		super(name, bytesPerItem);
		this.signed = singned;
		this.bytesPerItem = bytesPerItem;
	}

	protected NumericItem(String name, byte[] data, int offset, int bytesPerItem, boolean singned) {
		super(name, data, offset, bytesPerItem);
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
	public String importDataString(String data) throws DataImportException {
		String token = data.trim();
		String rest = null;

		int pos = Util.searchTokenSplitPosition(token," \t\r\n\f");
		if(pos>0) {
			rest = token.substring(pos);
			token = token.substring(0,  pos);
		}
		
		long longNum;
		if (isSigned()) {
			longNum = Long.parseLong(token);
		} else {
			try {
				longNum = Long.parseUnsignedLong(token);
			} catch (NumberFormatException e) {
				throw new DataImportException(e.getMessage());
			}
		}
		this.setNumber(longNum);
		
		return rest;
	}

	@Override
	public String exportDataString() {
		return Long.toString(this.getNumber());
	}

	/**
	 * Liefert den Wert numerisch (als long).
	 * 
	 * @return Wert
	 */
	public long getNumber() {
		return Util.reverseByteArrayToLong(this.getData(), this.isSigned());
	}

	/**
	 * Übernimmt den gegebenen numerischen Wert.
	 * 
	 * @param number
	 *            Wert
	 * @throws DataImportException
	 */
	public void setNumber(long number) throws DataImportException {
		// TODO: Prüfen, ob die Länge (Array-Länge) ausreicht
		if (!this.isSigned() && number < 0) {
			throw new DataImportException("negative numbers are not supported");
		}
		byte[] bytes = Util.longToReverseByteArray(number, this.getItemLengthInBytes());
		setData(bytes);
	}
}
