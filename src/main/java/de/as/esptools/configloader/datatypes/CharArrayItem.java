package de.as.esptools.configloader.datatypes;

public class CharArrayItem extends DataItem implements IArrayDataType {

	public static final String NAME = "char";

	public CharArrayItem(int length) {
		super(NAME, length);
	}

	@Override
	public String importDataString(String data) throws DataImportException {
		String token = data;
		String rest = null;
		int pos = this.getBinLength();
		if (pos < token.length()) {
			rest = token.substring(pos);
			token = token.substring(0, pos);
		}
		this.setData(token.getBytes());

		// if (!this.isInArray() && !this.allowLongDataImport() && rest != null)
		// {
		// throw new DataImportException("data array to long");
		// }

		if (!this.allowLongDataImport() && rest != null) {
			throw new DataImportException("data array to long");
		}

		return rest;
	}

	// @Override
	// public boolean isInArray() {
	// return this.getArrayLength() > 1;
	// }

	@Override
	public String exportDataString() {
		return new String(this.getData());
	}

	@Override
	protected boolean allowShortDataImport() {
		return true;
	}

	@Override
	protected boolean allowLongDataImport() {
		return false;
	}

	@Override
	public int getArrayLength() {
		return this.getBinLength();
	}

}
