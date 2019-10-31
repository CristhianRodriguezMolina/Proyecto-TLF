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

	private Argumento argumento;

	private InvocacionFuncion invocacionFuncion;

	private Arreglo arreglo;

	private Mapa mapa;

	/**
	 * @param operadorAsignacion
	 */
	public Asignacion(Token operadorAsignacion) {
		super();
		this.operadorAsignacion = operadorAsignacion;
	}
	
	/**
	 * @param operadorAsignacion
	 * @param expresion
	 */
	public Asignacion(Token operadorAsignacion, Argumento argumento) {
		super();
		this.operadorAsignacion = operadorAsignacion;
		this.argumento = argumento;
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

	public Asignacion(Token operadorAsignacion, Arreglo arreglo) {
		super();
		this.operadorAsignacion = operadorAsignacion;
		this.arreglo = arreglo;
	}

	public Asignacion(Token operadorAsignacion, Mapa mapa) {
		super();
		this.operadorAsignacion = operadorAsignacion;
		this.mapa = mapa;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
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
	 * @return the argumento
	 */
	public Argumento getArgumento() {
		return argumento;
	}

	/**
	 * @param argumento the argumento to set
	 */
	public void setArgumento(Argumento argumento) {
		this.argumento = argumento;
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

	/**
	 * @return the arreglo
	 */
	public Arreglo getArreglo() {
		return arreglo;
	}

	/**
	 * @param arreglo the arreglo to set
	 */
	public void setArreglo(Arreglo arreglo) {
		this.arreglo = arreglo;
	}

	public TreeItem<String> getArbolVisual() {
		
		TreeItem<String> raiz = new TreeItem<>("Asignación");
		
		raiz.getChildren().add(new TreeItem<>("Operador de asignacion: " + operadorAsignacion.getPalabra()));
		
		if(argumento != null) {
			raiz.getChildren().add(argumento.getArbolVisual());
		}else if (invocacionFuncion != null){
			raiz.getChildren().add(invocacionFuncion.getArbolVisual());
		}else if(arreglo != null) {
			raiz.getChildren().add(arreglo.getArbolVisual());
		}else if(mapa != null) {
			raiz.getChildren().add(mapa.getArbolVisual());
		}
		
		return raiz;
		
	}
}
