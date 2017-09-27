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
	public void addItem(IDataType item);

	/**
	 * Liefert Liste der Elemente.
	 * 
	 * @return Liste der Elemente
	 */
	public List<IDataType> getItems();

	/**
	 * Liefern Anzahl der Elemente.
	 * 
	 * @return Anzahl
	 */
	public int getItemCount();

}
