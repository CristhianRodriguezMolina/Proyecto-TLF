package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;

public class Mapa {

	private ArrayList<ArgumentoMapa> listaArgumentos;

	public Mapa(ArrayList<ArgumentoMapa> listaArgumentos) {
		this.listaArgumentos = listaArgumentos;
	}

	public ArrayList<ArgumentoMapa> getListaArgumentos() {
		return listaArgumentos;
	}

	public void setListaArgumentos(ArrayList<ArgumentoMapa> listaArgumentos) {
		this.listaArgumentos = listaArgumentos;
	}

	public TreeItem<String> getArbolVisual(){
		
		
		TreeItem<String> raiz = new TreeItem<String>("Mapa");
		
		if(this.listaArgumentos.size() > 0) {
			TreeItem<String> listaArgumentos = new TreeItem<>("Argumentos");
			raiz.getChildren().add(listaArgumentos);
			for (ArgumentoMapa argumento : this.listaArgumentos) {
				listaArgumentos.getChildren().add(argumento.getArbolVisual());
			}
		}
		
		return raiz;
		
	}
}
