package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;

public class Mapa {

	private ArrayList<Argumento> listaArgumentos;

	public Mapa(ArrayList<Argumento> listaArgumentos) {
		this.listaArgumentos = listaArgumentos;
	}

	public ArrayList<Argumento> getListaArgumentos() {
		return listaArgumentos;
	}

	public void setListaArgumentos(ArrayList<Argumento> listaArgumentos) {
		this.listaArgumentos = listaArgumentos;
	}

	public TreeItem<String> getArbolVisual(){
		
		
		TreeItem<String> raiz = new TreeItem<String>("Mapa");
		
		if(this.listaArgumentos.size() > 0) {
			TreeItem<String> listaArgumentos = new TreeItem<>("Argumentos");
			raiz.getChildren().add(listaArgumentos);
			for (Argumento argumento : this.listaArgumentos) {
				listaArgumentos.getChildren().add(argumento.getArbolVisual());
			}
		}
		
		return raiz;
		
	}
}
