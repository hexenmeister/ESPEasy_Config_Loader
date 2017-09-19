package de.as.esptools.configloader.datatypes;

import java.util.Arrays;
import java.util.StringTokenizer;

import de.as.esptools.configloader.datatypes.util.Util;

public abstract class DataItem implements IDataType {

	private byte[] data;
	private int offset = 0;
	private int length = 0;

	/**
	 * Erstellt ein XDataItem mit eigenem ByteArray in der (für ein Item)
	 * notwendigen Länge.
	 * 
	 * @param length
	 *            Länge der Item-Representation in Bytes
	 */
	protected DataItem(int length) {
		this.data = new byte[length];
		this.length = length;
	}

	/**
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
	protected DataItem(byte[] data, int offset, int length) {
		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	protected byte[] getData() {
		return data;
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
		Util.hexToBytes(this.getData(), data, this.allowShortDataImport(), this.allowLongDataImport());
		// StringTokenizer st = new StringTokenizer(data);
		// byte[] bytes = this.getData();
		// int cnt = 0;
		// while (st.hasMoreTokens()) {
		// String s = st.nextToken();
		// // TODO (Verbesserung):
		// // Wenn die Länge!=2, dann in 2-Zeichen Strings aufteilen, wenn
		// // nicht teilbar => Fehler
		// if (cnt >= bytes.length) {
		// if (this.allowLongDataImport()) {
		// break;
		// } else {
		// throw new DataImportException("import string to long");
		// }
		// }
		// bytes[cnt++] = (byte) Integer.parseInt(s, 16);
		// }
		// if (this.allowShortDataImport()) {
		// for (int i = cnt, n = bytes.length; i < n; i++) {
		// bytes[i] = 0;
		// }
		// } else if (cnt < bytes.length) {
		// throw new DataImportException("import string to short");
		// }
	}

	@Override
	public String exportHex() {
		return Util.bytesToHex(this.getData());
	}

}
