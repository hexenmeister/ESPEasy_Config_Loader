package de.as.esptools.configloader.datatypes;

import java.util.Arrays;

import de.as.esptools.configloader.datatypes.util.Util;

public abstract class DataItem implements IDataType {

	private byte[] data;
	private int offset = 0;
	private int length = 0;
	private String name;
	private boolean isInArray = false;

	/**
	 * Erstellt ein XDataItem mit eigenem ByteArray in der (für ein Item)
	 * notwendigen Länge.
	 * 
	 * @param name
	 *            Typname
	 * @param length
	 *            Länge der Item-Representation in Bytes
	 */
	protected DataItem(String name, int length) {
		this.name = name;
		this.data = new byte[length];
		this.length = length;
		this.isInArray = false;
	}

	/**
	 * Erstellt ein Item als Referenz auf ein existierendes DataArray.
	 * 
	 * @param name
	 *            Typname
	 * @param data
	 *            Referenz auf ein existierendes byteArray (für
	 *            XArrayItem-Elemente => sie halten selbst die Daten für alle
	 *            enthaltenen Elemente).
	 * @param offset
	 *            Erste Index im Array für die Bytes
	 *
	 * @param length
	 *            Länge der Item-Representation in Bytes
	 */
	protected DataItem(String name, byte[] data, int offset, int length) {
		this.name = name;
		this.data = data;
		this.offset = offset;
		this.length = length;
		this.isInArray = true;
	}

	public String getTypeName() {
		return this.name;
	}

	public boolean isInArray() {
		return this.isInArray;
	}

	static final String EMPTY = "                             ";
	static final int INDENT = 13;

	public String exportTypeAndDataString(boolean indent) {
		String indstr = indent ? EMPTY.substring(0, Math.max(0, INDENT - this.getTypeName().length())) : "";
		return this.getTypeName() + " :" + indstr + " " + exportDataString();
	}

	protected byte[] getData() {
		if (this.offset == 0 && this.length == this.data.length) {
			return this.data;
		}

		byte[] pData = new byte[this.length];
		System.arraycopy(this.data, this.offset, pData, 0, this.length);
		return pData;
	}

	protected void setData(byte[] bytes) throws DataImportException {
		if (bytes.length == this.length) {
			System.arraycopy(bytes, 0, this.data, this.offset, this.length);
		} else {
			if (bytes.length < this.length) {
				if (this.allowShortDataImport()) {
					System.arraycopy(bytes, 0, this.data, this.offset, bytes.length);
					Arrays.fill(this.data, this.offset + bytes.length, this.offset + this.length, (byte) 0);
				} else {
					throw new DataImportException("data array to short");
				}
			}
			if (bytes.length > this.length) {
				if (this.allowLongDataImport()) {
					System.arraycopy(bytes, 0, this.data, this.offset, this.length);
				} else {
					throw new DataImportException("data array to long");
				}
			}
		}
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

	@Override
	public int getBinLength() {
		return this.length;
	}

	@Override
	public void importBin(byte[] data) throws DataImportException {
		if (data.length != this.length) {
			throw new DataImportException("wrong length");
		}
		this.importBin(data, 0);
	}

	@Override
	public void importBin(byte[] data, int offset) throws DataImportException {
		if (data.length < offset + this.offset + this.length) {
			throw new DataImportException("data array to short");
		}
		System.arraycopy(data, offset, this.data, this.offset, this.getBinLength());
	}

	@Override
	public byte[] exportBin() {
		return this.getData();
	}

	@Override
	public void importHex(String data) throws DataImportException {
		if (this.offset == 0) {
			try {
				Util.hexToBytes(this.getData(), data, this.allowShortDataImport(), this.allowLongDataImport());
			} catch (NumberFormatException e) {
				throw new DataImportException("invalid data: " + e.getMessage());
			}
		} else {
			byte[] pData = new byte[this.length];
			Util.hexToBytes(pData, data, this.allowShortDataImport(), this.allowLongDataImport());
			System.arraycopy(pData, 0, this.data, this.offset, this.length);
		}
	}

	@Override
	public String exportHex() {
		return Util.bytesToHex(this.getData());
	}

}
