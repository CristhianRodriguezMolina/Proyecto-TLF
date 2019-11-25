package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

public class Switch extends Sentencia {

	private ArrayList<Caso> listaCasos;
	private Expresion expresion;
	
	public Switch(Expresion expresion, ArrayList<Caso> listaCasos) {
		this.expresion = expresion;
		this.listaCasos = listaCasos;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public ArrayList<Caso> getListaCasos() {
		return listaCasos;
	}

	public void setListaCasos(ArrayList<Caso> listaCasos) {
		this.listaCasos = listaCasos;
	}

	@Override
	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<String>("Switch");
		
		raiz.getChildren().add(expresion.getArbolVisual());
		
		if(!listaCasos.isEmpty()) {
			TreeItem<String> listaCasos = new TreeItem<String>("Casos");
			raiz.getChildren().add(listaCasos);
			for (Caso caso : this.listaCasos) {
				listaCasos.getChildren().add(caso.getArbolVisual());
			}
		}
				
		return raiz;
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		tablaSimbolos.guardarSimboloSentencia(this, ambito);
		
		for(Caso caso: listaCasos) 
		{
			caso.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito, "/switch", false);
		
		for(Caso caso: listaCasos) 
		{
			caso.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		
	}

	@Override
	public String getJavaCode() {

		String javaCode = "switch("+expresion.getJavaCode()+"){\n";
		
		for (Caso caso : listaCasos) {
			
			javaCode += caso.getJavaCode()+"\n";
			
		}
		
		javaCode += "}\n";
		
		return javaCode;
	}

}
