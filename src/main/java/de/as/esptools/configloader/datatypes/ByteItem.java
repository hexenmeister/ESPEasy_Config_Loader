package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public class ByteItem extends DataItem {

	public static final String NAME = "byte";

	/**
	 * Datentyp-Länge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 1;

	public ByteItem() {
		super(NAME, BYTES_PER_ITEM);
	}

	protected ByteItem(byte[] data, int offset) {
		super(NAME, data, offset, BYTES_PER_ITEM);
	}

	@Override
	public String importDataString(String data) throws DataImportException {
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

		this.importHex(token);

		if (!this.isInArray() && !this.allowLongDataImport() && rest != null && !rest.trim().isEmpty()) {
			throw new DataImportException("data array to long");
		}

		return rest;
	}

	@Override
	public String exportDataString() {
		return this.exportHex();
	}

}
