package de.as.esptools.configloader.datatypes;

public class CharItem extends DataItem {

	public static final String NAME = "char";
	public boolean nullTerminated = false;

	/**
	 * Datentyp-L�nge in Bytes.
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
	protected String importDataStringIntern(String data) throws DataImportException {
		if (data == null || data.isEmpty()) {
			this.setData(new byte[] { 0 });
			return null;
		}

		String token = data;
		String rest = null;
		int pos = this.getBinLength();
		if (pos < token.length()) {
			rest = token.substring(pos);
			token = token.substring(0, pos);
		}
		this.setData(token.getBytes());

		return rest;
	}

	@Override
	public String exportDataString() {
		if (this.isNullterminated()) {
			byte[] d = this.getData();
			int pos = 0;
			while (pos < d.length && d[pos] != 0) {
				pos++;
			}
			return new String(d, 0, pos);
		}
		return new String(this.getData());
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
