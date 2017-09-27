package de.as.esptools.configloader.datatypes;

public class DataStructure implements IArrayDataType{

	public DataStructure() {

	}

	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBinLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void importBin(byte[] data) throws DataImportException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importBin(byte[] data, int offset) throws DataImportException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importHex(String data) throws DataImportException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String importDataString(String data) throws DataImportException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] exportBin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String exportHex() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String exportDataString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String exportTypeAndDataString(boolean indent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getArrayLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	// TODO implement
	
}
