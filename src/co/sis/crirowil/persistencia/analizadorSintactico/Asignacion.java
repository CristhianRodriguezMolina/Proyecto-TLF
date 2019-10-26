package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una asignacion y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Asignacion {

	private Token operadorAsignacion;

	private Expresion expresion;

	private InvocacionFuncion invocacionFuncion;

	/**
	 * @param operadorAsignacion
	 * @param expresion
	 */
	public Asignacion(Token operadorAsignacion, Expresion expresion) {
		super();
		this.operadorAsignacion = operadorAsignacion;
		this.expresion = expresion;
	}

	/**
	 * @param operadorAsignacion
	 * @param invocacionFuncion
	 */
	public Asignacion(Token operadorAsignacion, InvocacionFuncion invocacionFuncion) {
		super();
		this.operadorAsignacion = operadorAsignacion;
		this.invocacionFuncion = invocacionFuncion;
	}

	/**
	 * @return the operadorAsignacion
	 */
	public Token getOperadorAsignacion() {
		return operadorAsignacion;
	}

	/**
	 * @param operadorAsignacion the operadorAsignacion to set
	 */
	public void setOperadorAsignacion(Token operadorAsignacion) {
		this.operadorAsignacion = operadorAsignacion;
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



	public TreeItem<String> getArbolVisual() {
		
		TreeItem<String> raiz = new TreeItem<>("Asignaci�n");
		
		raiz.getChildren().add(new TreeItem<>("Operador de asignacion: " + operadorAsignacion.getPalabra()));
		
		if(expresion != null) {
			raiz.getChildren().add(expresion.getArbolVisual());
		}else {
			raiz.getChildren().add(invocacionFuncion.getArbolVisual());
		}
		
		return raiz;
		
	}
}
