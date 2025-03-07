package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

public class PorCada extends Sentencia {

	private DeclaracionVariable declaracionVariable;
	private Token lista;
	private BloqueSentencia bloqueSentencia;	

	public PorCada(DeclaracionVariable declaracionVariable, Token lista, BloqueSentencia bloqueSentencia) {
		this.declaracionVariable = declaracionVariable;
		this.lista = lista;
		this.bloqueSentencia = bloqueSentencia;
	}

	public DeclaracionVariable getDeclaracionVariable() {
		return declaracionVariable;
	}

	public void setDeclaracionVariable(DeclaracionVariable declaracionVariable) {
		this.declaracionVariable = declaracionVariable;
	}

	public Token getLista() {
		return lista;
	}

	public void setLista(Token lista) {
		this.lista = lista;
	}

	public BloqueSentencia getBloqueSentencia() {
		return bloqueSentencia;
	}

	public void setBloqueSentencia(BloqueSentencia bloqueSentencia) {
		this.bloqueSentencia = bloqueSentencia;
	}

	@Override
	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<String>("PorCada");
		if(declaracionVariable != null) 
		{
			raiz.getChildren().add(declaracionVariable.getArbolVisual());
		}
		
		raiz.getChildren().add(new TreeItem<String>("Lista : " + lista.getPalabra()));
		
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
		declaracionVariable.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		
		for(Sentencia sentencia: bloqueSentencia.getListaSentencias()) 
		{
			sentencia.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		declaracionVariable.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		
		Simbolo s = tablaSimbolos.buscarSimboloVariable(lista.getPalabra(), ambito);
		if(s == null) {
			erroresSemanticos.add("La variable " + lista.getPalabra() + " no existe."); 
		}else if(s.getArreglo()==null && (s.getExpresion()!=null || s.getInvocacionFuncion()!=null || s.getMapa()!=null || s.getArgumento()!=null)) {
			erroresSemanticos.add("La variable " + lista.getPalabra() + " no es una lista");
		}else if(s.getArgumento()==null && s.getExpresion()==null && s.getInvocacionFuncion()==null && s.getMapa()==null && s.getArreglo()==null) {
			erroresSemanticos.add("La variable " + lista.getPalabra() + " no ha sido declarada anteriormente.");
		}else if(!s.getTipo().equals(declaracionVariable.getTipoRetorno().getPalabra())) {
			erroresSemanticos.add("Tipo incorrecto: No se puede convertir de "+declaracionVariable.getTipoRetorno().getPalabra()+" a " + s.getTipo());
		}
		
		for(Sentencia sentencia: bloqueSentencia.getListaSentencias()) 
		{
			sentencia.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		
	}

	@Override
	public String getJavaCode() {
		String dec = declaracionVariable.getJavaCode();
		String javaCode = "for(" + dec.substring(0, dec.length() - 1) + " : "+lista.getPalabra()+"){\n";
		
		for (Sentencia sentencia : bloqueSentencia.getListaSentencias()) {
			javaCode += sentencia.getJavaCode()+"\n";
		}
		
		javaCode += "}\n";
		
		return javaCode;
	}
	
	

}
