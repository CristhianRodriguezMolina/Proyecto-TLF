package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

public class Caso extends Sentencia {

	private Token tipoCaso;
	private Expresion expresion;
	private ArrayList<Sentencia> listaSentencias;

	public Caso(Token tipoCaso, Expresion expresion, ArrayList<Sentencia> listaSentencias) {
		this.tipoCaso = tipoCaso;
		this.expresion = expresion;
		this.listaSentencias = listaSentencias;
	}

	public Token getTipoCaso() {
		return tipoCaso;
	}

	public void setTipoCaso(Token tipoCaso) {
		this.tipoCaso = tipoCaso;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public ArrayList<Sentencia> getListaSentencias() {
		return listaSentencias;
	}

	public void setListaSentencias(ArrayList<Sentencia> listaSentencias) {
		this.listaSentencias = listaSentencias;
	}

	public TreeItem<String> getArbolVisual() {
		
		TreeItem<String> raiz = new TreeItem<String>(tipoCaso.getPalabra());
		
		if(expresion != null) {
			raiz.getChildren().add(expresion.getArbolVisual());			
		}
		
		if(!listaSentencias.isEmpty()) {
			TreeItem<String> listaSentencias = new TreeItem<String>("Sentencias");
			raiz.getChildren().add(listaSentencias);
			for (Sentencia sentencia : this.listaSentencias) {
				listaSentencias.getChildren().add(sentencia.getArbolVisual());
			}
		}
		
		return raiz;
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		tablaSimbolos.guardarSimboloSentencia(this, ambito);
		
		for(Sentencia sentencia: listaSentencias) 
		{
			sentencia.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		if(expresion != null) {
			expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}
		
		for(Sentencia sentencia: listaSentencias) 
		{
			sentencia.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		
	}

}
