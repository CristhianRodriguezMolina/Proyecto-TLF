package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;

/**
 * Clase que describe que es una sentencias valorNumerico y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class ValorNumerico {

	private Token signo;
	private Token termino;
	/**
	 * @param signo
	 * @param termino
	 */
	public ValorNumerico(Token signo, Token termino) {
		super();
		this.signo = signo;
		this.termino = termino;
	}
	/**
	 * @return the signo
	 */
	public Token getSigno() {
		return signo;
	}
	/**
	 * @param signo the signo to set
	 */
	public void setSigno(Token signo) {
		this.signo = signo;
	}
	/**
	 * @return the termino
	 */
	public Token getTermino() {
		return termino;
	}
	/**
	 * @param termino the termino to set
	 */
	public void setTermino(Token termino) {
		this.termino = termino;
	}
	
	@Override
	public String toString() {
		return signo + "" + termino;
	}
	
	
	
	
}
