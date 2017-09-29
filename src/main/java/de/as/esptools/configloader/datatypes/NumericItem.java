package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public abstract class NumericItem extends DataItem {

	private boolean signed = true;
	private int bytesPerItem;
	private long maxValue;
	private long minValue;

	protected NumericItem(String name, int bytesPerItem, boolean singned) {
		super(name, bytesPerItem);
		init(bytesPerItem, singned);
	}

	protected NumericItem(String name, byte[] data, int offset, int bytesPerItem, boolean singned) {
		super(name, data, offset, bytesPerItem);
		init(bytesPerItem, singned);
	}

	private void init(int bytesPerItem, boolean singned) {
		this.signed = singned;
		this.bytesPerItem = bytesPerItem;
		this.maxValue = computeMaxValue();
		this.minValue = computeMinValue();
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
	protected String importDataStringIntern(String data) throws DataImportException {
		if (data == null) {
			throw new DataImportException("invalid input data (null)");
		}

		String token = data.trim();
		String rest = null;

		int pos = Util.searchTokenSplitPosition(token, " \t\r\n\f");
		if (pos > 0) {
			rest = token.substring(pos);
			token = token.substring(0, pos);
		}

		long longNum;
		try {
			if (isSigned()) {
				longNum = Long.parseLong(token);
			} else {
				longNum = Long.parseUnsignedLong(token);
			}
		} catch (NumberFormatException e) {
			throw new DataImportException(e.getMessage());
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
		if (number > this.getMaxValue()) {
			throw new DataImportException("number too big");
		}
		if (number < this.getMinValue()) {
			throw new DataImportException("number too small");
		}

		if (!this.isSigned() && number < 0) {
			throw new DataImportException("negative numbers are not supported");
		}
		byte[] bytes = Util.longToReverseByteArray(number, this.getItemLengthInBytes());
		setData(bytes);
	}

	protected long getMaxValue() {
		return this.maxValue;
	}

	protected long getMinValue() {
		return this.minValue;
	}

	protected long computeMaxValue() {
		int n = this.getItemLengthInBytes();
		byte[] ta = new byte[n];
		for (int i = 0; i < n - 1; i++) {
			ta[i] = (byte) 0xFF;
		}
		ta[ta.length - 1] = this.isSigned() ? (byte) 0x7F : (byte) 0xfF;
		return Util.reverseByteArrayToLong(ta, this.isSigned());
	}

	protected long computeMinValue() {
		if (this.isSigned()) {
			int n = this.getItemLengthInBytes();
			byte[] ta = new byte[n];
			for (int i = 0; i < n - 1; i++) {
				ta[i] = (byte) 0x00;
			}
			// ta[ta.length - 1] = this.isSigned() ? (byte) 0x80 : 00;
			// return Util.reverseByteArrayToLong(ta, isSigned());
			ta[ta.length - 1] = (byte) 0x80;
			return Util.reverseByteArrayToLong(ta, true);
		} else {
			return 0;
		}
	}
}
