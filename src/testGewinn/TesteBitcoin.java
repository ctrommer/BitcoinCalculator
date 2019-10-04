package testGewinn;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import investieren.Bitcoin;
import investieren.BitcoinBesitzer;
import investieren.BitcoinHaendler;

public class TesteBitcoin {

	/**
	 * Machen die Bitoin-Besitzer bei steigenden Kursen Gewinne? 
	 * Verlieren sie bei fallenden Kursen?
	 * 
	 * Hier erst mal der einfache Fall: nur ein einziger Bitcoin, zwei Bitcoinbesitzer, die den
	 * Bitcoin zuerst zu immer höheren Preisen handeln. Haben sie dadurch etwas gewonnen?
	 * 
	 * Danach handeln sie zu immer niedrigeren Preisen. Haben sie dadurch etwas verloren?
	 * 
	 * Bei diesem Test werden Steuern, Gebühren, laufende Kosten, Inflation und gesparte Ausgaben nicht berücksichtigt.  
	 * 
	 * Erwartetes Ergebnis: 
	 * Zu jedem Zeitpunkt x ( auch in 1000 Jahren ) machen die Bitcoinbesitzer keinen Gewinn. 
	 * Sie tauschen nur untereinander zu unterschiedlichen Kursen. 
	 */
	@Test
	public void testeKursgewinneZweiInvestorenEinBitcoin() {

		BitcoinBesitzer bitcoinBesitzerA =	new BitcoinBesitzer();
		BitcoinBesitzer bitcoinBesitzerB = new BitcoinBesitzer();
		long preisErsterBitcoin = 1;

		Bitcoin.setzeStartwerte( preisErsterBitcoin );
		bitcoinBesitzerA.stelleZumMarktpreisHer();

		BitcoinHaendler haendler = new BitcoinHaendler();
		for ( long handelspreis = preisErsterBitcoin; handelspreis < 17_000; handelspreis++) {
			haendler.handeln(bitcoinBesitzerA, bitcoinBesitzerB, handelspreis);
			handelspreis++;
			haendler.handeln(bitcoinBesitzerB, bitcoinBesitzerA, handelspreis);
		}
		
		long gewinnAllerInvestoren = bitcoinBesitzerA.ermittleGewinn() + bitcoinBesitzerB.ermittleGewinn();
		assertTrue("Summe der Gewinne aller Investoren muss  0 - Erzeugungskosten sein.", gewinnAllerInvestoren == -Bitcoin.getHerstellungskostenAllerBitcoins() );
		assertTrue("Die Investoren haben die Herstellungskosten des einzigen Bitcoins verloren, also -1.", gewinnAllerInvestoren == -1 );

		for ( long handelspreis = 17_000; handelspreis >= 0; handelspreis--) {
			haendler.handeln(bitcoinBesitzerA, bitcoinBesitzerB, handelspreis);
			handelspreis--;
			haendler.handeln(bitcoinBesitzerB, bitcoinBesitzerA, handelspreis);
		}

		gewinnAllerInvestoren = bitcoinBesitzerA.ermittleGewinn() +  bitcoinBesitzerB.ermittleGewinn() ;
		assertTrue("Summe der Gewinne aller Investoren muss  0 - Erzeugungskosten sein.", gewinnAllerInvestoren == -Bitcoin.getHerstellungskostenAllerBitcoins() );
		assertTrue("Die Investoren haben die Herstellungskosten des einzigen Bitcoins verloren, also -1.", gewinnAllerInvestoren == -1 );
	}

