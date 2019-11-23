package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

public abstract class Expresion {

	public abstract TreeItem<String> getArbolVisual();

	/**
	 * Metodo que me permite obtener el tipo de la expresion
	 * @return
	 */
	public abstract String obtenerTipo();

	public abstract void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos,
			Simbolo ambito, String identificador, boolean relacional);
	
	
	
}
