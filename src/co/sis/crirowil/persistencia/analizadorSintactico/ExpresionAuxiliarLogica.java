package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;

/**
 * 
 * @author Usuario
 *
 */
public class ExpresionAuxiliarLogica {
	
	private Token operadorLogico;
	private ExpresionLogica expresionLogica;
	private ExpresionAuxiliarLogica expresionAuxiliarLogica;
	
	/**
	 * 
	 * @param operadorLogico
	 * @param expresionLogica
	 * @param expresionAuxiliarLogica
	 */
	public ExpresionAuxiliarLogica(Token operadorLogico, ExpresionLogica expresionLogica,
			ExpresionAuxiliarLogica expresionAuxiliarLogica) {
		super();
		this.operadorLogico = operadorLogico;
		this.expresionLogica = expresionLogica;
		this.expresionAuxiliarLogica = expresionAuxiliarLogica;
	}
}
