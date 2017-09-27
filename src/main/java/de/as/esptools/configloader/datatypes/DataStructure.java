package de.as.esptools.configloader.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataStructure implements IArrayDataType, IDataStructure {

	private String name;
	private List<IDataType> items;

	public DataStructure(String name) {
		this.name = name;
		this.items = new ArrayList<IDataType>();
	}

	@Override
	public void addItem(IDataType item) {
		this.items.add(item);
	}

	@Override
	public List<IDataType> getItems() {
		return this.items;
	}

	@Override
	public int getItemCount() {
		return this.items.size();
	}

	@Override
	public String getTypeName() {
		return this.name;
	}

	@Override
	public int getBinLength() {
		int ret = 0;
		List<IDataType> l = this.getItems();
		for (Iterator<IDataType> it = l.iterator(); it.hasNext();) {
			ret += it.next().getBinLength();
		}
		return ret;
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
		byte[] bytes = new byte[this.getBinLength()];
		int pos = 0;
		List<IDataType> l = this.getItems();
		for (Iterator<IDataType> it = l.iterator(); it.hasNext();) {
			IDataType item = it.next();
			byte[] itemBytes = item.exportBin();
			int itemLength = item.getBinLength();
			if (itemBytes.length != itemLength) {
				throw new RuntimeException("internal error: wrong item array length");
			}
			System.arraycopy(itemBytes, 0, bytes, pos, itemLength);
			pos += itemLength;
		}
		return bytes;
	}

	// TODO: Stream Im/Export?

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
		// Strukture gleichzeitig als Array verwendbar
		return this.getItemCount();
	}

	// TODO implement

}
