package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
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
	public TreeItem<String> getArbolVisual() {
		
		if(nombre != null) {
			return new TreeItem<String>("Nombre: "+nombre.getPalabra());
		}else {
			return expresion.getArbolVisual();
		}
		
	}

	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		if(expresion != null) {
			
			expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
			
		}
		
	}
	
	public String getTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		if(nombre != null) {
			Simbolo s = tablaSimbolos.buscarSimboloVariable(nombre.getPalabra(), ambito);
			if(s == null) {
				erroresSemanticos.add("La variable "+nombre.getPalabra()+" no ha sido declarada anteriormente");
			}else {
				return s.getTipo();
			}			
		}else if(expresion != null) {
			return expresion.obtenerTipo();
		}
		
		return "nulo";
		
	}
	
	
}
