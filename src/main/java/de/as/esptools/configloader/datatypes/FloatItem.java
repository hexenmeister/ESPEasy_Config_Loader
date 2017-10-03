package de.as.esptools.configloader.datatypes;

import java.text.NumberFormat;
import java.util.Locale;

import de.as.esptools.configloader.datatypes.util.Util;

public class FloatItem extends DataItem {

	public static final String NAME = "float";

	/**
	 * Datentyp-L�nge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 4;

	public FloatItem() {
		super(NAME, BYTES_PER_ITEM);
	}

	protected FloatItem(byte[] data, int offset) {
		super(NAME, data, offset, BYTES_PER_ITEM);
	}

	@Override
	public String importDataStringIntern(String data) throws DataImportException {
		if (data == null) {
			throw new DataImportException("invalid input data (null)");
		}

		String token = data.trim();
		String rest = null;

		int pos = Util.searchTokenSplitPosition(token, " \t\n\r\f");
		if (pos > 0) {
			rest = token.substring(pos);
			token = token.substring(0, pos);
		}

		float fNum;
		try {
			fNum = Float.parseFloat(token);
		} catch (NumberFormatException e) {
			throw new DataImportException("invalid input data: not a float (" + token + ")");
		}
		this.setFloat(fNum);

		return rest;
	}

	private static final NumberFormat NF = NumberFormat.getNumberInstance(Locale.US);

	static {
		NF.setGroupingUsed(false);
		NF.setMinimumIntegerDigits(1);
	}

	@Override
	protected String exportDataStringIntern() {
		return NF.format(this.getFloat());
	}

	/**
	 * Liefert L�nge eines Items in Bytes.
	 * 
	 * @return L�nge
	 */
	public int getItemLengthInBytes() {
		return BYTES_PER_ITEM;
	}

	/**
	 * Liefert den Wert numerisch (als long),
	 * 
	 * @return Wert
	 */
	public float getFloat() {
		return Util.byteArrayToFloat(this.getData());
	}

	/**
	 * �bernimmt den gegebenen numerischen Wert.
	 * 
	 * @param number
	 *            Wert
	 * @throws DataImportException
	 */
	public void setFloat(float number) throws DataImportException {
		// TODO: Pr�fen, ob die L�nge (Array-L�nge) ausreicht?
		this.setData(Util.floatToByteArray(number));
	}

}
