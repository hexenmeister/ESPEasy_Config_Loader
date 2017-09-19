package de.as.esptools.configloader.datatypes;

import java.nio.ByteBuffer;

public abstract class NumericArrayItem extends DataItem {

	private boolean signed = true;
	private int bytesPerItem;

	protected NumericArrayItem(int length, int bytesPerItem, boolean singned) {
		super(length * bytesPerItem);
		this.signed = singned;
		this.bytesPerItem = bytesPerItem;
	}

	public boolean isSigned() {
		return this.signed;
	}

	/**
	 * Liefert Länge eines Items in Bytes.
	 * 
	 * @return Länge
	 */
	public int getItemLengthInBytes() {
		return this.bytesPerItem;
	}

	@Override
	public void importString(String data) throws DataImportException {
		long longNum;
		if (isSigned()) {
			longNum = Long.parseLong(data);
		} else {
			longNum = Long.parseUnsignedLong(data);
		}
		byte[] bytes = longToReverseByteArray(longNum, this.getItemLengthInBytes());
		setData(bytes);
	}

	@Override
	public String exportString() {
		return Long.toString(bytesToLong(this.getData()));
	}

	// public static final byte[] intToByteArray(int value) {
	// return new byte[] {
	// (byte)(value >>> 24),
	// (byte)(value >>> 16),
	// (byte)(value >>> 8),
	// (byte)value};
	// }

	protected long bytesToLong(byte[] bytes) {
		long res = 0;
		// byte ist signed, falls höchstwertiger byte < 0, dann ist die ganze
		// Zahl negativ
		boolean negativ = (this.isSigned() && bytes[bytes.length - 1] < 0);
		for (int i = 0, n = bytes.length; i < n; i++) {
			// byte ist signed, es soll jedoch als unsigned interpretiert werden
			long unsignedValue = bytes[i] & 0xFF;
			if (negativ) {
				// falls die Zahl negativ sein soll, Zweierkomplement berechnen
				unsignedValue ^= 0xFF;
			}
			res += (unsignedValue << 8 * (i));
		}

		if (negativ) {
			// Zweierkomplement
			res = -res - 1;
		}

		return res;
		// ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		// buffer.put(bytes);
		// buffer.flip(); // need flip
		// return buffer.getLong();
	}

	protected byte[] longToReverseByteArray(long value, int lenghtInBytes) {
		byte[] array = new byte[lenghtInBytes];
		for (int i = 0; i < lenghtInBytes; i++) {
			array[i] = (byte) (value >>> 8 * i);
		}

		return array;
	}
}
