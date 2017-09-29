package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public abstract class ArrayDataItem<T extends DataItem, V> implements IArrayDataType {

	static final int DEFAULT_HEX_PER_LINE = 16;

	private DataItem[] array;

	private byte[] data;

	private int bytesPerItem;

	private String name;

	protected ArrayDataItem(String name, int length, int bytesPerItem) {
		this(name, length, bytesPerItem, null);
	}

	protected ArrayDataItem(String name, int length, int bytesPerItem, V additionalData) {
		if (length <= 0) {
			throw new RuntimeException("empty array is not allowed");
		}
		this.name = name;
		this.array = new DataItem[length];
		this.data = new byte[length * bytesPerItem];
		this.bytesPerItem = bytesPerItem;
		for (int i = 0; i < length; i++) {
			this.array[i] = this.createType(this.data, i * bytesPerItem, additionalData);
		}
	}

	public String getTypeName() {
		return this.name;
	}

	protected abstract T createType(byte[] data, int offset, V additionalData);

	protected String getArrayDelimenter() {
		return "\r\n\t";
	}

	@Override
	public int getBinLength() {
		return this.data.length;
	}

	protected byte[] getData() {
		return data;
	}

	public int getArrayLength() {
		return this.array.length;
	}

	@Override
	public void importBin(byte[] data) throws DataImportException {
		if (data.length != this.data.length) {
			throw new DataImportException("wrong length");
		}
		this.importBin(data, 0);
	}

	@Override
	public void importBin(byte[] data, int offset) throws DataImportException {
		if (data.length < offset + this.data.length) {
			throw new DataImportException("data array to short");
		}
		System.arraycopy(data, offset, this.data, 0, this.data.length);
	}

	@Override
	public byte[] exportBin() {
		return this.getData();
	}

	@Override
	public void importHex(String data) throws DataImportException {
		Util.hexToBytes(this.getData(), data, this.allowShortDataImport(), this.allowLongDataImport());
	}

	@Override
	public String exportHex() {
		return Util.bytesToHex(this.getData(), this.getHexPerLine(), this.getArrayDelimenter());
	}

	@Override
	public final void importDataString(String data) throws DataImportException {
		if (data == null) {
			throw new DataImportException("invalid input data (null)");
		}

		String rest = this.importDataStringIntern(data);

		// if (!this.isInArray() && !this.allowLongDataImport() && rest != null
		// && !rest.trim().isEmpty()) {
		// throw new DataImportException("data array to long");
		// }
	}

	protected String importDataStringIntern(String data) throws DataImportException {
		if (data == null) {
			throw new DataImportException("invalid input data (null)");
		}

		for (int i = 0, n = this.array.length; i < n; i++) {
			data = this.array[i].importDataStringIntern(data);
		}

		if (!this.array[0].allowLongDataImport() && data != null && !data.trim().isEmpty()) {
			throw new DataImportException("data to long");
		}

		return data;
	}

	@Override
	public String exportDataString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, n = this.array.length; i < n; i++) {
			sb.append(this.array[i].exportDataString());
			if (i < n - 1) {
				sb.append(this.getExportDelimeter());
			}
		}
		return sb.toString();
	}

	/**
	 * Lieefert Zeichenkette, die als Trenner zw. den Daten einzelnen Items beim
	 * Export fungiert.
	 * 
	 * @return Trenner-String
	 */
	protected String getExportDelimeter() {
		return " ";
	}

	public String exportTypeAndDataString(boolean indent) {
		String indstr = indent ? DataItem.EMPTY.substring(0, Math.max(0, DataItem.INDENT - this.getTypeName().length()))
				: "";
		return this.getTypeName() + "[" + this.getArrayLength() + "]" + " :" + indstr + " " + exportDataString();
	}

	/**
	 * Gibt an, ob es erlaubt sein soll,auch kürzere (als Array-Buffer) Daten zu
	 * importieren. In diesem Fall wird der Rest mit Nulen überschrieben.
	 * 
	 * @return Entscheidung
	 */
	protected boolean allowShortDataImport() {
		return false;
	}

	/**
	 * Gibt an, ob es erlaubt sein soll,auch längere (als Array-Buffer) Daten zu
	 * importieren. In diesem Fall wird der Rest ignoriert.
	 * 
	 * @return Entscheidung
	 */
	protected boolean allowLongDataImport() {
		return false;
	}

	public int getBytesPerItem() {
		return bytesPerItem;
	}

	protected int getHexPerLine() {
		return DEFAULT_HEX_PER_LINE;
	}
}
