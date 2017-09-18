package de.as.esptools.configloader.utils;

public final class Util {

	/**
	 * Gibt Inhalt des gegebenen Byte-Arrays als String in einer hexademimalen Darstellung aus.
	 * @param in Eingabe
	 * @return Ausgabe
	 */
	public static String bytesToHex(byte[] in) {
		final StringBuilder builder = new StringBuilder();
		for (byte b : in) {
			builder.append(String.format("%02x ", b));
		}
		return builder.toString().trim().toUpperCase();
	}
	
}
