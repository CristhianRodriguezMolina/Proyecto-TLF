package co.sis.crirowil.persistencia.analizadorSintactico;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
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

}
