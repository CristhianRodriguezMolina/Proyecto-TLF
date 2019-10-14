package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;

/**
 * Clase que describe que es una funcion y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Funcion {

	/**
	 * Nombre de la funcion
	 */
	private Token nombre;

	/**
	 * Lista de los parametros de una funcion
	 */
	private ArrayList<Parametro> listaParametros;

	/**
	 * Guarda el tipo de retorno de la funcion null es void
	 */
	private Token retorno;

	/**
	 * Lista de setencias ejecuta la funcion
	 */
	private BloqueSentencia bloqueSentencias;

	/**
	 * Contructor de la clase funcion
	 * 
	 * @param nombre
	 * @param listaParametros
	 * @param retorno
	 * @param bloqueSentencias
	 */
	public Funcion(Token nombre, ArrayList<Parametro> listaParametros, Token retorno,
			BloqueSentencia bloqueSentencias) {
		super();
		this.nombre = nombre;
		this.listaParametros = listaParametros;
		this.retorno = retorno;
		this.bloqueSentencias = bloqueSentencias;
	}

	/**
	 * @return the nombre
	 */
	public Token getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(Token nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the listaParametros
	 */
	public ArrayList<Parametro> getListaParametros() {
		return listaParametros;
	}

	/**
	 * @param listaParametros the listaParametros to set
	 */
	public void setListaParametros(ArrayList<Parametro> listaParametros) {
		this.listaParametros = listaParametros;
	}

	/**
	 * @return the retorno
	 */
	public Token getRetorno() {
		return retorno;
	}

	/**
	 * @param retorno the retorno to set
	 */
	public void setRetorno(Token retorno) {
		this.retorno = retorno;
	}

	/**
	 * @return the bloqueSentencias
	 */
	public BloqueSentencia getBloqueSentencias() {
		return bloqueSentencias;
	}

	/**
	 * @param bloqueSentencias the bloqueSentencias to set
	 */
	public void setBloqueSentencias(BloqueSentencia bloqueSentencias) {
		this.bloqueSentencias = bloqueSentencias;
	}
	
	
}
