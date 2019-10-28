package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import javafx.scene.control.TreeItem;


/**
 * Clase que describe que es una sentencia de asignacion y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class SentenciaAsignacion extends Sentencia{
	
	private Token nombre;
	private Asignacion asignacion;
	
	
	
	/**
	 * @param nombre
	 * @param asignacion
	 */
	public SentenciaAsignacion(Token nombre, Asignacion asignacion) {
		super();
		this.nombre = nombre;
		this.asignacion = asignacion;
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
	 * @return the asignacion
	 */
	public Asignacion getAsignacion() {
		return asignacion;
	}
	/**
	 * @param asignacion the asignacion to set
	 */
	public void setAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}
	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<String>("Sentencia de Asignacion");
		raiz.getChildren().add(new TreeItem<String>("Variable: " + nombre.getPalabra()));
		raiz.getChildren().add(asignacion.getArbolVisual());
		return raiz;
	}
	
	

}
