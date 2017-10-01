package de.as.esptools.configloader.datatypes;

import java.util.List;

/**
 * Zusammensetzung (Struktur) aus DataItems.
 * 
 * @author Alexander Schulz
 */
public interface IDataStructure {

	/**
	 * F�gt einen neuen Element der Liste hinzu.
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
	 * Importiert den gegebenen String, �berpr�ft die angegebenen Typen und
	 * �berf�hrt Daten in seine interne Darstellung.
	 * 
	 * @param data
	 *            Eingabe-String
	 * @return Restder Zeichenkette, falls weitere Daten enthalten sind
	 * @throws DataImportException
	 *             falls Proble3mebeim Import afgetreten sind
	 */
	public void importTypeAndDataString(String data) throws DataImportException;
}
