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

	// /**
	// * Liefert Liste der Elemente.
	// *
	// * @return Liste der Elemente
	// */
	public List<IDataType> getItems();

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
	 * @throws DataImportException
	 *             falls Probleme beim Import afgetreten sind
	 */
	public void importTypeAndDataString(String data) throws DataImportException;

	/**
	 * Analysiert den gegebenen String, erstellt benötigte Datentypen (fügt
	 * Items hinzu) und übernimmt die Daten (Name, Werte) darein.
	 * 
	 * @param data
	 *            Eingabe-String
	 * @return Restder Zeichenkette, falls weitere Daten enthalten sind
	 * @throws DataImportException
	 *             falls Probleme beim Import afgetreten sind
	 */
	public void importTypeAndDataStringCreate(String data) throws DataImportException, DataItemCreationException;

	/**
	 * Importiert Daten aus einer anderen Struktur.
	 * 
	 * @param data
	 *            Strukture mit Daten
	 * @throws DataImportException
	 *             falls Probleme beim Import afgetreten sind
	 */
	public void importStructure(IDataStructure data) throws DataImportException;

	/**
	 * Füllt die Datenstruktur bis zu der gegebenen Adresse auf. Diese Adresse
	 * wird die nächte freie Adresse.
	 * 
	 * @param toAddress
	 *            Adresse, bis der aufgefüllt werden muss (also die nächte nach
	 *            dem Filler)
	 */
	public void fillUp(int toAddress) throws DataItemCreationException;
}
