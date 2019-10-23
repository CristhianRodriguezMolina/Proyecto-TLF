package co.sis.crirowil.persistencia.analizadorSintactico;

import javafx.scene.control.TreeItem;

public class Condicion {

	/**
	 * Expresion de la condicion
	 */
	private Expresion expresion;
	
	public Condicion(Expresion expresion) {
		this.expresion = expresion;
	}
	
	/**
	 * 
	 * @return
	 */
	public Expresion getExpresion() {
		return expresion;
	}

	/**
	 * 
	 * @param expresion
	 */
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}



	public TreeItem<String> getArbolVisual() {

		return expresion.getArbolVisual();
		
	}

}
