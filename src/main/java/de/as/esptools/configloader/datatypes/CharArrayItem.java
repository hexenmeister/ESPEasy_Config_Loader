package de.as.esptools.configloader.datatypes;

public class CharArrayItem extends ArrayDataItem<CharItem, Boolean> {

	private static final int BYTES_PER_ITEM = CharItem.BYTES_PER_ITEM;

	public CharArrayItem(int length) {
		super(CharItem.NAME, length, BYTES_PER_ITEM, Boolean.TRUE);
	}

	public CharArrayItem(int length, boolean nullTerminated) {
		super(CharItem.NAME, length, BYTES_PER_ITEM, Boolean.valueOf(nullTerminated));
	}

	@Override
	protected CharItem createType(byte[] data, int offset, Boolean ad) {
		return new CharItem(data, offset, ad.booleanValue());
	}

	@Override
	protected String getExportDelimeter() {
		return "";
	}

	@Override
	public String exportDataString() {
		String ret = super.exportDataString();
		return CharItem.quoteWhenNonPrinteable(ret);
	}

	protected String importDataStringIntern(String data) throws DataImportException {
		if (data == null) {
			throw new DataImportException("invalid input data (null)");
		}

		String token = data;
		String rest = null;
		if (data.trim().startsWith("\"")) {
			token = data.trim();
			int pp = token.indexOf("\"");
			int pa = pp;
			while ((pp = token.indexOf("\"", pp + 1)) >= 0) {
				if (token.charAt(pp - 1) != '\\') {
					token = token.substring(pa + 1, pp);
					if (pp + 1 <= token.length()) {
						rest = token.substring(pp + 1);
					}
					break;
				}
			}
			if (pp < 0) {
				throw new DataImportException("missing closing quotation");
			}
		}

		token = super.importDataStringIntern(token);
		if (token == null || token.isEmpty()) {
			return rest;
		}
		if (rest == null) {
			return token;
		}

		return rest + token;
	}

}
