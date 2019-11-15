package co.sis.crirowil.persistencia.analizadorSemantico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSintactico.UnidadDeCompilacion;

public class AnalizadorSemantico {
	
	TablaSimbolos tablaSimbolos;
	ArrayList<String> erroresSemanticos;
	UnidadDeCompilacion unidadCompilacion;

	public AnalizadorSemantico( UnidadDeCompilacion unidadCompilacion ) {
		this.erroresSemanticos = new ArrayList<String>();
		this.unidadCompilacion = unidadCompilacion;
		this.tablaSimbolos = new TablaSimbolos( erroresSemanticos );
	}
	
	public void llenarTablaSimbolos() {
		this.unidadCompilacion.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos);
	}

	public TablaSimbolos getTablaSimbolos() {
		return tablaSimbolos;
	}

	public void setTablaSimbolos(TablaSimbolos tablaSimbolos) {
		this.tablaSimbolos = tablaSimbolos;
	}

	public ArrayList<String> getErroresSemanticos() {
		return erroresSemanticos;
	}

	public void setErroresSemanticos(ArrayList<String> erroresSemanticos) {
		this.erroresSemanticos = erroresSemanticos;
	}
	
	
}
