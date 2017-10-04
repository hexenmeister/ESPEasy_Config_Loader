package de.as.esptools.configloader.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.as.esptools.configloader.datatypes.util.Util;

public class DataStructure implements IDataStructure, IDataType, Cloneable {

    private String name;

    private String typeName;

    private List<IDataType> items;

    public DataStructure(String name) {
        this.typeName = name;
        this.items = new ArrayList<IDataType>();
    }

    @Override
    public void addItem(IDataType item) {
        this.items.add(item);
    }

//    @Override
    public List<IDataType> getItems() {
        return this.items;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTypeName() {
        return this.typeName;
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
        this.importBin(data, 0);
    }

    @Override
    public void importBin(byte[] data, int offset) throws DataImportException {
        int pos = offset;
        List<IDataType> l = this.getItems();
        for (Iterator<IDataType> it = l.iterator(); it.hasNext();) {
            IDataType item = it.next();
            item.importBin(data, pos);
            pos += item.getBinLength();
        }

        if (pos < data.length) {
            throw new DataImportException("import string to long");
        }
    }

    @Override
    public final void importHex(String data) throws DataImportException {
        String ret = importHexIntern(data);
        if (ret != null /* && !this.allowLongDataImport() */) {
            throw new DataImportException("import string to long");
        }
    }

    @Override
    public String importHexIntern(String data) throws DataImportException {
        String ret = data;
        List<IDataType> l = this.getItems();
        for (Iterator<IDataType> it = l.iterator(); it.hasNext();) {
            IDataType item = it.next();
            ret = item.importHexIntern(ret);
        }

        return ret;
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

    // XXX: Stream Im/Export?

    @Override
    public String exportHex() {
        StringBuilder sb = new StringBuilder();
        List<IDataType> l = this.getItems();
        for (Iterator<IDataType> it = l.iterator(); it.hasNext();) {
            IDataType item = it.next();
            String export = item.exportHex();
            if (!export.isEmpty()) {
                sb.append(export);
                if (it.hasNext()) {
                    // sb.append(this.getDelimeter)
                    sb.append(" ");
                }
            }
        }
        // TODO Output format: Blockweise, 16 Hex-Zahlen pro Zeile
        return sb.toString();
    }

    @Override
    public void importDataString(String data) throws DataImportException {
        String ret = this.importDataStringIntern(data);

        if (ret != null /* && !this.allowLongDataImport() */) {
            throw new DataImportException("import string to long");
        }
    }

    @Override
    public String importDataStringIntern(String data) throws DataImportException {
        String ret = data;
        List<IDataType> l = this.getItems();
        for (Iterator<IDataType> it = l.iterator(); it.hasNext();) {
            IDataType item = it.next();
            ret = item.importDataStringIntern(ret);
            if (ret != null) {
                ret = ret.trim();
            }
        }

        return ret;
    }

    @Override
    public void importTypeAndDataString(String data) throws DataImportException {
        String ret = data;
        List<IDataType> l = this.getItems();
        for (Iterator<IDataType> it = l.iterator(); it.hasNext();) {
            IDataType item = it.next();
            int posd = ret.indexOf(":");
            if (posd < 0) {
                throw new DataImportException("no typefound. expected type: " + item.getTypeName());
            }
            String type = ret.substring(0, posd).trim();
            String name = "";
            int posn = type.indexOf(' ');
            if (posn > 0) {
                name = type.substring(posn).trim();
                type = type.substring(0, posn);
            }
            if (!type.equalsIgnoreCase(item.getTypeName())) {
                throw new DataImportException("unexpected type " + type + ".expected: " + item.getTypeName());
            }
            item.setName(name);
            ret = ret.substring(posd + 1);
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
    public void importTypeAndDataStringCreate(String data) throws DataImportException {
        // TODO implement
        
    }

    @Override
    public String exportDataString() {
        StringBuilder sb = new StringBuilder();
        List<IDataType> l = this.getItems();
        for (Iterator<IDataType> it = l.iterator(); it.hasNext();) {
            IDataType item = it.next();
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
        List<IDataType> l = this.getItems();
        for (Iterator<IDataType> it = l.iterator(); it.hasNext();) {
            IDataType item = it.next();
            sb.append(item.exportTypeAndDataString(indent));
            if (it.hasNext()) {
                sb.append(this.getExportDelimeter());
            }
        }

        return sb.toString();
    }

    /**
     * Lieefert Zeichenkette, die als Trenner zw. den Daten einzelnen Items beim Export fungiert.
     * 
     * @return Trenner-String
     */
    protected String getExportDelimeter() {
        return "\r\n";
    }

    @Override
    public String toString() {
        return this.exportTypeAndDataString(true);
        // return this.getTypeName();
    }

    @Override
    public void importStructure(IDataStructure data) throws DataImportException {
        // TODO import Structure
        // Elemente nach deren Namen zuordnen und Daten übernehmen. Unbenannte Elemente werden nicht berücksichtigt.
    }

    @Override
    public void fillUp(int toAddress) throws DataItemCreationException {
        int cAddr = this.getBinLength();
        int fLength = toAddress - cAddr;
        if (fLength < 0) {
            throw new DataItemCreationException("can not add filler: address to low. next possible: " + cAddr);
        }
        this.addItem(new FillUp(cAddr, toAddress));
    }

    protected static class FillUp extends DataItem {
        @SuppressWarnings("unused")
        private int address;

        private int toAddress;

        public FillUp(int cAddr, int toAddr) {
            super("fillup", toAddr - cAddr);
            this.address = cAddr;
            this.toAddress = toAddr;
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public String exportDataStringIntern() {
            return Integer.toString(this.toAddress);
        }

        @Override
        public String importDataStringIntern(String data) throws DataImportException {
            String token = data.trim();
            String rest = null;

            int pos = Util.searchTokenSplitPosition(token, " \t\r\n\f");
            if (pos > 0) {
                rest = token.substring(pos);
                token = token.substring(0, pos);
            }

            // ignore data, return rest
            // this.importHex(token);

            return rest;
        }
    }

    @Override
    public DataStructure clone() throws CloneNotSupportedException {
        DataStructure ret = (DataStructure) super.clone();
        ret.items = new ArrayList<IDataType>();
        for (Iterator<IDataType> it = this.items.iterator(); it.hasNext();) {
            IDataType item = (IDataType) it.next();
            ret.items.add(item.clone());
        }
        return ret;
    }


}
