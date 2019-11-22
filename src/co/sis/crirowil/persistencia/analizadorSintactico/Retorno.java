package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una sentencias retorno y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Retorno extends Sentencia
{

	private Token identificador;
	
	private Expresion expresion;
	
	private InvocacionFuncion invocacionFuncion;
	
	
	
	/**
	 * @param invocacionFuncion
	 */
	public Retorno(InvocacionFuncion invocacionFuncion) {
		super();
		this.invocacionFuncion = invocacionFuncion;
	}



	/**
	 * @param expresion
	 */
	public Retorno(Expresion expresion) {
		super();
		this.expresion = expresion;
	}



	/**
	 * @param identificador
	 */
	public Retorno(Token identificador) {
		super();
		this.identificador = identificador;
	}
	
	
	/**
	 * @param NULL
	 */
	public Retorno() {
		super();
	}



	/**
	 * @return the identificador
	 */
	public Token getIdentificador() {
		return identificador;
	}



	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(Token identificador) {
		this.identificador = identificador;
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

		TreeItem<String> raiz = new TreeItem<>("Retorno");
		
		if(identificador != null) {
			raiz.getChildren().add(new TreeItem<>("Identificador: "+identificador.getPalabra()));
		}else if(invocacionFuncion != null) {
			raiz.getChildren().add(invocacionFuncion.getArbolVisual());
		}else if(expresion != null) {
			raiz.getChildren().add(expresion.getArbolVisual());
		}else {
			raiz.getChildren().add(new TreeItem<>("Nada"));
		}
		
		return raiz;
		
	}



	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		return;		
	}



	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		if(identificador != null) {
			Simbolo s = tablaSimbolos.buscarSimboloVariable(identificador.getPalabra(), ambito);
			if(s == null) {
				erroresSemanticos.add("La variable "+identificador.getPalabra()+" no existe.");
			} 
		}else if(expresion != null) {
			expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}else if(invocacionFuncion != null) {
			invocacionFuncion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}
		
	}

}
