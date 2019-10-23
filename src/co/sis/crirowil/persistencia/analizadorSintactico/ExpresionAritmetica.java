package co.sis.crirowil.persistencia.analizadorSintactico;

import javafx.scene.control.TreeItem;

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
	
	@Override
	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<String>("Expresion Aritmetica");
		
		raiz.getChildren().add(new TreeItem<String>("Valor numerico: " + valorNumerico.toString()));
		
		raiz.getChildren().add(getExpresionAritmetica().getArbolVisual());
		
		raiz.getChildren().add(getExpresionAuxiliar().getArbolVisual());
		
		return raiz;
		
	}
	
	
	
	
}
