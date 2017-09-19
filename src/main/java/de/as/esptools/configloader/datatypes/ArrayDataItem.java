package de.as.esptools.configloader.datatypes;

import de.as.esptools.configloader.datatypes.util.Util;

public abstract class ArrayDataItem<T extends DataItem> implements IDataType {

	private DataItem[] array;
	private byte[] data;
	private int bytesPerItem;

	protected ArrayDataItem(int length, int bytesPerItem) {
		if (length <= 0) {
			throw new RuntimeException("empty array is not allowed"); // TODO
																		// Exception
		}
		this.array = new DataItem[length];
		this.data = new byte[length * bytesPerItem];
		this.bytesPerItem = bytesPerItem;
		for (int i = 0; i < length; i++) {
			this.array[i] = this.createType(this.data, i * bytesPerItem);
		}
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
		// TODO Auto-generated method stub
	}

	@Override
	public String exportHex() {
		// String ret = "";
		// for (int i = 0, n = this.array.length; i < n; i++) {
		// ret += this.array[i].exportHex();
		// ret += this.getArrayDelimenter();
		// }
		//
		// return ret;
		return Util.bytesToHex(this.getData(), this.bytesPerItem, "\r\n\t");
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
