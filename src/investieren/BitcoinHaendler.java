package investieren;

public class BitcoinHaendler {

	/**
	 * Ein Bitcoin wechselt den Besitzer.
	 * 
	 * @param verkaeufer
	 * der den Bitcoin verkauft
	 * @param kaeufer
	 * der den Bitcoin kauft
	 * @param preis
	 * der Preis ist der neue Marktpreis
	 */
	public void handeln( BitcoinBesitzer verkaeufer, BitcoinBesitzer kaeufer, long preis  ) {
		
		if ( verkaeufer == kaeufer ) {
			return;
		}
		
		Bitcoin investition = verkaeufer.verkaufeInvestition( preis );
		
		if ( investition != null ) {
			kaeufer.kaufeInvestition(investition, preis);
			investition.setMarktwert(preis);
		}
		
	}
	
	public void bezahlenMitEinemBitcoin( BitcoinBesitzer bezahler, BitcoinBesitzer kassierer, long preis  ) {
		
		if ( bezahler == kassierer ) {
			return;
		}
		
		Bitcoin investition = bezahler.bezahleMitEinemBitcoin( preis );
		
		if ( investition != null ) {
			kassierer.kassiereEinenBitcoin(investition, preis);
			investition.setMarktwert(preis);
		}
		
	}	
	
}
