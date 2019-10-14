package co.sis.crirowil.persistencia.analizadorSintactico;

/**
 * Clase que describe que es una sentencias ExpresionAritmetica y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class ExpresionAritmetica extends Expresion
{
	private ExpresionAritmetica expresionAritmetica;
	private ExpresionAuxiliar expresionAuxiliar;
	private ValorNumerico valorNumerico;
	/**
	 * @param expresionAritmetica
	 * @param expresionAuxiliar
	 */
	public ExpresionAritmetica(ExpresionAritmetica expresionAritmetica, ExpresionAuxiliar expresionAuxiliar) {
		super();
		this.expresionAritmetica = expresionAritmetica;
		this.expresionAuxiliar = expresionAuxiliar;
	}
	/**
	 * @param expresionAritmetica
	 * @param valorNumerico
	 */
	public ExpresionAritmetica(ValorNumerico valorNumerico, ExpresionAritmetica expresionAritmetica) {
		super();
		this.expresionAritmetica = expresionAritmetica;
		this.valorNumerico = valorNumerico;
	}
	/**
	 * @return the expresionAritmetica
	 */
	public ExpresionAritmetica getExpresionAritmetica() {
		return expresionAritmetica;
	}
	/**
	 * @param expresionAritmetica the expresionAritmetica to set
	 */
	public void setExpresionAritmetica(ExpresionAritmetica expresionAritmetica) {
		this.expresionAritmetica = expresionAritmetica;
	}
	/**
	 * @return the expresionAuxiliar
	 */
	public ExpresionAuxiliar getExpresionAuxiliar() {
		return expresionAuxiliar;
	}
	/**
	 * @param expresionAuxiliar the expresionAuxiliar to set
	 */
	public void setExpresionAuxiliar(ExpresionAuxiliar expresionAuxiliar) {
		this.expresionAuxiliar = expresionAuxiliar;
	}
	/**
	 * @return the valorNumerico
	 */
	public ValorNumerico getValorNumerico() {
		return valorNumerico;
	}
	/**
	 * @param valorNumerico the valorNumerico to set
	 */
	public void setValorNumerico(ValorNumerico valorNumerico) {
		this.valorNumerico = valorNumerico;
	}
	
	
	
	
}
