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
	 * Importiert den gegebenen String, �berpr�ft die angegebenen Typen und
	 * �berf�hrt Daten in seine interne Darstellung.
	 * 
	 * @param data
	 *            Eingabe-String
	 * @throws DataImportException
	 *             falls Probleme beim Import afgetreten sind
	 */
	public void importTypeAndDataString(String data) throws DataImportException;

	/**
	 * Analysiert den gegebenen String, erstellt ben�tigte Datentypen (f�gt
	 * Items hinzu) und �bernimmt die Daten (Name, Werte) darein.
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
	 * F�llt die Datenstruktur bis zu der gegebenen Adresse auf. Diese Adresse
	 * wird die n�chte freie Adresse.
	 * 
	 * @param toAddress
	 *            Adresse, bis der aufgef�llt werden muss (also die n�chte nach
	 *            dem Filler)
	 */
	public void fillUp(int toAddress) throws DataItemCreationException;
}
