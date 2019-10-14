package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

/**
 * Clase que describe que es un bloqueSentencias y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class BloqueSentencia {
	
	/**
	 * lista de sentencias del bloque
	 */
	ArrayList<Sentencia> listaSentencias;

	/**
	 * Constructor principal
	 * @param listaSentencias
	 */
	public BloqueSentencia(ArrayList<Sentencia> listaSentencias) {
		super();
		this.listaSentencias = listaSentencias;
	}

	/**
	 * @return the listaSentencias
	 */
	public ArrayList<Sentencia> getListaSentencias() {
		return listaSentencias;
	}

	/**
	 * @param listaSentencias the listaSentencias to set
	 */
	public void setListaSentencias(ArrayList<Sentencia> listaSentencias) {
		this.listaSentencias = listaSentencias;
	}

	

}
