package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es un argumento y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Argumento {

	private Token nombre;
	
	private Expresion expresion;
	
	

	/**
	 * @param nombre
	 */
	public Argumento(Token nombre) {
		super();
		this.nombre = nombre;
	}
	
	

	/**
	 * @param expresion
	 */
	public Argumento(Expresion expresion) {
		super();
		this.expresion = expresion;
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
	 * @return the expresion
	 */
	public Expresion getExpresion() {
		return expresion;
	}

	/**
	 * @param expresion the expresion to set
	 */
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}
	
	/**
	 * Me obtiene una representacion grafica a modo de arbol de como esta compuesta la clase
	 */
	public TreeItem<String> getArbolVisual() 
	{
		TreeItem<String> raiz = new TreeItem<String>("Argumento");
		if(nombre != null) 
		{
			raiz.getChildren().add(new TreeItem<String>("Nombre: " + nombre));
		}
		else 
		{
			TreeItem<String> expresionTree = new TreeItem<String>("Expresion");
			expresionTree.getChildren().add(expresion.getArbolVisual());
			raiz.getChildren().add(expresionTree);
		}
		
		return raiz;
	}
	
}
