package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public class FloatItem extends DataItem {

	/**
	 * Datentyp-L�nge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 4;
	
	public FloatItem() {
		super(BYTES_PER_ITEM);
	}

	protected FloatItem(byte[] data, int offset) {
        super(data, offset, BYTES_PER_ITEM);
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
	 * Liefert L�nge eines Items in Bytes.
	 * 
	 * @return L�nge
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
	 * �bernimmt den gegebenen numerischen Wert.
	 * @param number Wert
	 * @throws DataImportException
	 */
	public void setFloat(float number) throws DataImportException {
		// TODO: Pr�fen, ob die L�nge (Array-L�nge) ausreicht?
		this.setData(Util.floatToByteArray(number));
	}

}
