package de.as.esptools.configloader.datatypes;

public interface IDataType {

	public String getName();

	public void setName(String name);

	public String getTypeName();

	public int getBinLength();

	public void importBin(byte[] data) throws DataImportException;

	public void importBin(byte[] data, int offset) throws DataImportException;

	public void importHex(String data) throws DataImportException;

	public String importHexIntern(String data) throws DataImportException;

	/**
	 * Importiert den gegebenen String und überführt in seine interne
	 * Darstellung.
	 * 
	 * @param data
	 *            Eingabe-String
	 * @return Restder Zeichenkette, falls weitere Daten enthalten sind
	 * @throws DataImportException
	 *             falls Proble3mebeim Import afgetreten sind
	 */
	public void importDataString(String data) throws DataImportException;

	public String importDataStringIntern(String data) throws DataImportException;

	public byte[] exportBin();

	public String exportHex();

	public String exportDataString();

	public String exportTypeAndDataString(boolean indent);

	public IDataType clone() throws CloneNotSupportedException;
	
	// public boolean isInArray();

}
