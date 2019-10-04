package investieren;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Investor<T extends Investition> {
	
	List<T> investitionen = new ArrayList<>();
	
	long ausgaben;
	long einnahmen;
	
	long steuernFuerEinenHandel;
	long gebuehrenFuerEinenHandel;
	
	public Investor() {
	}

	public Investor(long steuernFuerEinenHandel, long gebuehrenFuerEinenHandel) {
		super();
		this.steuernFuerEinenHandel = steuernFuerEinenHandel;
		this.gebuehrenFuerEinenHandel = gebuehrenFuerEinenHandel;
	}

	protected void gebuehrenUndSteuernBezahlen() {
		ausgaben+=steuernFuerEinenHandel;
		ausgaben+=gebuehrenFuerEinenHandel;
	}

	public abstract void stelleZumMarktpreisHer();

	public void kaufeInvestition( T investition, long kaufpreis ) {
		investitionen.add( Objects.requireNonNull(investition ));
		ausgaben += kaufpreis;
		
		gebuehrenUndSteuernBezahlen();
	}

	public T verkaufeInvestition( long verkaufspreis ) {
		if ( investitionen.size() == 0 ) {
			return null;
		}		
		int zufall = ThreadLocalRandom.current().nextInt(0, investitionen.size() );
		
		einnahmen+=verkaufspreis;
		gebuehrenUndSteuernBezahlen();
		
		return investitionen.remove(zufall);		
	}
	
	public long ermittleGewinn() {
		return einnahmen-ausgaben;
	}

}
