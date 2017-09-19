package de.as.esptools.configloader.datatypes;

public interface IDataType {

	public int getBinLength();

	public void importBin(byte[] data) throws DataImportException;

	public void importBin(byte[] data, int offset) throws DataImportException;

	public void importHex(String data) throws DataImportException;

	public void importString(String data) throws DataImportException;

	public byte[] exportBin();

	public String exportHex();

	public String exportString();

}
