package de.as.esptools.configloader.datatypes;

public class CharItem extends DataItem {

	public static final String NAME = "char";
	public boolean nullTerminated = false;

	/**
	 * Datentyp-Länge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 1;

	public CharItem() {
		this(false);
	}

	public CharItem(boolean nullTerminated) {
		super(NAME, BYTES_PER_ITEM);
		this.nullTerminated = nullTerminated;
	}

	protected CharItem(byte[] data, int offset) {
		this(data, offset, false);
	}

	protected CharItem(byte[] data, int offset, boolean nullTerminated) {
		super(NAME, data, offset, BYTES_PER_ITEM);
		this.nullTerminated = nullTerminated;
	}

	@Override
	public String importDataStringIntern(String data) throws DataImportException {
		if (data == null || data.isEmpty()) {
			this.setData(new byte[] { 0 });
			return null;
		}

		String token = data;
		String rest = null;

		if (data.trim().startsWith("\"")) {
			token = data.trim();
			int pp = token.indexOf("\"");
			int pa = pp;
			while ((pp = token.indexOf("\"", pp + 1)) >= 0) {
				if (token.charAt(pp - 1) != '\\') {
					if (pp + 1 <= token.length()) {
						rest = token.substring(pp + 1);
					}
					token = token.substring(pa + 1, pp);
					token = token.replaceAll("\\\\\"", "\"");
					break;
				}
			}
			if (pp < 0) {
				throw new DataImportException("missing closing quotation");
			}
			if (token.length() > this.getBinLength()) {
				throw new DataImportException("data too long");
			}
		} else {
			int pos = this.getBinLength();
			int pa = 0;
			if (pos < token.length()) {
				if (pos < token.length() - 1) {
					if (token.startsWith("\\")) {
						pa++;
						pos++;
					}
				}
				rest = token.substring(pos);
				token = token.substring(pa, pos);
			}
		}

		this.setData(token.getBytes());

		return rest;
	}

	@Override
	protected String exportDataStringIntern() {
		String ret;
		if (this.isNullterminated()) {
			byte[] d = this.getData();
			int pos = 0;
			while (pos < d.length && d[pos] != 0) {
				pos++;
			}
			ret = new String(d, 0, pos);
		} else {
			ret = new String(this.getData());
		}
		return ret;
	}

	@Override
	public String exportDataString() {
		String ret = exportDataStringIntern();
		return quoteWhenNonPrinteable(ret);
	}

	static String quoteWhenNonPrinteable(String data) {
		if (data.matches(".*\\s+.*")) {
			data = data.replaceAll("\"", "\\\"");
			data = "\"" + data + "\"";
		}
		return data;
	}

	protected boolean isNullterminated() {
		return this.nullTerminated;
	}

	@Override
	protected boolean allowShortDataImport() {
		return true;
	}

	@Override
	protected boolean allowLongDataImport() {
		return false;
	}

}
