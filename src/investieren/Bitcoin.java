package investieren;

import java.util.Objects;

// TODO: vereinfachen. Bitcoin nicht von Investition ableiten, sondern die Werte von Investition hier rein.
// TODO: als Uebung dann den Anwender das gleiche fuer Gold machen.

public class Bitcoin extends Investition {

	private static long marktwert;							// Annahme, dass Marktwert für alle Bitcoins gleich
	private static long herstellungskostenAllerBitcoins;
	
	private Bitcoin(long erzeugungskosten) {
		super(erzeugungskosten);
	}

	/**
	 * Wenn der Bitcoin unter Marktwert oder über Marktwert erzeugt wird, gewinnt oder verliert
	 * nur der Erzeuger des Bitcoins.
	 * 
	 * @return 
	 * Zum Marktwert erzeugte CryptoCurrency.
	 */
	static Bitcoin erzeugeZumMarktpreis() {
		herstellungskostenAllerBitcoins += marktwert;
		return new Bitcoin(Objects.requireNonNull(marktwert));
	}

	@Override
	public void setMarktwert( long gehandelterPreis ) {
		marktwert = gehandelterPreis;		
	}

	/**
	 * Zum Initialisieren der Bitcoin-Simulation werden hier die Startwerte gesetzt.
	 * @param startMarktWert
	 * Erzeugungskosten des ersten Bitcoins = erster Marktwert 
	 */
	public static void setzeStartwerte( long startMarktWert ) {
		herstellungskostenAllerBitcoins = 0;
		marktwert = startMarktWert;
	}

	public static Long getMarktwert() {
		return marktwert;
	}

	public static long getHerstellungskostenAllerBitcoins() {
		return herstellungskostenAllerBitcoins;
	}

}
