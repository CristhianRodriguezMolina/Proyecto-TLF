package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.JOptionPane;

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

	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito, String identificador, boolean declaracion) {
		if(nombre != null) {
			
			Simbolo s = tablaSimbolos.buscarSimboloVariable(nombre.getPalabra(), ambito);
			Simbolo iden = tablaSimbolos.buscarSimboloVariable(identificador, ambito);
			if(s == null) {
				erroresSemanticos.add("La variable "+nombre.getPalabra()+" no existe.");
			}
			else 
			{
				if(!s.getTipo().equals(iden.getTipo())) 
				{
					erroresSemanticos.add("Tipo incorrecto: No se puede convertir de " + s.getTipo() + " a " + iden.getTipo());
				}
			}
			
		}else if(expresion != null) {
			
			expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, identificador, declaracion);
			
		}
		
	}
	
	public String getTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		if(nombre != null) {
			Simbolo s = tablaSimbolos.buscarSimboloVariable(nombre.getPalabra(), ambito);
			if(s == null) {
				erroresSemanticos.add("La variable "+nombre.getPalabra()+" no ha sido declarada anteriormente" + " en el ambito de " + ambito.getNombre());
			}else {
				if(nombre.getColumna()>s.getColumna() && nombre.getFila()>s.getFila()) {
					return s.getTipo();					
				}else {
					erroresSemanticos.add("La variable "+nombre.getPalabra()+" no ha sido declarada anteriormente" + " en el ambito de " + ambito.getNombre());
				}
			}			
		}else if(expresion != null) {
			return expresion.obtenerTipo(tablaSimbolos, erroresSemanticos, ambito);
		}
		
		return "nulo";
		
	}
	
	
}
