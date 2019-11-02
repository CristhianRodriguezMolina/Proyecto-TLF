package co.sis.crirowil.persistencia.analizadorSemantico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSintactico.UnidadDeCompilacion;

public class AnalizadorSemantico {
	
	TablaSimbolos tablaSimbolos;
	ArrayList<String> erroresSemanticos;
	UnidadDeCompilacion uc;

	public AnalizadorSemantico( UnidadDeCompilacion uc ) {
		this.erroresSemanticos = new ArrayList<String>();
		this.uc = uc;
		this.tablaSimbolos = new TablaSimbolos();
	}
	
	public void llenarTablaSimbolos() {
		this.uc.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos);
	}
}
