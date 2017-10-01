package de.as.esptools.configloader.datatypes;

import java.util.List;

/**
 * Zusammensetzung (Struktur) aus DataItems.
 * 
 * @author Alexander Schulz
 */
public interface IDataStructure {

	/**
	 * Fügt einen neuen Element der Liste hinzu.
	 * 
	 * @param item
	 *            Element
	 */
	public void addItem(DataItem item);

	/**
	 * Liefert Liste der Elemente.
	 * 
	 * @return Liste der Elemente
	 */
	public List<DataItem> getItems();

	/**
	 * Liefern Anzahl der Elemente.
	 * 
	 * @return Anzahl
	 */
	public int getItemCount();

	/**
	 * Importiert den gegebenen String, überprüft die angegebenen Typen und
	 * überführt Daten in seine interne Darstellung.
	 * 
	 * @param data
	 *            Eingabe-String
	 * @return Restder Zeichenkette, falls weitere Daten enthalten sind
	 * @throws DataImportException
	 *             falls Proble3mebeim Import afgetreten sind
	 */
	public void importTypeAndDataString(String data) throws DataImportException;
}
