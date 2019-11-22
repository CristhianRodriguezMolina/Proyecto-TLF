package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

public class ArgumentoMapa {

	private Token llave;
	private Token dato;

	public ArgumentoMapa(Token llave, Token dato) {
		this.llave = llave;
		this.dato = dato;
	}

	public Token getLlave() {
		return llave;
	}

	public void setLlave(Token llave) {
		this.llave = llave;
	}

	public Token getDato() {
		return dato;
	}

	public void setDato(Token dato) {
		this.dato = dato;
	}
	
	public TreeItem<String> getArbolVisual(){
		
		TreeItem<String> raiz = new TreeItem<String>("Argumento mapa");
		
		raiz.getChildren().add(new TreeItem<String>("Llave : "+llave.getPalabra()));
		
		raiz.getChildren().add(new TreeItem<String>("Dato : "+dato.getPalabra()));
		
		
		return raiz;
	}
	
	public String getTipoLlave(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		Simbolo s = tablaSimbolos.buscarSimboloVariable(llave.getPalabra(), ambito);
		if(s == null) {
			erroresSemanticos.add("La variable "+llave.getPalabra()+" no ha sido declarada anteriormente (En argumento de mapa)");
		}else {
			if(llave.getColumna()>s.getColumna() && llave.getFila()>s.getFila()) {
				return s.getTipo();	
			}else {
				erroresSemanticos.add("La variable "+llave.getPalabra()+" no ha sido declarada anteriormente (En argumento de mapa)");	
			}
		}
		return "nulo"; 
		
	}
	
	public String getTipoDato(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		Simbolo s = tablaSimbolos.buscarSimboloVariable(dato.getPalabra(), ambito);
		if(s == null) {
			erroresSemanticos.add("La variable "+dato.getPalabra()+" no ha sido declarada anteriormente (En argumento de mapa)");
		}else {
			if(dato.getColumna()>s.getColumna() && dato.getFila()>s.getFila()) {
				return s.getTipo();			
			}else {
				erroresSemanticos.add("La variable "+dato.getPalabra()+" no ha sido declarada anteriormente (En argumento de mapa)");	
			}
		}
		return "nulo"; 
		
	}

}
