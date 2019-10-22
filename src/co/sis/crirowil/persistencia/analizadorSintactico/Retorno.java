package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
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
		// TODO Auto-generated method stub
		return null;
	}

}
