package de.as.esptools.configloader.datatypes;

public class FloatArrayItem extends DataItem {

	// TODO: implementieren
	
	/**
	 * Datentyp-L�nge in Bytes.
	 */
	private static final int BYTES_PER_ITEM = 4;
	
	protected FloatArrayItem(int length) {
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
		// TODO
		return 0;
	}

	/**
	 * �bernimmt den gegebenen numerischen Wert.
	 * @param number Wert
	 * @throws DataImportException
	 */
	public void setFloat(float number) throws DataImportException {
		// TODO: Pr�fen, ob die L�nge (Array-L�nge) ausreicht?
		
		// TODO
//		byte[] bytes = floatToByteArray(number);
//		setData(bytes);
	}

//	public void testFloat() {
//		float f = 1f;
//		java.nio.ByteBuffer buf = java.nio.ByteBuffer.allocate(4);
//		buf.putFloat(f);
//		byte[] bytes = buf.array();
//		System.out.println(Arrays.toString(bytes));
//		java.nio.ByteBuffer buf2 = java.nio.ByteBuffer.allocate(4);
//		buf2.put(bytes);
//		float f2 = buf2.getFloat(0);
//		
//	}

}
