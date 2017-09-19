package de.as.esptools.configloader.datatypes;

public class BooleanItem extends DataItem {

	public BooleanItem() {
		super(1);
	}

	@Override
	public void importString(String data) throws DataImportException {
		if (data.equalsIgnoreCase("true") || data.equals("1") || data.equals("01")) {
			setData(new byte[] { (byte) 0x01 });
		} else {
			setData(new byte[] { (byte) 0x00 });
		}
	}

	@Override
	public String exportString() {
		byte b = getData()[0];
		return b != 0 ? "1" : "0";
	}

}
