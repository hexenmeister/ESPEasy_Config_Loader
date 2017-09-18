package de.as.esptools.configloader.datatypes;

public class CharArrayItem extends DataItem {

	public CharArrayItem(int length) {
		super(length);
	}

	@Override
	public void importString(String data) throws DataImportException {
		this.setData(data.getBytes());
	}

	@Override
	public String exportString() {
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

}
