package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
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

		TreeItem<String> raiz = new TreeItem<String>("Condicion");
		raiz.getChildren().add(expresion.getArbolVisual());
		return raiz;
		
	}
	
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, null, true);
	}

}
