package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public class ByteArrayItem extends DataItem implements IArrayDataType {

	public static final String NAME = "byte";

	public ByteArrayItem(int length) {
		super(NAME, length);
	}

	@Override
	public String importDataString(String data) throws DataImportException {
		String token = data.trim();
		String rest = null;

		int pos = Util.searchTokenSplitPosition(token, " \t\r\n\f", this.getBinLength());
		if (pos > 0) {
			rest = token.substring(pos);
			token = token.substring(0, pos);
		}

		this.importHex(token);

		return rest;
	}

	@Override
	public String exportDataString() {
		return this.exportHex();
	}

	@Override
	public int getArrayLength() {
		return this.getBinLength();
	}
}
