package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public class FloatItem extends DataItem {

	public static final String NAME = "float";
	
	/**
	 * Datentyp-L�nge in Bytes.
	 */
	static final int BYTES_PER_ITEM = 4;
	
	public FloatItem() {
		super(NAME, BYTES_PER_ITEM);
	}

	protected FloatItem(byte[] data, int offset) {
        super(NAME, data, offset, BYTES_PER_ITEM);
    }
	
	@Override
	public String importDataString(String data) throws DataImportException {
		if (data == null) {
			throw new DataImportException("invalid input data (null)");
		}
		
		String token = data.trim();
		String rest = null;

		int pos = Util.searchTokenSplitPosition(token," \t\n\r\f");
		if(pos>0) {
			rest = token.substring(pos);
			token = token.substring(0,  pos);
		}
		
		float fNum = Float.parseFloat(token);
		this.setFloat(fNum);
		
		return rest;
	}
	
	@Override
	public String exportDataString() {
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
