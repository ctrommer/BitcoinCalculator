package investieren;

/**
 * Investieren: Geld hergeben, um in Zukunft ( inflationsbereinigt ) mehr Geld zurück zu bekommen.
 * z.B. X Euro ausgeben, um ein Haus zu bauen, um später Y Euro an Miete einzunehmen. 
 *
 */
public abstract class Investition {
	
	long erzeugungsKosten;	// z.B. Kosten für Hausbau 
	long laufendeKosten;	// z.B. Reparaturen, Versicherungen, Steuern
	long einnahmen;			// z.B. Mieteinnahmen 
	long gesparteAusgaben;	// z.B. bei selbstbewohnter Immobilie 

	public Investition(long erzeugungskosten) {
		this.erzeugungsKosten = erzeugungskosten;
	}

	public abstract void setMarktwert( long gehandelterPreis );

}
