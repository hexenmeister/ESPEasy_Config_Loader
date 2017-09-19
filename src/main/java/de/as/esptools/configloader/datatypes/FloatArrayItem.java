package de.as.esptools.configloader.datatypes;

public class FloatArrayItem extends DataItem {

	// TODO: implementieren
	
	/**
	 * Datentyp-Länge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 4;
	
	protected FloatArrayItem(int length) {
		super(length*BYTES_PER_ITEM); // TODO
	}

	@Override
	public void importString(String data) throws DataImportException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String exportString() {
		// TODO Auto-generated method stub
		return null;
	}

}
