package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es un ciclo y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Ciclo extends Sentencia
{
	private DeclaracionVariable declaracionVariable;
	private Condicion condicion;
	private SentenciaAsignacion sentenciaAsignacion;
	private BloqueSentencia bloqueSentencia;
	
	/**
	 * @param asignacion
	 * @param condicion
	 * @param sentenciaAsignacion
	 * @param bloqueSentencia
	 */
	public Ciclo(DeclaracionVariable declaracionVariable, Condicion condicion, SentenciaAsignacion sentenciaAsignacion,
			BloqueSentencia bloqueSentencia) {
		super();
		this.declaracionVariable = declaracionVariable;
		this.condicion = condicion;
		this.sentenciaAsignacion = sentenciaAsignacion;
		this.bloqueSentencia = bloqueSentencia;
		
		for(String s = "cm"; s.length() > 5;) {
			
		}
	}
	
	
	/**
	 * @param condicion
	 * @param bloqueSentencia
	 */
	public Ciclo(Condicion condicion, BloqueSentencia bloqueSentencia) {
		super();
		this.condicion = condicion;
		this.bloqueSentencia = bloqueSentencia;
	}
	
	
	/**
	 * @return the declaracionVariable
	 */
	public DeclaracionVariable getDeclaracionVariable() {
		return declaracionVariable;
	}


	/**
	 * @param declaracionVariable the declaracionVariable to set
	 */
	public void setDeclaracionVariable(DeclaracionVariable declaracionVariable) {
		this.declaracionVariable = declaracionVariable;
	}


	/**
	 * @return the condicion
	 */
	public Condicion getCondicion() {
		return condicion;
	}
	/**
	 * @param condicion the condicion to set
	 */
	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}
	/**
	 * @return the sentenciaAsignacion
	 */
	public SentenciaAsignacion getSentenciaAsignacion() {
		return sentenciaAsignacion;
	}
	/**
	 * @param sentenciaAsignacion the sentenciaAsignacion to set
	 */
	public void setSentenciaAsignacion(SentenciaAsignacion sentenciaAsignacion) {
		this.sentenciaAsignacion = sentenciaAsignacion;
	}
	/**
	 * @return the bloqueSentencia
	 */
	public BloqueSentencia getBloqueSentencia() {
		return bloqueSentencia;
	}
	/**
	 * @param bloqueSentencia the bloqueSentencia to set
	 */
	public void setBloqueSentencia(BloqueSentencia bloqueSentencia) {
		this.bloqueSentencia = bloqueSentencia;
	}


	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<String>("Ciclo");
		if(declaracionVariable != null) 
		{
			raiz.getChildren().add(declaracionVariable.getArbolVisual());
		}
		
		raiz.getChildren().add(condicion.getArbolVisual());
		
		if(sentenciaAsignacion != null) 
		{
			raiz.getChildren().add(sentenciaAsignacion.getArbolVisual());
		}
		
		TreeItem<String> sentencias = new TreeItem<String>("Sentencias");
		raiz.getChildren().add(sentencias);
		for (Sentencia sentencia : bloqueSentencia.getListaSentencias()) {
			sentencias.getChildren().add(sentencia.getArbolVisual());
		}
		return raiz;
	}


	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		tablaSimbolos.guardarSimboloSentencia(this, ambito);
		
		if(declaracionVariable != null) {
			declaracionVariable.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}else if(sentenciaAsignacion != null) {
			sentenciaAsignacion.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));	
		}		
		
		for(Sentencia sentencia: bloqueSentencia.getListaSentencias()) 
		{
			sentencia.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		
	}


	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		if(declaracionVariable != null) {
			declaracionVariable.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));			
		}else if(sentenciaAsignacion != null) {
			sentenciaAsignacion.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));			
		}
		condicion.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		
		for(Sentencia sentencia: bloqueSentencia.getListaSentencias()) 
		{
			sentencia.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		
	}


	@Override
	public String getJavaCode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