	/**
	 * Wie {@link #testeKursgewinneZweiInvestorenEinBitcoin() }, aber mit mehreren Bitcoins, die zu unterschiedlichen Kursen erzeugt werden.
	 */
	@Test
	public void testeKursgewinneZweiInvestorenMehrereBitcoins() {
		BitcoinBesitzer investorA =	new BitcoinBesitzer();
		BitcoinBesitzer investorB = new BitcoinBesitzer();		
		long preisErsterBitcoin = 1;
		
		Bitcoin.setzeStartwerte( preisErsterBitcoin );
		investorA.stelleZumMarktpreisHer();
		
		BitcoinHaendler haendler = new BitcoinHaendler();
		for ( long handelspreis = preisErsterBitcoin; handelspreis < 17_000; handelspreis++) {
			haendler.handeln(investorA, investorB, handelspreis);
			investorA.stelleZumMarktpreisHer();
			handelspreis++;	
			haendler.handeln(investorB, investorA, handelspreis);
		}
		
		long gewinnAllerInvestoren = investorA.ermittleGewinn() + investorB.ermittleGewinn();
		assertTrue("Summe der Gewinne aller Investoren muss  0 - Erzeugungskosten sein.", gewinnAllerInvestoren == -Bitcoin.getHerstellungskostenAllerBitcoins() );

		for ( long handelspreis = 17_000; handelspreis >= 0; handelspreis--) {
			haendler.handeln(investorA, investorB, handelspreis);
			investorA.stelleZumMarktpreisHer();
			handelspreis--;
			haendler.handeln(investorB, investorA, handelspreis);
		}

		gewinnAllerInvestoren = investorA.ermittleGewinn() +  investorB.ermittleGewinn() ;
		assertTrue("Summe der Gewinne aller Investoren muss  0 - Erzeugungskosten sein.", gewinnAllerInvestoren == -Bitcoin.getHerstellungskostenAllerBitcoins() );		
	}

	/**
	 * Wie {@link #testeKursgewinneZweiInvestorenEinBitcoin() }, aber mit mehreren Bitcoins, die zu unterschiedlichen Kursen erzeugt werden
	 * und mehreren Investoren.
	 */
	@Test
	public void testeKursgewinneMehrereInvestorenMehrereBitcoins() {
		
		List<BitcoinBesitzer> alleInvestoren = new ArrayList<>();

		for ( int investor = 1; investor <= 100; investor++ ) {
			alleInvestoren.add(new BitcoinBesitzer());
		}

		long ersterPreisBitcoin = 1;
		Bitcoin.setzeStartwerte( ersterPreisBitcoin );
		alleInvestoren.get(0).stelleZumMarktpreisHer();

		BitcoinHaendler haendler = new BitcoinHaendler();
		for ( long handelspreis = 1; handelspreis < 17_000; handelspreis++) {

			BitcoinBesitzer kaeufer = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			BitcoinBesitzer verkaeufer = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			haendler.handeln(kaeufer, verkaeufer, handelspreis);

			BitcoinBesitzer herstellerVonNeuemBitcoin = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			herstellerVonNeuemBitcoin.stelleZumMarktpreisHer();
			handelspreis++;	
		}
		
		long gewinnAllerInvestoren = 0;
		for (BitcoinBesitzer investor : alleInvestoren) {
			gewinnAllerInvestoren += investor.ermittleGewinn();
		}

		assertTrue("Summe der Gewinne aller Investoren muss  0 - Erzeugungskosten sein.", gewinnAllerInvestoren == -Bitcoin.getHerstellungskostenAllerBitcoins() );
	}	

	/**
	 * Schütz ein konstanter Bitcoin Preis vor Inflation? 
	 * 
	 * Annahme: Der Bitcoin Wert in Euro bleibt konstant. Sind die Bitcoin-Investoren vor der Inflation geschützt?
	 */
	@Test
	public void testeIstBitcoinBeiKonstantemEuroWertInflationsschutz() {
		
		List<BitcoinBesitzer> alleInvestoren = new ArrayList<>();

		for ( int investor = 1; investor <= 100; investor++ ) {
			alleInvestoren.add(new BitcoinBesitzer());
		}

		final long konstanterPreisBitcoin = 1;
		Bitcoin.setzeStartwerte( konstanterPreisBitcoin );
		alleInvestoren.get(0).stelleZumMarktpreisHer();

		BitcoinHaendler haendler = new BitcoinHaendler();
		for ( long index = 1; index < 17_000; index++) {

			BitcoinBesitzer kaeufer = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			BitcoinBesitzer verkaeufer = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			haendler.handeln(kaeufer, verkaeufer, konstanterPreisBitcoin);

			BitcoinBesitzer herstellerVonNeuemBitcoin = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			herstellerVonNeuemBitcoin.stelleZumMarktpreisHer();
		}
		
		long gewinnAllerInvestoren = 0;
		for (BitcoinBesitzer investor : alleInvestoren) {
			gewinnAllerInvestoren += investor.ermittleGewinn();
		}

		assertTrue("Summe der Gewinne aller Investoren muss  0 - Erzeugungskosten sein.", gewinnAllerInvestoren == -Bitcoin.getHerstellungskostenAllerBitcoins() );
				
	}

