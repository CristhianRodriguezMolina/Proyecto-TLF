package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

/**
 * Clase que describe que es una Unidad de compilacion y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class UnidadDeCompilacion {
	
	/**
	 * Guarda la lista de funciones de la unidad de compilacion
	 */
	private ArrayList<Funcion> listaFunciones;
	
	/**
	 * Constructor
	 * @param listaFunciones
	 */
	public UnidadDeCompilacion(ArrayList<Funcion> listaFunciones) {
		super();
		this.listaFunciones = listaFunciones;
	}

	/**
	 * @return the listaFunciones
	 */
	public ArrayList<Funcion> getListaFunciones() {
		return listaFunciones;
	}

	/**
	 * @param listaFunciones the listaFunciones to set
	 */
	public void setListaFunciones(ArrayList<Funcion> listaFunciones) {
		this.listaFunciones = listaFunciones;
	}
	
	

}
