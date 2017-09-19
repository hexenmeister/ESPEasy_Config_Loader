package de.as.esptools.configloader.datatypes;

import java.util.Arrays;
import java.util.StringTokenizer;

import de.as.esptools.configloader.datatypes.util.Util;

public abstract class DataItem implements IDataType {

	private byte[] data;

	protected DataItem(int length) {
		this.data = new byte[length];
	}

	protected byte[] getData() {
		return data;
	}

	protected void setData(byte[] bytes) throws DataImportException {
		if (bytes.length == this.data.length) {
			System.arraycopy(bytes, 0, this.data, 0, this.data.length);
		} else {
			if (bytes.length < this.data.length) {
				if (this.allowShortDataImport()) {
					System.arraycopy(bytes, 0, this.data, 0, bytes.length);
					Arrays.fill(this.data, bytes.length, this.data.length, (byte) 0);
				} else {
					throw new DataImportException("data array to short");
				}
			}
			if (bytes.length > this.data.length) {
				if (this.allowLongDataImport()) {
					System.arraycopy(bytes, 0, this.data, 0, this.data.length);
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
		return data.length;
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
		System.arraycopy(data, offset, this.data, 0, this.getBinLength());
	}

	@Override
	public byte[] exportBin() {
		return this.getData();
	}

	@Override
	public void importHex(String data) throws DataImportException {
		StringTokenizer st = new StringTokenizer(data);
		byte[] bytes = this.getData();
		int cnt = 0;
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			// TODO (Verbesserung):
			// Wenn die Länge!=2, dann in 2-Zeichen Strings aufteilen, wenn
			// nicht teilbar => Fehler
			if (cnt >= bytes.length) {
				if (this.allowLongDataImport()) {
					break;
				} else {
					throw new DataImportException("import string to long");
				}
			}
			bytes[cnt++] = (byte) Integer.parseInt(s, 16);
		}
		if (this.allowShortDataImport()) {
			for (int i = cnt, n = bytes.length; i < n; i++) {
				bytes[i] = 0;
			}
		} else if (cnt < bytes.length) {
			throw new DataImportException("import string to short");
		}
	}

	@Override
	public String exportHex() {
		return Util.bytesToHex(this.getData());
	}

}
