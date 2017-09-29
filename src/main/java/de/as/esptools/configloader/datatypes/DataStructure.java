package de.as.esptools.configloader.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataStructure implements IArrayDataType, IDataStructure {

	private String name;
	private List<DataItem> items;

	public DataStructure(String name) {
		this.name = name;
		this.items = new ArrayList<DataItem>();
	}

	@Override
	public void addItem(DataItem item) {
		this.items.add(item);
	}

	@Override
	public List<DataItem> getItems() {
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
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			ret += it.next().getBinLength();
		}
		return ret;
	}

	@Override
	public void importBin(byte[] data) throws DataImportException {
		this.importBin(data, 0);
	}

	@Override
	public void importBin(byte[] data, int offset) throws DataImportException {
		int pos = offset;
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
			item.importBin(data, pos);
			pos += item.getBinLength();
		}

		if (pos < data.length) {
			throw new DataImportException("import string to long");
		}
	}

	@Override
	public void importHex(String data) throws DataImportException {
		String ret = data;
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
			ret = item.importHexIntern(ret);
		}

		if (ret != null /* && !this.allowLongDataImport() */) {
			throw new DataImportException("import string to long");
		}
	}

	@Override
	public byte[] exportBin() {
		byte[] bytes = new byte[this.getBinLength()];
		int pos = 0;
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
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
		StringBuilder sb = new StringBuilder();
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
			sb.append(item.exportHex());
			if (it.hasNext()) {
				// sb.append(this.getD)
				sb.append(" ");
			}
		}
		// TODO Output format
		return sb.toString();
	}

	@Override
	public void importDataString(String data) throws DataImportException {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public String exportDataString() {
		StringBuilder sb = new StringBuilder();
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
			sb.append(item.exportDataString());
			sb.append(this.getExportDelimeter());
		}

		return sb.toString();
		// XXX
	}

	@Override
	public String exportTypeAndDataString(boolean indent) {
		StringBuilder sb = new StringBuilder();
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
			sb.append(item.exportTypeAndDataString(indent));
			sb.append(this.getExportDelimeter());
		}

		return sb.toString();
	}

	/**
	 * Lieefert Zeichenkette, die als Trenner zw. den Daten einzelnen Items beim
	 * Export fungiert.
	 * 
	 * @return Trenner-String
	 */
	protected String getExportDelimeter() {
		return "\r\n";
	}

	@Override
	public int getArrayLength() {
		// Strukture gleichzeitig als Array verwendbar
		return this.getItemCount();
	}

}
