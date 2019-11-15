package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una sentencias nonais y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Nonais extends Sentencia {
	
	private Condicion condicion;
	
	private BloqueSentencia bloqueSentencia;
	
	

	/**
	 * @param bloqueSentencia
	 */
	public Nonais(Condicion condicion, BloqueSentencia bloqueSentencia) {
		super();
		this.condicion = condicion;
		this.bloqueSentencia = bloqueSentencia;
	}

	
	
	public Condicion getCondicion() {
		return condicion;
	}



	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}



	/**
	 * @return the bloqueSentencia
	 */
	public BloqueSentencia getBloqueSentencia() {
		return bloqueSentencia;
	}

	/**
	 * @param bloqueSentencia the bloqueSentencia to set
	 */
	public void setBloqueSentencia(BloqueSentencia bloqueSentencia) {
		this.bloqueSentencia = bloqueSentencia;
	}

	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<String>("Nonais");

		TreeItem<String> condicion = new TreeItem<String>("Condición");
		raiz.getChildren().add(condicion);
		condicion.getChildren().add(this.condicion.getArbolVisual());

		TreeItem<String> sentencias = new TreeItem<String>("Sentencias");
		raiz.getChildren().add(sentencias);

		for (Sentencia sentencia : bloqueSentencia.getListaSentencias()) {
			sentencias.getChildren().add(sentencia.getArbolVisual());
		}
		
		return raiz;
	}



	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
