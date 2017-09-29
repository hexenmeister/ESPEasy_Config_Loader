package de.as.esptools.configloader.datatypes.util;

import de.as.esptools.configloader.datatypes.DataImportException;

public final class Util {

	/**
	 * Gibt Inhalt des gegebenen Byte-Arrays als String in einer hexademimalen
	 * Darstellung aus.
	 * 
	 * @param in
	 *            Eingabe
	 * @return Ausgabe
	 */
	public static final String bytesToHex(byte[] in) {
		return bytesToHex(in, 0, null);
	}

	/**
	 * Gibt Inhalt des gegebenen Byte-Arrays als String in einer hexademimalen
	 * Darstellung aus.
	 * 
	 * @param in
	 *            Eingabe
	 * @param bytePerLine
	 *            falls >0, wird nach dieser Anzahl der verarbeiteten Bytes ein
	 *            Delimeten eingefügt (i.d.R. eine neue Zeile)
	 * @param delimeter
	 *            Line-Delimeter
	 * @return Ausgabe
	 */
	public static final String bytesToHex(byte[] in, int bytePerLine, String delimeter) {
		final StringBuilder builder = new StringBuilder();
		int cnt = 0;
		for (byte b : in) {
			builder.append(String.format("%02x ", b));
			cnt++;
			if (delimeter != null && bytePerLine >= 0 && cnt % bytePerLine == 0) {
				builder.append(delimeter);
			}
		}
		return builder.toString().trim().toUpperCase();
	}

	/**
	 * Überführt hexadecimale Darstellung in ein Byte-Array.
	 * 
	 * @param bytes
	 *            Ziel-Array
	 * @param data
	 *            Input-String
	 * @param allowShort
	 *            gibt an, ob zu kurze Daten erlaub sein sollen (Rest wird mit
	 *            Nulen gefüllt)
	 * @throws DataImportException
	 *             Zeigt Import/Interpretationsprobleme an
	 * @return Rest, der nach dem Import übrig blieb
	 */
	public static final String hexToBytes(byte[] bytes, String data, boolean allowShort) throws DataImportException {
		StringTokenizerEx st = new StringTokenizerEx(data);
		int cnt = 0;
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			// TODO (Verbesserung):
			// Wenn die Länge!=2, dann in 2-Zeichen Strings aufteilen, wenn
			// nicht teilbar => Fehler
			if (cnt >= bytes.length) {
				return s + st.getRemainingString();
				// StringBuilder sb = new StringBuilder(s);
				// while (st.hasMoreTokens()) {
				// sb.append(" ");
				// sb.append(st.nextToken());
				// }
				// return sb.toString();

				// if (allowLong) {
				// break;
				// } else {
				// throw new DataImportException("import string to long");
				// }
			}
			int val;
			try {
				val = Integer.parseInt(s, 16);
			} catch (NumberFormatException e) {
				throw new DataImportException("invalid input. data must be a hex number");
			}
			if (val > 255 || val < 0) {
				throw new DataImportException("invalid inpit. data must be in range from 00 to FF");
			}
			bytes[cnt++] = (byte) val;
		}
		if (allowShort) {
			for (int i = cnt, n = bytes.length; i < n; i++) {
				bytes[i] = 0;
			}
		} else if (cnt < bytes.length) {
			throw new DataImportException("import string to short");
		}

		return null;
	}

	/**
	 * Liefert byteweise Darstellung für den gegebene ganzzahlige Wert.
	 * 
	 * @param value
	 *            Ganzzzahl
	 * @param lenghtInBytes
	 *            Länge der Byte-Darstellung
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
	 * Überführt byteweise Darstellung in eine (ggf. vorzeichenbehaftete)
	 * Ganzzahl.
	 * 
	 * @param bytes
	 *            Byte-Array
	 * @param signed
	 *            gibt an, od die Zahl vorzeichenbehaftet ist
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

	/**
	 * Erstellt eine float-Zahl aus ihrer Byte-Representaion.
	 * 
	 * @param bytes
	 *            ByteArray
	 * @return float-Wert
	 */
	public static final float byteArrayToFloat(byte[] bytes) {
		java.nio.ByteBuffer buf = java.nio.ByteBuffer.allocate(4);
		buf.put(bytes);
		return buf.getFloat(0);
	}

	/**
	 * Liefert Byte-Representation einer float-Zahl.
	 * 
	 * @param f
	 *            float-Wert.
	 * @return ByteArray
	 */
	public static final byte[] floatToByteArray(float f) {
		java.nio.ByteBuffer buf = java.nio.ByteBuffer.allocate(Float.BYTES);
		buf.putFloat(f);
		byte[] bytes = buf.array();
		return bytes;
	}

	/**
	 * Sucht die erste Position eines Delimeters (irgendein Zeichen aus delim).
	 * 
	 * @param data
	 *            String, in dem gesucht wird
	 * @param delim
	 *            Trennerliste
	 * @return Position oder -1
	 */
	public static int searchTokenSplitPosition(String data, String delim) {
		return searchTokenSplitPosition(data, delim, 1);
	}

	/**
	 * Sucht die N-te (occur) Position eines Delimeters (irgendein Zeichen aus
	 * delim).
	 * 
	 * @param data
	 *            String, in dem gesucht wird
	 * @param delim
	 *            Trennerliste
	 * @param occur
	 *            Gesuchtes Vorkommen
	 * @return Position oder -1
	 */
	public static int searchTokenSplitPosition(String data, String delim, int occur) {
		int ret = -1;
		for (int i = 0, n = data.length(); i < n; i++) {
			if (delim.indexOf(data.charAt(i)) >= 0) {
				occur--;
				if (occur < 1) {
					return i;
				}
			}
		}
		return ret;
	}
}
