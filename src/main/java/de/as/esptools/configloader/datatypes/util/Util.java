package de.as.esptools.configloader.datatypes.util;

public final class Util {

	/**
	 * Gibt Inhalt des gegebenen Byte-Arrays als String in einer hexademimalen Darstellung aus.
	 * @param in Eingabe
	 * @return Ausgabe
	 */
	public static final String bytesToHex(byte[] in) {
		final StringBuilder builder = new StringBuilder();
		for (byte b : in) {
			builder.append(String.format("%02x ", b));
		}
		return builder.toString().trim().toUpperCase();
	}
	
	/**
	 * Liefert byteweise Darstellung für den gegebene ganzzahlige Wert.
	 * @param value Ganzzzahl
	 * @param lenghtInBytes Länge der Byte-Darstellung
	 * @return Byte-Array.
	 */
	public static final byte[] longToReverseByteArray(long value, int lenghtInBytes) {
		byte[] array = new byte[lenghtInBytes];
		for (int i = 0; i < lenghtInBytes; i++) {
			array[i] = (byte) (value >>> 8 * i);
		}

		return array;
	}
	
	/**
	 * Überführt byteweise Darstellung in eine (ggf. vorzeichenbehaftete) Ganzzahl.
	 * @param bytes Byte-Array
	 * @param signed gibt an, od die Zahl vorzeichenbehaftet ist
	 * @return Ganzzahl
	 */
	public static final long reverseByteArrayToLong(byte[] bytes, boolean signed) {
		long res = 0;
		// byte ist signed, falls höchstwertiger byte < 0, dann ist die ganze
		// Zahl negativ
		boolean negativ = (signed && bytes[bytes.length - 1] < 0);
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
	}
}
