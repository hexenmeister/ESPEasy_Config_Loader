package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public class BooleanItem extends DataItem {

	public static final String NAME = "boolean";

	/**
	 * Datentyp-Länge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 1;

	public BooleanItem() {
		super(NAME, BYTES_PER_ITEM);
	}

	protected BooleanItem(byte[] data, int offset) {
		super(NAME, data, offset, BYTES_PER_ITEM);
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

		if (token.equalsIgnoreCase("true") || token.equals("1") || token.equals("01")) {
			setData(new byte[] { (byte) 0x01 });
		} else if (token.equalsIgnoreCase("false") || token.equals("0") || token.equals("00")) {
			setData(new byte[] { (byte) 0x00 });
		} else
			throw new DataImportException("unknown import format");

		return rest;
	}

	@Override
	protected String exportDataStringIntern() {
		byte b = getData()[0];
		return b != 0 ? "1" : "0";
	}

}
