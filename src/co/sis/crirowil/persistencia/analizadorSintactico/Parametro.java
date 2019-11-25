package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.util.Util;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es un Parametro y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Parametro {
	
	/**
	 * Tipo de retorno del parametro
	 */
	private Token retorno;
	
	/**
	 * nombre del parametro
	 */
	private Token nombre;

	/**
	 * Metodo Constructor
	 * @param retorno
	 * @param nombre
	 */
	public Parametro(Token retorno, Token nombre) {
		super();
		this.retorno = retorno;
		this.nombre = nombre;
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

	public TreeItem<String> getArbolVisual() {
		return new TreeItem<String>( nombre.getPalabra()+" : "+retorno.getPalabra() );
	}

	public String obtenerTipo() {
		return retorno.getPalabra();
	}

	public String getJavaCode() {
		String codigo = Util.traducirTipo(retorno.getPalabra()) + " " + nombre.getPalabra();
		return codigo;
		
	}
}
