package co.sis.crirowil.persistencia.analizadorSintactico;

import javafx.scene.control.TreeItem;

public abstract class Expresion {

	public abstract TreeItem<String> getArbolVisual();

	/**
	 * Metodo que me permite obtener el tipo de la expresion
	 * @return
	 */
	public abstract String obtenerTipo();
	
	
	
}
