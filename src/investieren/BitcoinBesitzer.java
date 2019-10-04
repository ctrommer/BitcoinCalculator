package investieren;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class BitcoinBesitzer extends Investor<Bitcoin> {

	@Override
	public void stelleZumMarktpreisHer() {
		investitionen.add( Objects.requireNonNull( Bitcoin.erzeugeZumMarktpreis() ));
		ausgaben += Bitcoin.getMarktwert();
	}
		
	/**
	 *
 	 * Beim Bezahlen wird ein Bitcoin hergegeben, dafür nimmt man etwas im Wert des einen Bitcoins ein, z.B. ein Brot.
	 * Der Wert des Brotes wird hier zu den Einnahmen gerechnet, da man ja das Geld, dass man sonst für das Brot
	 * ausgegeben hätte spart. 

	 * @param aktuellerBitcoinKurs
	 * 	zu welchem Kurs der Bitcoin grade gehandelt wird
	 * @return
	 * Bitcoin, mit dem man bezahlt.
	 */
	public Bitcoin bezahleMitEinemBitcoin(long aktuellerBitcoinKurs) {
		if ( investitionen.size() == 0 ) {
			return null;
		}		
		int zufall = ThreadLocalRandom.current().nextInt(0, investitionen.size() );
		
		einnahmen+=aktuellerBitcoinKurs;
		gebuehrenUndSteuernBezahlen();
		
		return investitionen.remove(zufall);		
	}

	/**
	 * Man kassiert einen Bitcoin und gibt dafür etwas her. Daher werden hier die Ausgaben hochgezählt.
	 * 
	 * @param investition
	 * @param aktuellerBitcoinKurs
	 */
	public void kassiereEinenBitcoin(Bitcoin investition, long aktuellerBitcoinKurs) {
		investitionen.add( Objects.requireNonNull(investition ));
		ausgaben += aktuellerBitcoinKurs;
		
		gebuehrenUndSteuernBezahlen();		
	}	

}
