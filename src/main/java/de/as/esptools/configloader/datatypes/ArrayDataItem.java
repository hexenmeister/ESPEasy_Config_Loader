package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public abstract class ArrayDataItem<T extends DataItem> implements IArrayDataType {

	private DataItem[] array;
	private byte[] data;
	private int bytesPerItem;
	private String name;

	protected ArrayDataItem(String name, int length, int bytesPerItem) {
		if (length <= 0) {
			throw new RuntimeException("empty array is not allowed");
		}
		this.name = name;
		this.array = new DataItem[length];
		this.data = new byte[length * bytesPerItem];
		this.bytesPerItem = bytesPerItem;
		for (int i = 0; i < length; i++) {
			this.array[i] = this.createType(this.data, i * bytesPerItem);
		}
	}

	public String getTypeName() {
		return this.name;
	}

	protected abstract T createType(byte[] data, int offset);

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
		return Util.bytesToHex(this.getData(), this.bytesPerItem, "\r\n\t");
	}

	@Override
	public String importDataString(String data) throws DataImportException {
		for (int i = 0, n = this.array.length; i < n; i++) {
			data = this.array[i].importDataString(data);
		}
		
		if (!this.array[0].isInArray() && !this.array[0].allowLongDataImport() && data != null) {
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

	public String exportTypeAndDataString() {
		return this.getTypeName()
				+ DataItem.EMPTY.substring(0, Math.max(0, DataItem.INTENT - this.getTypeName().length())) + " "
				+ exportDataString();
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
}
