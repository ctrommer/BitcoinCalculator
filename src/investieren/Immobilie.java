package investieren;

public class Immobilie extends Investition {

	public Immobilie(long erzeugungskosten ) {
		super(erzeugungskosten);
	}

	@Override
	public void setMarktwert(long gehandelterPreis) {
		
		// TODO: der Marktwert sollte genauer ermittelt werden:
		// hier muss man den Handelspreis von vergleichbaren Immobilien in der gleichen Gegend als
		// Grundlage nehmen. Preis pro Quadratmeter ist wohhl ein üblicher Vergleichswert.
		// oder:
		// nur ein einziges Haus? Sollte ja schon reichen, um den Unterschied zu zeigen.
	}

}
