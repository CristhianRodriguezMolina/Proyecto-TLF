package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una sentencias nonas y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Nonas extends Sentencia {
	
	private BloqueSentencia bloqueSentencia;
	
	

	/**
	 * @param bloqueSentencia
	 */
	public Nonas(BloqueSentencia bloqueSentencia) {
		super();
		this.bloqueSentencia = bloqueSentencia;
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

		TreeItem<String> raiz = new TreeItem<String>("Nonas");

		TreeItem<String> sentencias = new TreeItem<String>("Sentencias");
		raiz.getChildren().add(sentencias);

		for (Sentencia sentencia : bloqueSentencia.getListaSentencias()) {
			sentencias.getChildren().add(sentencia.getArbolVisual());
		}
		
		return raiz;
		
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		tablaSimbolos.guardarSimboloSentencia(this, ambito);
		
		for (Sentencia sentencia : bloqueSentencia.getListaSentencias()) {
			sentencia.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		for (Sentencia sentencia : bloqueSentencia.getListaSentencias()) {
			sentencia.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		
	}

	@Override
	public String getJavaCode() {

		String javaCode = "else {\n";
		
		for (Sentencia sentencia : bloqueSentencia.getListaSentencias()) {
			
			javaCode += sentencia.getJavaCode()+"\n";
			
		}
		
		javaCode += "}";
		
		return javaCode;
	}
	
	
	
}
