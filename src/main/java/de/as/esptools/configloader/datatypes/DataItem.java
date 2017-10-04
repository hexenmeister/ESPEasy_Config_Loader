package de.as.esptools.configloader.datatypes;

import java.util.Arrays;

import de.as.esptools.configloader.datatypes.util.Util;

public abstract class DataItem implements IDataType, Cloneable {

    private byte[] data;

    private int offset = 0;

    private int length = 0;

    private String name;

    private String typeName;

    /**
     * Erstellt ein XDataItem mit eigenem ByteArray in der (für ein Item) notwendigen Länge.
     * 
     * @param typeName Typname
     * @param length Länge der Item-Representation in Bytes
     */
    protected DataItem(String typeName, int length) {
        this.typeName = typeName;
        this.data = new byte[length];
        this.offset = 0;
        this.length = length;
    }

    /**
     * Erstellt ein Item als Referenz auf ein existierendes DataArray.
     * 
     * @param name Typname
     * @param data Referenz auf ein existierendes byteArray (für XArrayItem-Elemente => sie halten selbst die Daten für alle enthaltenen Elemente).
     * @param offset Erste Index im Array für die Bytes
     *
     * @param length Länge der Item-Representation in Bytes
     */
    protected DataItem(String name, byte[] data, int offset, int length) {
        this.typeName = name;
        this.data = data;
        this.offset = offset;
        this.length = length;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return this.typeName;
    }

    static final String EMPTY = "                                                          ";

    static final int INDENT = 18;

    public String exportTypeAndDataString(boolean indent) {
        String name = this.getName();
        name = (name != null ? (" " + name) : "");
        String vname = this.getTypeName() + name;
        String indstr = indent ? EMPTY.substring(0, Math.max(0, INDENT - vname.length())) : "";
        return vname + " :" + indstr + " " + exportDataString();
    }

    protected byte[] getData() {
        if (this.offset == 0 && this.length == this.data.length) {
            return this.data;
        }

        byte[] pData = new byte[this.length];
        System.arraycopy(this.data, this.offset, pData, 0, this.length);
        return pData;
    }

    protected void setData(byte[] bytes) throws DataImportException {
        if (bytes.length == this.length) {
            System.arraycopy(bytes, 0, this.data, this.offset, this.length);
        } else {
            if (bytes.length < this.length) {
                if (this.allowShortDataImport()) {
                    System.arraycopy(bytes, 0, this.data, this.offset, bytes.length);
                    Arrays.fill(this.data, this.offset + bytes.length, this.offset + this.length, (byte) 0);
                } else {
                    throw new DataImportException("data array to short");
                }
            }
            if (bytes.length > this.length) {
                if (this.allowLongDataImport()) {
                    System.arraycopy(bytes, 0, this.data, this.offset, this.length);
                } else {
                    throw new DataImportException("data array to long");
                }
            }
        }
    }

    /**
     * Gibt an, ob es erlaubt sein soll,auch kürzere (als Array-Buffer) Daten zu importieren. In diesem Fall wird der Rest mit Nulen überschrieben.
     * 
     * @return Entscheidung
     */
    protected boolean allowShortDataImport() {
        return false;
    }

    /**
     * Gibt an, ob es erlaubt sein soll,auch längere (als Array-Buffer) Daten zu importieren. In diesem Fall wird der Rest ignoriert.
     * 
     * @return Entscheidung
     */
    protected boolean allowLongDataImport() {
        return false;
    }

    @Override
    public int getBinLength() {
        return this.length;
    }

    @Override
    public void importBin(byte[] data) throws DataImportException {
        if (data.length != this.length) {
            throw new DataImportException("wrong length");
        }
        this.importBin(data, 0);
    }

    @Override
    public void importBin(byte[] data, int offset) throws DataImportException {
        if (data.length < offset + this.offset + this.length) {
            throw new DataImportException("data array to short");
        }
        System.arraycopy(data, offset, this.data, this.offset, this.getBinLength());
    }

    @Override
    public byte[] exportBin() {
        return this.getData();
    }

    @Override
    public final void importHex(String data) throws DataImportException {
        String ret = importHexIntern(data);
        if (ret != null && !this.allowLongDataImport()) {
            throw new DataImportException("import string to long");
        }
    }

    @Override
    public String importHexIntern(String data) throws DataImportException {
        String ret;
        if (this.offset == 0 && this.length == this.data.length) {
            ret = Util.hexToBytes(this.getData(), data, this.allowShortDataImport());
        } else {
            byte[] pData = new byte[this.length];
            ret = Util.hexToBytes(pData, data, this.allowShortDataImport());
            System.arraycopy(pData, 0, this.data, this.offset, this.length);

        }
        return ret;
    }

    @Override
    public String exportHex() {
        return Util.bytesToHex(this.getData());
    }

    @Override
    public final void importDataString(String data) throws DataImportException {
        String rest = this.importDataStringIntern(data);

        if (!this.allowLongDataImport() && rest != null && !rest.trim().isEmpty()) {
            throw new DataImportException("data array to long");
        }
    }

    public abstract String importDataStringIntern(String data) throws DataImportException;

    @Override
    public String exportDataString() {
        return exportDataStringIntern();
    }

    protected abstract String exportDataStringIntern();

    @Override
    public String toString() {
        return this.exportTypeAndDataString(false);
    }

    @Override
    public DataItem clone() throws CloneNotSupportedException {
        DataItem ret = (DataItem) super.clone();
        return ret;
    }

}
