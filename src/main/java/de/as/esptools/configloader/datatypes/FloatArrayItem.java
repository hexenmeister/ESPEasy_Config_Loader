package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public class FloatArrayItem extends DataItem {

	/**
	 * Datentyp-Länge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 4;
	
	public FloatArrayItem(int length) {
		super(length*BYTES_PER_ITEM);
	}

	@Override
	public void importString(String data) throws DataImportException {
		float fNum = Float.parseFloat(data);
		this.setFloat(fNum);
	}

	@Override
	public String exportString() {
		return Float.toString(this.getFloat());
	}
	
	/**
	 * Liefert Länge eines Items in Bytes.
	 * 
	 * @return Länge
	 */
	public int getItemLengthInBytes() {
		return BYTES_PER_ITEM;
	}

	/**
	 * Liefert den Wert numerisch (als long),
	 * @return Wert
	 */
	public float getFloat() {
		return Util.byteArrayToFloat(this.getData());
	}

	/**
	 * Übernimmt den gegebenen numerischen Wert.
	 * @param number Wert
	 * @throws DataImportException
	 */
	public void setFloat(float number) throws DataImportException {
		// TODO: Prüfen, ob die Länge (Array-Länge) ausreicht?
		this.setData(Util.floatToByteArray(number));
	}

}
