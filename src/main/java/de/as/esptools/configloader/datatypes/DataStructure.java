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

	// XXX: Stream Im/Export?

	@Override
	public String exportHex() {
		StringBuilder sb = new StringBuilder();
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
			sb.append(item.exportHex());
			if (it.hasNext()) {
				// sb.append(this.getDelimeter)
				sb.append(" ");
			}
		}
		// TODO Output format: Blockweise, 16 Hex-Zahlen pro Zeile
		return sb.toString();
	}

	@Override
	public void importDataString(String data) throws DataImportException {
		String ret = data;
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
			ret = item.importDataStringIntern(ret);
			if (ret != null) {
				ret = ret.trim();
			}
		}

		if (ret != null /* && !this.allowLongDataImport() */) {
			throw new DataImportException("import string to long");
		}
	}

	@Override
	public void importTypeAndDataString(String data) throws DataImportException {
		String ret = data;
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
			int posd = ret.indexOf(":");
			// int pos = ret.indexOf(item.getTypeName());
			// if (pos < 0 || pos > posd) {
			// String addInfo = "";
			// if (posd >= 0) {
			// addInfo = " : " + ret.substring(0, posd);
			// }
			// throw new DataImportException("unexpected type" + addInfo +
			// ".expected: " + item.getTypeName());
			// }
			if (posd < 0) {
				throw new DataImportException("no typefound. expected type: " + item.getTypeName());
			}
			String type = ret.substring(0, posd).trim();
			if (!type.equalsIgnoreCase(item.getTypeName())) {
				throw new DataImportException("unexpected type " + type + ".expected: " + item.getTypeName());
			}
			ret = ret.substring(posd + 1);
			ret = item.importDataStringIntern(ret);
			if (ret != null) {
				ret = ret.trim();
			}
		}
		// TODO: prüfen
		if (ret != null /* && !this.allowLongDataImport() */) {
			throw new DataImportException("import string to long");
		}
	}

	@Override
	public String exportDataString() {
		StringBuilder sb = new StringBuilder();
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
			sb.append(item.exportDataString());
			if (it.hasNext()) {
				sb.append(this.getExportDelimeter());
			}
		}

		return sb.toString();
	}

	@Override
	public String exportTypeAndDataString(boolean indent) {
		StringBuilder sb = new StringBuilder();
		List<DataItem> l = this.getItems();
		for (Iterator<DataItem> it = l.iterator(); it.hasNext();) {
			DataItem item = it.next();
			sb.append(item.exportTypeAndDataString(indent));
			if (it.hasNext()) {
				sb.append(this.getExportDelimeter());
			}
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