	/**
	 * Schütz ein Bitcoin, dessen Wert um die Inflation steigt die Bitcoin-Investoren vor Inflation? 
	 * 
	 * Annahme: Der Bitcoin Wert in Euro steigt mit der Inflation. Sind die Bitcoin-Investoren vor der Inflation geschützt? 
	 * 
	 * Es wird von einer unrealistisch hohen Inflation ausgegangen: Bei jedem Bitcoinhandel ist die Inflation 100 %. 
	 * 
	 */	
	@Test
	public void testeIstBitcoinInflationsschutzWennInEuroGerechnetUmInflationSteigt() {
		List<BitcoinBesitzer> alleInvestoren = new ArrayList<>();

		for ( int investor = 1; investor <= 100; investor++ ) {
			alleInvestoren.add(new BitcoinBesitzer());
		}

		long bitcoinPreisDerUmInflationSteigt = 1;
		long steigerungDurchInflationFaktor = 2;
		
		Bitcoin.setzeStartwerte( bitcoinPreisDerUmInflationSteigt );
		alleInvestoren.get(0).stelleZumMarktpreisHer();

		BitcoinHaendler haendler = new BitcoinHaendler();
		for ( long index = 1; index < 10; index++) {

			BitcoinBesitzer kaeufer = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			BitcoinBesitzer verkaeufer = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			haendler.handeln(kaeufer, verkaeufer, bitcoinPreisDerUmInflationSteigt);

			BitcoinBesitzer herstellerVonNeuemBitcoin = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			herstellerVonNeuemBitcoin.stelleZumMarktpreisHer();
			bitcoinPreisDerUmInflationSteigt*= steigerungDurchInflationFaktor;
		}
		
		long gewinnAllerInvestoren = 0;
		for (BitcoinBesitzer investor : alleInvestoren) {
			gewinnAllerInvestoren += investor.ermittleGewinn();
		}

		assertTrue("Summe der Gewinne aller Investoren muss  0 - Erzeugungskosten sein.", gewinnAllerInvestoren == -Bitcoin.getHerstellungskostenAllerBitcoins() );
		
	}
	
	/**
	 * Was passiert, wenn die Banken hohe Überweisungsgebühren verlangen? Bietet der Bitcoin hier Schutz?
	 * 
	 * Es wird davon ausgegangen, dass der Handelspreis des Bitcoins konstant ist.
	 * 
	 * Auch der Handel mit Bitcoin braucht natürlich Strom, somit fallen auch hier für jede Transaktion Kosten an.
	 */
	@Test
	public void testeSchuetztBitcoinVorUeberweisungsgebuehren() {
		List<BitcoinBesitzer> alleInvestoren = new ArrayList<>();

		for ( int investor = 1; investor <= 100; investor++ ) {
			alleInvestoren.add(new BitcoinBesitzer());
		}

		long ersterPreisBitcoin = 1;
		Bitcoin.setzeStartwerte( ersterPreisBitcoin );
		alleInvestoren.get(0).stelleZumMarktpreisHer();

		BitcoinHaendler haendler = new BitcoinHaendler();
		for ( long handelspreis = 1; handelspreis < 17_000; handelspreis++) {

			BitcoinBesitzer kaeufer = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			BitcoinBesitzer verkaeufer = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			haendler.handeln(kaeufer, verkaeufer, handelspreis);

			BitcoinBesitzer herstellerVonNeuemBitcoin = alleInvestoren.get( ThreadLocalRandom.current().nextInt(0, alleInvestoren.size() ) );
			herstellerVonNeuemBitcoin.stelleZumMarktpreisHer();
		}
		
		long gewinnAllerInvestoren = 0;
		for (BitcoinBesitzer investor : alleInvestoren) {
			gewinnAllerInvestoren += investor.ermittleGewinn();
		}

		assertTrue("Summe der Gewinne aller Investoren muss  0 - Erzeugungskosten sein.", gewinnAllerInvestoren == -Bitcoin.getHerstellungskostenAllerBitcoins() );		
	}
	
}
