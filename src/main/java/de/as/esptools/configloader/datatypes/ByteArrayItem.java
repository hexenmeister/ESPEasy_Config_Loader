package de.as.esptools.configloader.datatypes;

public class ByteArrayItem extends DataItem {

	public ByteArrayItem(int length) {
		super(length);
	}

	@Override
	public void importString(String data) throws DataImportException {
		this.importHex(data);
	}

	@Override
	public String exportString() {
		return this.exportHex();
	}

}
