package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una sentencias de invocacion y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class SentenciaInvocacion extends Sentencia {
	
	InvocacionFuncion invocacionFuncion;
	
	

	/**
	 * @param invocacionFuncion
	 */
	public SentenciaInvocacion(InvocacionFuncion invocacionFuncion) {
		super();
		this.invocacionFuncion = invocacionFuncion;
	}

	/**
	 * @return the invocacionFuncion
	 */
	public InvocacionFuncion getInvocacionFuncion() {
		return invocacionFuncion;
	}

	/**
	 * @param invocacionFuncion the invocacionFuncion to set
	 */
	public void setInvocacionFuncion(InvocacionFuncion invocacionFuncion) {
		this.invocacionFuncion = invocacionFuncion;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		return invocacionFuncion.getArbolVisual();
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
